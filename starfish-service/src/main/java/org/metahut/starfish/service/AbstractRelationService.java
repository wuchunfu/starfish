package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.instance.Relation;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.List;

/**
 *
 */
public abstract class AbstractRelationService<V,K,T> implements IRelationApi<V,K,T> {

    @Override
    public List<Relation<K>> lines(V env) throws StarFishMetaDataQueryException {
        return null;
    }

    @Override
    public void link(V env, K headId, K tailId, String property) throws StarFishMetaDataOperatingException {

    }

    @Override
    public void crack(V env, K headId, K tailId, String property) throws StarFishMetaDataOperatingException {

    }

    @Override
    public void delete(V env) throws StarFishMetaDataOperatingException {

    }

    @Override
    public void delete(V env, K instanceId) throws StarFishMetaDataOperatingException {

    }

    @Override
    public void delete(V env, K... instanceIds) throws StarFishMetaDataOperatingException {

    }

    @Override
    public void move(V env, K oldHeadId, K newHeadId, K tailId, String property) throws StarFishMetaDataOperatingException {

    }

    @Override
    public void copy(V oldEnv, V newEnv, K... instanceIds) throws StarFishMetaDataOperatingException {

    }
}
