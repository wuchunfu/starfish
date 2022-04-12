package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.instance.Relation;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Collection;
import java.util.List;

/**
 *
 */
public abstract class AbstractRelationService<V,K,T> implements IRelationApi<V,K,T> {

    @Override
    public List<Relation<K>> lines(V env) throws StarFishMetaDataQueryException {
        return null;
    }
}
