package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.SfNode;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 */
public abstract class AbstractNodeService<K extends Comparable,E extends Comparable,T> extends AbstractQueryService<T> {

    final AbstractInstanceService<K,E,T> classService;

    final AbstractPropertyService<K,E,T> propertyService;

    /**
     * TODO check not null
     * @param classService
     * @param propertyService
     */
    public AbstractNodeService(AbstractInstanceService<K,E,T> classService, AbstractPropertyService<K,E,T> propertyService) {
        this.classService = classService;
        this.propertyService = propertyService;
    }

    public Map<K, SfNode<K>> union(Set<K> instance,Map<K, Map<String,Object>> props) {
        return instance.stream().collect(Collectors.toMap(k -> k, k -> new SfNode<>(k,props.get(k))));
    }

    @Override
    public Collection<T> query(AbstractQueryCondition condition) {
        return merge(classService.query(() -> condition), propertyService.query(() -> condition));
    }

    @Override
    public Future<Collection<T>> query(Supplier<AbstractQueryCondition> condition) {
        return new FakeFuture<>(merge(classService.query(condition), propertyService.query(condition)));
    }

    public void delete(E env,K id) {
        classService.delete(env,id);
        propertyService.delete(env,id);
    }

    public <M> void add(E env,K id,String property,M obj) {
        propertyService.add(env,id,property,obj);
    }

    public <M> void update(E env,K id,String property,M obj) {
        propertyService.update(env,id,property,obj);
    }

    public void delete(E env,K id,String property) {
        propertyService.delete(env,id,property);
    }

}
