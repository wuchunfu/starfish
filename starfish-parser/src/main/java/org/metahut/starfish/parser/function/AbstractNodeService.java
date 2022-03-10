package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.SfNode;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 */
public abstract class AbstractNodeService<K extends Comparable,E extends Comparable,T> extends AbstractQueryService<T> {

    abstract AbstractInstanceService<K,E,T> getClassService();

    abstract AbstractPropertyService<K,E,T> getPropertyService();

    public Map<K, SfNode<K>> union(Set<K> instance,Map<K, Map<String,Object>> props) {
        return instance.stream().collect(Collectors.toMap(k -> k, k -> new SfNode<>(k,props.get(k))));
    }

    @Override
    public Collection<T> query(AbstractQueryCondition condition) {
        return merge(getClassService().query(() -> condition), getPropertyService().query(() -> condition));
    }

    @Override
    public Future<Collection<T>> query(Supplier<AbstractQueryCondition> condition) {
        return new FakeFuture<>(merge(getClassService().query(condition), getPropertyService().query(condition)));
    }

    // create
    /**
     * creat a node
     * @param env
     * @param attributes
     * @param <M>
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    public <M> K create(E env,Map<String,M> attributes) throws StarFishMetaDataOperatingException {
        K key = getClassService().create(env);
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
     * @param <M>
     * @throws StarFishMetaDataOperatingException
     */
    public <M> void add(E env,K instanceId,String property,M obj) throws StarFishMetaDataOperatingException {
        getPropertyService().add(env,instanceId,property,obj);
    }

    // update
    /**
     * update property in the node
     * @param env
     * @param instanceId
     * @param property
     * @param obj
     * @param <M>
     * @throws StarFishMetaDataOperatingException
     */
    public <M> void update(E env,K instanceId,String property,M obj) throws StarFishMetaDataOperatingException {
        getClassService().valid(env,instanceId);
        getPropertyService().update(env,instanceId,property,obj);
    }

    /**
     * partial update some props of one node
     * @param env
     * @param instanceId
     * @param attributes
     * @param <M>
     * @throws StarFishMetaDataOperatingException
     */
    public <M> void update(E env,K instanceId,Map<String,M> attributes) throws StarFishMetaDataOperatingException {
        getClassService().valid(env, instanceId);
        getPropertyService().update(env,instanceId,attributes);
    }

    // modify
    /**
     * force update all props of one node
     * @param env
     * @param instanceId
     * @param attributes
     * @param <M>
     * @throws StarFishMetaDataOperatingException
     */
    public <M> void modify(E env,K instanceId,Map<String,M> attributes) throws StarFishMetaDataOperatingException {
        getClassService().valid(env, instanceId);
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
        K key = getClassService().create(env);
        getPropertyService().copy(env,instanceId,key);
        return key;
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
        getClassService().valid(env,oldInstanceId,newInstanceId);
        getPropertyService().move(env,oldInstanceId,newInstanceId,property);
    }

    //delete
    /**
     * delete a node
     * @param env
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    public void delete(E env,K instanceId) throws StarFishMetaDataOperatingException {
        getClassService().delete(env,instanceId);
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
        getClassService().valid(env,instanceId);
        getPropertyService().delete(env,instanceId,property);
    }

    /**
     * batch delete nodes
     * @param env
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    public void delete(E env, Collection<K> instanceIds) throws StarFishMetaDataOperatingException {
        getClassService().delete(env,instanceIds);
        getPropertyService().delete(env,instanceIds);
    }

}
