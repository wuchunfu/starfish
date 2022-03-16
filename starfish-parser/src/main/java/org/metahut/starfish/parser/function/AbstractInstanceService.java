package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public abstract class AbstractInstanceService<E,K,T> implements IInstanceApi<E,K,T> {

    @Override
    public abstract Set<K> instanceMap(E env) throws StarFishMetaDataQueryException;

    @Override
    public Future<Set<K>> instanceMap(Supplier<E> env) throws StarFishMetaDataQueryException {
        return new FakeFuture<>(instanceMap(env.get()));
    }

    @Override
    public abstract void valid(E env, K... instanceIds) throws StarFishMetaDataOperatingException;

    @Override
    public abstract K create(E env) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void copy(E oldEnv,E newEnv,boolean deleteOld) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void delete(E env) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void delete(E env,K instanceId) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void delete(E env, K... instanceIds) throws StarFishMetaDataOperatingException;
}
