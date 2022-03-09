package org.metahut.starfish.parser.function;

import java.util.Collection;

/**
 *
 */
public abstract class AbstractGraphService<K extends Comparable,E extends Comparable,T> implements AbstractQueryService<T> {

    private final AbstractNodeService<K,E,T> nodeService;

    private final AbstractRelationService<K,E,T> relationService;

    public AbstractGraphService(AbstractNodeService<K,E,T> nodeService, AbstractRelationService<K,E,T> relationService) {
        this.nodeService = nodeService;
        this.relationService = relationService;
    }

    @Override
    public Collection<T> query(AbstractQueryCondition condition) {
        return merge(nodeService.query(condition),relationService.query(condition));
    }
}
