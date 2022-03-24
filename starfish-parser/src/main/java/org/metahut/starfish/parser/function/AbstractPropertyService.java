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
public abstract class AbstractPropertyService<V,K,T> implements IPropertyApi<V, K, T> {

    @Override
    public abstract Map<K, Map<String, T>> attributesMap(V typeName) throws StarFishMetaDataQueryException;

    @Override
    public abstract Map<String, T> attributesMap(V typeName, K instanceId) throws StarFishMetaDataQueryException;

    @Override
    public abstract void add(V typeName, K id, String property, T obj) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void add(V typeName, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void update(V typeName, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void update(V typeName, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void modify(V typeName, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void move(V typeName, K oldInstanceId, K newInstanceId, String property) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void copy(V typeName, K fromInstanceId, K toInstanceId) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void copy(V oldtypeName, V newtypeName, boolean deleteOld) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void delete(V typeName) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void delete(V typeName, K instanceId, String property) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void delete(V typeName, K instanceId) throws StarFishMetaDataOperatingException;

    @Override
    public abstract void delete(V typeName, K... instanceIds) throws StarFishMetaDataOperatingException;

    @Override
    public abstract Collection<T> query(AbstractQueryCondition condition);

    @Override
    public Future<Collection<T>> query(Supplier<AbstractQueryCondition> condition) {
        return new FakeFuture<>(query(condition.get()));
    }
}
