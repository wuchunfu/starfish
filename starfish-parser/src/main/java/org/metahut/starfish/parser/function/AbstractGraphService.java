package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 *
 */
public abstract class AbstractGraphService<K extends Comparable,E extends Comparable,T> extends AbstractQueryService<T> {

    abstract AbstractNodeService<K,E,T> getNodeService();

    abstract AbstractRelationService<K,E,T> getRelationService();

    @Override
    public Collection<T> query(AbstractQueryCondition condition) {
        return merge(getNodeService().query(condition),getRelationService().query(condition));
    }

    @Override
    public Future<Collection<T>> query(Supplier<AbstractQueryCondition> condition) {
        return new FakeFuture<>(merge(getNodeService().query(condition),getRelationService().query(condition)));
    }

    // create
    /**
     * create a node
     * @param env
     * @param attributes
     * @param <M>
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    public <M> K create(E env,Map<String,M> attributes) throws StarFishMetaDataOperatingException {
        return getNodeService().create(env,attributes);
    }

    /**
     * create a new node under the appointed node
     * @param env
     * @param parentInstanceId
     * @param property
     * @param attributes
     * @param <M>
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    public <M> K create(E env,K parentInstanceId,String property,Map<String,M> attributes) throws StarFishMetaDataOperatingException {
        K key = getNodeService().create(env, attributes);
        getNodeService().getClassService().valid(env,parentInstanceId);
        getRelationService().link(env,parentInstanceId,key,property);
        return key;
    }

    // add node
    /**
     * add property to the node
     * @param env
     * @param instanceId
     * @param property
     * @param obj
     * @param <M>
     * @throws StarFishMetaDataOperatingException
     */
    public <M> void add(E env,K instanceId,String property,M obj) throws StarFishMetaDataOperatingException {
        getNodeService().add(env,instanceId,property,obj);
    }

    //add relation
    /**
     * link two node
     * @param env
     * @param headId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    public void link(E env,K headId,K tailId,String property) throws StarFishMetaDataOperatingException {
        getNodeService().getClassService().valid(env,headId,tailId);
        getRelationService().link(env,headId,tailId,property);
    }

    // update node property
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
        getNodeService().update(env,instanceId,property,obj);
    }

    /**
     * clever update some props of the node
     * @param env
     * @param instanceId
     * @param attributes
     * @param <M>
     * @throws StarFishMetaDataOperatingException
     */
    public <M> void update(E env,K instanceId,Map<String,M> attributes) throws StarFishMetaDataOperatingException {
        getNodeService().update(env,instanceId,attributes);
    }

    // modify
    /**
     * force update all props of the node
     * @param env
     * @param instanceId
     * @param attributes
     * @param <M>
     * @throws StarFishMetaDataOperatingException
     */
    public <M> void modify(E env,K instanceId,Map<String,M> attributes) throws StarFishMetaDataOperatingException {
        getNodeService().modify(env,instanceId,attributes);
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
        return getNodeService().copy(env,instanceId);
    }

    /**
     * copy a node and link to another node
     * @param env
     * @param fromInstanceId
     * @param toInstanceId
     * @param property
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    public K copy(E env,K fromInstanceId,K toInstanceId,String property) throws StarFishMetaDataOperatingException {
        getNodeService().getClassService().valid(env,toInstanceId);
        K newInstanceId = getNodeService().copy(env, fromInstanceId);
        getRelationService().link(env,newInstanceId,toInstanceId,property);
        return newInstanceId;
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
        getNodeService().move(env,oldInstanceId,newInstanceId,property);
    }

    /**
     * repoint the node to another node
     * @param env
     * @param oldHeadId
     * @param newHeadId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    public void move(E env,K oldHeadId,K newHeadId,K tailId,String property) throws StarFishMetaDataOperatingException {
        getNodeService().getClassService().valid(env,oldHeadId,newHeadId,tailId);
        getRelationService().move(env,oldHeadId,newHeadId,tailId,property);
    }

    // delete
    /**
     * delete a node
     * @param env
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    public void delete(E env,K instanceId) throws StarFishMetaDataOperatingException {
        getNodeService().delete(env,instanceId);
        getRelationService().delete(env,instanceId);
    }

    /**
     * delete a property from a node
     * @param env
     * @param id
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    public void delete(E env,K id,String property) throws StarFishMetaDataOperatingException {
        getNodeService().delete(env,id,property);
    }

    // crack
    /**
     * crack the relation between two nodes
     * @param env
     * @param headId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    public void crack(E env,K headId,K tailId,String property) throws StarFishMetaDataOperatingException {
        getNodeService().getClassService().valid(env,headId,tailId);
        getRelationService().crack(env,headId,tailId,property);
    }

    /**
     * batch delete instances
     * @param env
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    public void delete(E env, Collection<K> instanceIds) throws StarFishMetaDataOperatingException {
        getNodeService().delete(env,instanceIds);
        getRelationService().delete(env,instanceIds);
    }

}
