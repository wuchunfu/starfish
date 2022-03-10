package org.metahut.starfish.parser.function;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Supplier;

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

    abstract void union(Set<K> keys,Map<K, Map<String,Object>> props);

    @Override
    public Collection<T> query(AbstractQueryCondition condition) {
        return merge(classService.query(() -> condition), propertyService.query(() -> condition));
    }

    @Override
    public Future<Collection<T>> query(Supplier<AbstractQueryCondition> condition) {
        return new FakeFuture<>(merge(classService.query(condition), propertyService.query(condition)));
    }

}
