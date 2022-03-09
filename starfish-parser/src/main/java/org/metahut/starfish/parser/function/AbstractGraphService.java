package org.metahut.starfish.parser.function;

/**
 *
 */
public abstract class AbstractGraphService<K extends Comparable,E extends Comparable> {

    private final AbstractNodeService<K,E> nodeService;

    private final AbstractRelationService<K,E> relationService;

    public AbstractGraphService(AbstractNodeService<K,E> nodeService, AbstractRelationService<K,E> relationService) {
        this.nodeService = nodeService;
        this.relationService = relationService;
    }


}
