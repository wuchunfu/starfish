package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.SfNode;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 *
 */
public abstract class AbstractGraphService<K extends Comparable,E extends Comparable,T> implements AbstractQueryService<T> {

    final AbstractNodeService<K,E,T> nodeService;

    final AbstractRelationService<K,E,T> relationService;

    /**
     * TODO check not null
     * @param nodeService
     * @param relationService
     */
    public AbstractGraphService(AbstractNodeService<K,E,T> nodeService, AbstractRelationService<K,E,T> relationService) {
        this.nodeService = nodeService;
        this.relationService = relationService;
    }

    @Override
    public Collection<T> query(AbstractQueryCondition condition) {
        return merge(nodeService.query(condition),relationService.query(condition));
    }

    @Override
    public Future<Collection<T>> query(Supplier<AbstractQueryCondition> condition) {
        return new FakeFuture<>(merge(nodeService.query(condition),relationService.query(condition)));
    }

    public SfNode<K> query(E env) {
        Supplier<E> string = () -> env;
        Set<K> ks = nodeService.classService.instanceMap(env);
        Map<K, Map<String, Object>> kMapMap = nodeService.propertyService.propertyMap(env);

        return null;
    }
}
