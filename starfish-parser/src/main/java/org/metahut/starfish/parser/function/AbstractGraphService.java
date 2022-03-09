package org.metahut.starfish.parser.function;

/**
 *
 */
public abstract class AbstractGraphService<K extends Comparable> {

    private final AbstractNodeService<K> nodeService;

    private final AbstractRelationService<K> relationService;

    public AbstractGraphService(AbstractNodeService<K> nodeService, AbstractRelationService<K> relationService) {
        this.nodeService = nodeService;
        this.relationService = relationService;
    }


}
