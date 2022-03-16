package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.SfNode;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 */
public abstract class AbstractNodeService<E,K,T> implements INodeApi<E,K,T> {

    abstract AbstractInstanceService<E,K,T> getInstanceService();

    abstract AbstractPropertyService<E,K,T> getPropertyService();

    public Map<K, SfNode<K,T>> union(Set<K> instance,Map<K, Map<String,T>> props) {
        return instance.stream().collect(Collectors.toMap(k -> k, k -> new SfNode<>(k,props.get(k))));
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
    public Map<K,SfNode<K,T>> nodes(E env) throws StarFishMetaDataQueryException {
        return union(getInstanceService().instanceMap(env),getPropertyService().attributesMap(env));
    }

    // create
    @Override
    public K create(E env,Map<String,T> attributes) throws StarFishMetaDataOperatingException {
        K key = getInstanceService().create(env);
        getPropertyService().add(env,key,attributes);
        return key;
    }

    // add
    @Override
    public void add(E env,K instanceId,String property,T obj) throws StarFishMetaDataOperatingException {
        getPropertyService().add(env,instanceId,property,obj);
    }

    // update
    @Override
    public void update(E env,K instanceId,String property,T obj) throws StarFishMetaDataOperatingException {
        getInstanceService().valid(env,instanceId);
        getPropertyService().update(env,instanceId,property,obj);
    }

    @Override
    public void update(E env,K instanceId,Map<String,T> attributes) throws StarFishMetaDataOperatingException {
        getInstanceService().valid(env, instanceId);
        getPropertyService().update(env,instanceId,attributes);
    }

    // modify
    @Override
    public void modify(E env, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException {
        getInstanceService().valid(env, instanceId);
        getPropertyService().modify(env,instanceId,attributes);
    }

    // copy
    @Override
    public K copy(E env, K instanceId) throws StarFishMetaDataOperatingException {
        K key = getInstanceService().create(env);
        getPropertyService().copy(env,instanceId,key);
        return key;
    }

    @Override
    public void copy(E toEnv, E fromEnv, K... instanceIds) throws StarFishMetaDataOperatingException {
    }

    // move
    @Override
    public void move(E env, K oldInstanceId, K newInstanceId, String property) throws StarFishMetaDataOperatingException {
        getInstanceService().valid(env,oldInstanceId,newInstanceId);
        getPropertyService().move(env,oldInstanceId,newInstanceId,property);
    }

    //delete
    @Override
    public void delete(E env) throws StarFishMetaDataOperatingException {
        getInstanceService().delete(env);
        getPropertyService().delete(env);
    }

    @Override
    public void delete(E env, K instanceId) throws StarFishMetaDataOperatingException {
        getInstanceService().delete(env,instanceId);
        getPropertyService().delete(env,instanceId);
    }

    @Override
    public void delete(E env, K instanceId, String property) throws StarFishMetaDataOperatingException {
        getInstanceService().valid(env,instanceId);
        getPropertyService().delete(env,instanceId,property);
    }

    @Override
    public void delete(E env, K... instanceIds) throws StarFishMetaDataOperatingException {
        getInstanceService().delete(env,instanceIds);
        getPropertyService().delete(env,instanceIds);
    }

}
