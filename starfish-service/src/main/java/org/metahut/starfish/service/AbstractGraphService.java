package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.instance.Graph;
import org.metahut.starfish.parser.domain.instance.Node;
import org.metahut.starfish.parser.domain.instance.Relation;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 *
 */
public abstract class AbstractGraphService<V,K,T> implements IGraphApi<V, K, T> {

    protected abstract AbstractNodeService<V,K,T> getNodeService();

    protected abstract AbstractRelationService<V,K,T> getRelationService();

    public Graph<K,T> union(Map<K, Node<K,T>> nodes, List<Relation<K>> lines) {
        return new Graph<>(nodes,lines);
    }

    @Override
    public Collection<T> query(AbstractQueryCondition condition) {
        return merge(getNodeService().query(condition),getRelationService().query(condition));
    }

    @Override
    public Future<Collection<T>> query(Supplier<AbstractQueryCondition> condition) {
        return new FakeFuture<>(merge(getNodeService().query(condition),getRelationService().query(condition)));
    }

    @Override
    public Graph<K,T> graph(V typeName) throws StarFishMetaDataQueryException {
        return union(getNodeService().nodes(typeName),getRelationService().lines(typeName));
    }

    // create
    @Override
    public K create(V typeName, String property, T obj) throws StarFishMetaDataOperatingException {
        Map<String,T> map = new HashMap<>();
        map.put(property,obj);
        return create(typeName,map);
    }

    /**
     * create a node
     * @param typeName
     * @param attributes
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public K create(V typeName, Map<String, T> attributes) throws StarFishMetaDataOperatingException {
        return getNodeService().create(typeName,attributes);
    }

    /**
     * create a new node under the appointed node
     * @param typeName
     * @param parentInstanceId
     * @param property
     * @param attributes
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public K create(V typeName, K parentInstanceId, String property, Map<String, T> attributes) throws StarFishMetaDataOperatingException {
        K key = getNodeService().create(typeName, attributes);
        getNodeService().getInstanceService().valid(typeName, Arrays.asList(parentInstanceId));
        getRelationService().link(typeName,parentInstanceId,key,property);
        return key;
    }

    // add node
    /**
     * add property to the node
     * @param typeName
     * @param instanceId
     * @param property
     * @param obj
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void add(V typeName, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException {
        getNodeService().add(typeName,instanceId,property,obj);
    }

    //add relation
    /**
     * link two node
     * @param typeName
     * @param headId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void link(V typeName, K headId, K tailId, String property) throws StarFishMetaDataOperatingException {
        getNodeService().getInstanceService().valid(typeName,Arrays.asList(headId,tailId));
        getRelationService().link(typeName,headId,tailId,property);
    }

    // update node property
    /**
     * update property in the node
     * @param typeName
     * @param instanceId
     * @param property
     * @param obj
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void update(V typeName, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException {
        getNodeService().update(typeName,instanceId,property,obj);
    }

    /**
     * clever update some props of the node
     * @param typeName
     * @param instanceId
     * @param attributes
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void update(V typeName, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException {
        getNodeService().update(typeName,instanceId,attributes);
    }

    // modify
    /**
     * force update all props of the node
     * @param typeName
     * @param instanceId
     * @param attributes
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void modify(V typeName, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException {
        getNodeService().modify(typeName,instanceId,attributes);
    }

    // copy

    /**
     * copy instances from one typeName to another typeName
     * @param totypeName
     * @param fromtypeName
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void copy(V totypeName, V fromtypeName, Collection<K> instanceIds) throws StarFishMetaDataOperatingException {

        getNodeService().copy(totypeName,fromtypeName,instanceIds);
        getRelationService().copy(totypeName,fromtypeName,instanceIds);
        getNodeService().copy(totypeName, fromtypeName, instanceIds);
    }

    /**
     * copy a node and link to another node
     * @param typeName
     * @param fromInstanceId
     * @param toInstanceId
     * @param property
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public K copy(V typeName, K fromInstanceId, K toInstanceId, String property) throws StarFishMetaDataOperatingException {
        getNodeService().getInstanceService().valid(typeName,Arrays.asList(toInstanceId));
        K newInstanceId = getNodeService().copy(typeName, fromInstanceId);
        getRelationService().link(typeName,newInstanceId,toInstanceId,property);
        return newInstanceId;
    }

    // move
    /**
     * move a property from one instance to another instance
     * @param typeName
     * @param oldInstanceId
     * @param newInstanceId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void move(V typeName, K oldInstanceId, K newInstanceId, String property) throws StarFishMetaDataOperatingException {
        getNodeService().move(typeName,oldInstanceId,newInstanceId,property);
    }

    /**
     * repoint the node to another node
     * @param typeName
     * @param oldHeadId
     * @param newHeadId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void move(V typeName, K oldHeadId, K newHeadId, K tailId, String property) throws StarFishMetaDataOperatingException {
        getNodeService().getInstanceService().valid(typeName,Arrays.asList(oldHeadId,newHeadId,tailId));
        getRelationService().move(typeName,oldHeadId,newHeadId,tailId,property);
    }

    // delete
    /**
     * delete all node in typeName
     * @param typeName
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void delete(V typeName) throws StarFishMetaDataOperatingException {
        getNodeService().delete(typeName);
        getRelationService().delete(typeName);
    }

    /**
     * delete a node
     * @param typeName
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void delete(V typeName, K instanceId) throws StarFishMetaDataOperatingException {
        getNodeService().delete(typeName,instanceId);
        getRelationService().delete(typeName,instanceId);
    }

    /**
     * delete a property from a node
     * @param typeName
     * @param id
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void delete(V typeName, K id, String property) throws StarFishMetaDataOperatingException {
        getNodeService().delete(typeName,id,property);
    }

    // crack
    /**
     * crack the relation between two nodes
     * @param typeName
     * @param headId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void crack(V typeName, K headId, K tailId, String property) throws StarFishMetaDataOperatingException {
        getNodeService().getInstanceService().valid(typeName,Arrays.asList(headId,tailId));
        getRelationService().crack(typeName,headId,tailId,property);
    }

    /**
     * batch delete instances
     * @param typeName
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void delete(V typeName, Collection<K> instanceIds) throws StarFishMetaDataOperatingException {
        getNodeService().delete(typeName,instanceIds);
        getRelationService().delete(typeName,instanceIds);
    }

}
