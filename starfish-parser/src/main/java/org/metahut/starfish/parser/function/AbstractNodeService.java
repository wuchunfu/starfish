package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.SfNode;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 */
public abstract class AbstractNodeService<E,K,T> extends AbstractQueryService<T> {

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

    public Map<K,SfNode<K,T>> nodes(E env) throws StarFishMetaDataQueryException {
        return union(getInstanceService().instanceMap(env),getPropertyService().attributesMap(env));
    }

    // create
    /**
     * creat a node
     * @param env
     * @param attributes
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    public K create(E env,Map<String,T> attributes) throws StarFishMetaDataOperatingException {
        K key = getInstanceService().create(env);
        getPropertyService().add(env,key,attributes);
        return key;
    }

    // add
    /**
     * add a property info to a node
     * @param env
     * @param instanceId
     * @param property
     * @param obj
     * @throws StarFishMetaDataOperatingException
     */
    public void add(E env,K instanceId,String property,T obj) throws StarFishMetaDataOperatingException {
        getPropertyService().add(env,instanceId,property,obj);
    }

    // update
    /**
     * update property in the node
     * @param env
     * @param instanceId
     * @param property
     * @param obj
     * @throws StarFishMetaDataOperatingException
     */
    public void update(E env,K instanceId,String property,T obj) throws StarFishMetaDataOperatingException {
        getInstanceService().valid(env,instanceId);
        getPropertyService().update(env,instanceId,property,obj);
    }

    /**
     * partial update some props of one node
     * @param env
     * @param instanceId
     * @param attributes
     * @throws StarFishMetaDataOperatingException
     */
    public void update(E env,K instanceId,Map<String,T> attributes) throws StarFishMetaDataOperatingException {
        getInstanceService().valid(env, instanceId);
        getPropertyService().update(env,instanceId,attributes);
    }

    // modify
    /**
     * force update all props of one node
     * @param env
     * @param instanceId
     * @param attributes
     * @throws StarFishMetaDataOperatingException
     */
    public void modify(E env,K instanceId,Map<String,T> attributes) throws StarFishMetaDataOperatingException {
        getInstanceService().valid(env, instanceId);
        getPropertyService().modify(env,instanceId,attributes);
    }

    // copy
    /**
     * copy a new node from one exists node
     * @param env
     * @param instanceId
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    public K copy(E env,K instanceId) throws StarFishMetaDataOperatingException {
        K key = getInstanceService().create(env);
        getPropertyService().copy(env,instanceId,key);
        return key;
    }

    /**
     * copy from the env to another env
     * @param oldEnv
     * @param newEnv
     * @param deleteOld
     * @throws StarFishMetaDataOperatingException
     */
    public void copy(E oldEnv,E newEnv,boolean deleteOld) throws StarFishMetaDataOperatingException {
        getInstanceService().copy(oldEnv,newEnv,deleteOld);
        getPropertyService().copy(oldEnv,newEnv,deleteOld);
    }

    // move
    /**
     * move a property from one instance to another instance
     * @param env
     * @param oldInstanceId
     * @param newInstanceId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    public void move(E env,K oldInstanceId,K newInstanceId,String property) throws StarFishMetaDataOperatingException {
        getInstanceService().valid(env,oldInstanceId,newInstanceId);
        getPropertyService().move(env,oldInstanceId,newInstanceId,property);
    }

    //delete

    /**
     * delete all node in env
     * @param env
     * @throws StarFishMetaDataOperatingException
     */
    public void delete(E env) throws StarFishMetaDataOperatingException {
        getInstanceService().delete(env);
        getPropertyService().delete(env);
    }

    /**
     * delete a node
     * @param env
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    public void delete(E env,K instanceId) throws StarFishMetaDataOperatingException {
        getInstanceService().delete(env,instanceId);
        getPropertyService().delete(env,instanceId);
    }

    /**
     * delete a property from a node
     * @param env
     * @param instanceId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    public void delete(E env,K instanceId,String property) throws StarFishMetaDataOperatingException {
        getInstanceService().valid(env,instanceId);
        getPropertyService().delete(env,instanceId,property);
    }

    /**
     * batch delete nodes
     * @param env
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    public void delete(E env, Collection<K> instanceIds) throws StarFishMetaDataOperatingException {
        getInstanceService().delete(env,instanceIds);
        getPropertyService().delete(env,instanceIds);
    }

}
