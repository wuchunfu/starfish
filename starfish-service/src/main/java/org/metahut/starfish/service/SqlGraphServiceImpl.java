package org.metahut.starfish.service;

import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.function.AbstractNodeService;
import org.metahut.starfish.parser.function.AbstractRelation;
import org.metahut.starfish.parser.function.AbstractRelationService;
import org.metahut.starfish.parser.function.AbstractSqlGraphService;

import javax.annotation.Resource;

/**
 *
 */
public class SqlGraphServiceImpl<E,K,T> extends AbstractSqlGraphService<E,K,T> {

    @Resource
    private AbstractNodeService<E,K,T> nodeService;

    @Resource
    private AbstractRelationService<E,K,T> relationService;

    @Override
    protected AbstractNodeService<E, K, T> getNodeService() {
        return nodeService;
    }

    @Override
    protected AbstractRelationService<E, K, T> getRelationService() {
        return relationService;
    }

    @Override
    public K create(E env, String property, T obj) throws StarFishMetaDataOperatingException {
        return null;
    }
}
