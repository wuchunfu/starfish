package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.Node;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Map;

/**
 *
 */
public interface INodeApi<E,K,T> extends AbstractQueryService<T> {

    Map<K, Node<K, T>> nodes(E env) throws StarFishMetaDataQueryException;

    /**
     * creat a node
     * @param env
     * @param attributes
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    K create(E env, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    /**
     * add a property info to a node
     * @param env
     * @param instanceId
     * @param property
     * @param obj
     * @throws StarFishMetaDataOperatingException
     */
    void add(E env, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException;

    // update
    /**
     * update property in the node
     * @param env
     * @param instanceId
     * @param property
     * @param obj
     * @throws StarFishMetaDataOperatingException
     */
    void update(E env, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException;

    /**
     * partial update some props of one node
     * @param env
     * @param instanceId
     * @param attributes
     * @throws StarFishMetaDataOperatingException
     */
    void update(E env, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    // modify
    /**
     * force update all props of one node
     * @param env
     * @param instanceId
     * @param attributes
     * @throws StarFishMetaDataOperatingException
     */
    void modify(E env, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    /**
     * copy a new node from one exists node
     * @param env
     * @param instanceId
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    K copy(E env, K instanceId) throws StarFishMetaDataOperatingException;

    /**
     * copy from the env to another env
     * @param toEnv
     * @param fromEnv
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    void copy(E toEnv, E fromEnv, K... instanceIds) throws StarFishMetaDataOperatingException;

    // move
    /**
     * move a property from one instance to another instance
     * @param env
     * @param oldInstanceId
     * @param newInstanceId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void move(E env, K oldInstanceId, K newInstanceId, String property) throws StarFishMetaDataOperatingException;

    //delete
    /**
     * delete all node in env
     * @param env
     * @throws StarFishMetaDataOperatingException
     */
    void delete(E env) throws StarFishMetaDataOperatingException;

    /**
     * delete a node
     * @param env
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    void delete(E env, K instanceId) throws StarFishMetaDataOperatingException;

    /**
     * delete a property from a node
     * @param env
     * @param instanceId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void delete(E env, K instanceId, String property) throws StarFishMetaDataOperatingException;

    /**
     * batch delete nodes
     * @param env
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    void delete(E env, K... instanceIds) throws StarFishMetaDataOperatingException;
}
