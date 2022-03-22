package org.metahut.starfish.service;

import org.metahut.starfish.parser.function.AbstractNodeService;
import org.metahut.starfish.parser.function.AbstractRelationService;
import org.metahut.starfish.parser.function.AbstractSqlGraphService;

import javax.annotation.Resource;

/**
 *
 */
public class GraphServiceImpl<E,K,T> extends AbstractSqlGraphService<E,K,T> {

    @Resource
    private Object graphService;

    @Override
    protected AbstractNodeService<E, K, T> getNodeService() {
        return (AbstractNodeService<E, K, T>)graphService;
    }

    @Override
    protected AbstractRelationService<E, K, T> getRelationService() {
        return (AbstractRelationService<E, K, T>)graphService;
    }
}
