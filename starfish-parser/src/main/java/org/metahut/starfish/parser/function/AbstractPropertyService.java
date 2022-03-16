package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 *
 */
public abstract class AbstractPropertyService<E,K,T> implements IPropertyApi<E, K, T> {

    @Override
    public abstract Map<K, Map<String, T>> attributesMap(E env) throws StarFishMetaDataQueryException;

    @Override
    public abstract Map<String, T> attributesMap(E env, K instanceId) throws StarFishMetaDataQueryException;

    @Override
    public abstract void add(E env, K id, String property, T obj) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void add(E env, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void update(E env, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void update(E env, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void modify(E env, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void move(E env, K oldInstanceId, K newInstanceId, String property) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void copy(E env, K fromInstanceId, K toInstanceId) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void copy(E oldEnv, E newEnv, boolean deleteOld) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void delete(E env) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void delete(E env, K instanceId, String property) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void delete(E env, K instanceId) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void delete(E env, K... instanceIds) throws StarFishMetaDataOperatingException;

    @Override
    public abstract Collection<T> query(AbstractQueryCondition condition);

    @Override
    public Future<Collection<T>> query(Supplier<AbstractQueryCondition> condition) {
        return new FakeFuture<>(query(condition.get()));
    }
}
