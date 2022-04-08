package org.metahut.starfish.service;

import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public abstract class AbstractInstanceService<V,K,T> implements IInstanceApi<V,K,T> {

    @Override
    public abstract Set<K> instanceMap(V typeName) throws StarFishMetaDataQueryException;

    @Override
    public Future<Set<K>> instanceMap(Supplier<V> typeName) throws StarFishMetaDataQueryException {
        return new FakeFuture<>(instanceMap(typeName.get()));
    }

    @Override
    public abstract void valid(V typeName, K... instanceIds) throws StarFishMetaDataOperatingException;

    @Override
    public abstract K create(V typeName) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void copy(V oldtypeName, V newtypeName,boolean deleteOld) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void delete(V typeName) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void delete(V typeName,K instanceId) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void delete(V typeName, K... instanceIds) throws StarFishMetaDataOperatingException;
}
