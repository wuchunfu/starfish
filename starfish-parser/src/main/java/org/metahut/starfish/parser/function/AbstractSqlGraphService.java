package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.Graph;
import org.metahut.starfish.parser.domain.instance.Relation;
import org.metahut.starfish.parser.domain.instance.Node;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 *
 */
public abstract class AbstractSqlGraphService<E,K,T> implements IGraphApi<E, K, T> {

    abstract AbstractNodeService<E,K,T> getNodeService();

    abstract AbstractRelationService<E,K,T> getRelationService();

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
    public Graph<K,T> graph(E env) throws StarFishMetaDataQueryException {
        return union(getNodeService().nodes(env),getRelationService().lines(env));
    }

    // create
    /**
     * create a node
     * @param env
     * @param attributes
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public K create(E env, Map<String, T> attributes) throws StarFishMetaDataOperatingException {
        return getNodeService().create(env,attributes);
    }

    /**
     * create a new node under the appointed node
     * @param env
     * @param parentInstanceId
     * @param property
     * @param attributes
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public K create(E env, K parentInstanceId, String property, Map<String, T> attributes) throws StarFishMetaDataOperatingException {
        K key = getNodeService().create(env, attributes);
        getNodeService().getInstanceService().valid(env,parentInstanceId);
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
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void add(E env, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException {
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
    @Override
    public void link(E env, K headId, K tailId, String property) throws StarFishMetaDataOperatingException {
        getNodeService().getInstanceService().valid(env,headId,tailId);
        getRelationService().link(env,headId,tailId,property);
    }

    // update node property
    /**
     * update property in the node
     * @param env
     * @param instanceId
     * @param property
     * @param obj
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void update(E env, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException {
        getNodeService().update(env,instanceId,property,obj);
    }

    /**
     * clever update some props of the node
     * @param env
     * @param instanceId
     * @param attributes
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void update(E env, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException {
        getNodeService().update(env,instanceId,attributes);
    }

    // modify
    /**
     * force update all props of the node
     * @param env
     * @param instanceId
     * @param attributes
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void modify(E env, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException {
        getNodeService().modify(env,instanceId,attributes);
    }

    // copy

    /**
     * copy instances from one env to another env
     * @param toEnv
     * @param fromEnv
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void copy(E toEnv,E fromEnv,K... instanceIds) throws StarFishMetaDataOperatingException {

        getNodeService().copy(toEnv,fromEnv,instanceIds);
        getRelationService().copy(toEnv,fromEnv,instanceIds);
        getNodeService().copy(toEnv, fromEnv, instanceIds);
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
    @Override
    public K copy(E env, K fromInstanceId, K toInstanceId, String property) throws StarFishMetaDataOperatingException {
        getNodeService().getInstanceService().valid(env,toInstanceId);
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
    @Override
    public void move(E env, K oldInstanceId, K newInstanceId, String property) throws StarFishMetaDataOperatingException {
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
    @Override
    public void move(E env, K oldHeadId, K newHeadId, K tailId, String property) throws StarFishMetaDataOperatingException {
        getNodeService().getInstanceService().valid(env,oldHeadId,newHeadId,tailId);
        getRelationService().move(env,oldHeadId,newHeadId,tailId,property);
    }

    // delete
    /**
     * delete all node in env
     * @param env
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void delete(E env) throws StarFishMetaDataOperatingException {
        getNodeService().delete(env);
        getRelationService().delete(env);
    }

    /**
     * delete a node
     * @param env
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void delete(E env, K instanceId) throws StarFishMetaDataOperatingException {
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
    @Override
    public void delete(E env, K id, String property) throws StarFishMetaDataOperatingException {
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
    @Override
    public void crack(E env, K headId, K tailId, String property) throws StarFishMetaDataOperatingException {
        getNodeService().getInstanceService().valid(env,headId,tailId);
        getRelationService().crack(env,headId,tailId,property);
    }

    /**
     * batch delete instances
     * @param env
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    @Override
    public void delete(E env, K... instanceIds) throws StarFishMetaDataOperatingException {
        getNodeService().delete(env,instanceIds);
        getRelationService().delete(env,instanceIds);
    }

}
