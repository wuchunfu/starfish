package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.instance.Node;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 */
public abstract class AbstractNodeService<V,K,T> implements INodeApi<V,K,T> {

    protected abstract AbstractInstanceService<V,K,T> getInstanceService();

    protected abstract AbstractPropertyService<V,K,T> getPropertyService();

    public Map<K, Node<K,T>> union(Set<K> instance,Map<K, Map<String,T>> props) {
        return instance.stream().collect(Collectors.toMap(k -> k, k -> new Node<>(k,props.get(k))));
    }

    @Override
    public Collection<T> query(AbstractQueryCondition condition) {
        return merge(getInstanceService().query(() -> condition), getPropertyService().query(() -> condition));
    }

    @Override
    public Future<Collection<T>> query(Supplier<AbstractQueryCondition> condition) {
        return new FakeFuture<>(merge(getInstanceService().query(condition), getPropertyService().query(condition)));
    }

    @Override
    public Map<K, Node<K,T>> nodes(V typeName) throws StarFishMetaDataQueryException {
        return union(getInstanceService().instanceMap(typeName),getPropertyService().attributesMap(typeName));
    }

    // create
    @Override
    public K create(V typeName,Map<String,T> attributes) throws StarFishMetaDataOperatingException {
        K key = getInstanceService().create(typeName);
        getPropertyService().add(typeName,key,attributes);
        return key;
    }

    // add
    @Override
    public void add(V typeName,K entityId,String property,T obj) throws StarFishMetaDataOperatingException {
        getPropertyService().add(typeName,entityId,property,obj);
    }

    // update
    @Override
    public void update(V typeName,K entityId,String property,T obj) throws StarFishMetaDataOperatingException {
        getInstanceService().valid(typeName, Arrays.asList(entityId));
        getPropertyService().update(typeName,entityId,property,obj);
    }

    @Override
    public void update(V typeName,K entityId,Map<String,T> attributes) throws StarFishMetaDataOperatingException {
        getInstanceService().valid(typeName, Arrays.asList(entityId));
        getPropertyService().update(typeName,entityId,attributes);
    }

    // modify
    @Override
    public void modify(V typeName, K entityId, Map<String, T> attributes) throws StarFishMetaDataOperatingException {
        getInstanceService().valid(typeName, Arrays.asList(entityId));
        getPropertyService().modify(typeName,entityId,attributes);
    }

    // copy
    @Override
    public K copy(V typeName, K entityId) throws StarFishMetaDataOperatingException {
        K key = getInstanceService().create(typeName);
        getPropertyService().copy(typeName,entityId,key);
        return key;
    }

    @Override
    public void copy(V totypeName, V fromtypeName, Collection<K> entityIds) throws StarFishMetaDataOperatingException {
    }

    // move
    @Override
    public void move(V typeName, K oldentityId, K newentityId, String property) throws StarFishMetaDataOperatingException {
        getInstanceService().valid(typeName,Arrays.asList(oldentityId,newentityId));
        getPropertyService().move(typeName,oldentityId,newentityId,property);
    }

    //delete
    @Override
    public void delete(V typeName) throws StarFishMetaDataOperatingException {
        getInstanceService().delete(typeName);
        getPropertyService().delete(typeName);
    }

    @Override
    public void delete(V typeName, K entityId) throws StarFishMetaDataOperatingException {
        getInstanceService().delete(typeName,entityId);
        getPropertyService().delete(typeName,entityId);
    }

    @Override
    public void delete(V typeName, K entityId, String property) throws StarFishMetaDataOperatingException {
        getInstanceService().valid(typeName,Arrays.asList(entityId));
        getPropertyService().delete(typeName,entityId,property);
    }

    @Override
    public void delete(V typeName, Collection<K> entityIds) throws StarFishMetaDataOperatingException {
        getInstanceService().delete(typeName,entityIds);
        getPropertyService().delete(typeName,entityIds);
    }

}
