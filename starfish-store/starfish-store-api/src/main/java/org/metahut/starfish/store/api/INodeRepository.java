package org.metahut.starfish.store.api;

import java.util.Map;

/**
 *
 */
public interface INodeRepository<E,K,T> {


    Map<K, Map<String, T>> nodes(E env);

    /**
     * creat a node
     * @param env
     * @param attributes
     * @return
     */
    K create(E env, Map<String, T> attributes);

    /**
     * add a property info to a node
     * @param env
     * @param instanceId
     * @param property
     * @param obj
     */
    void add(E env, K instanceId, String property, T obj);

    // update
    /**
     * update property in the node
     * @param env
     * @param instanceId
     * @param property
     * @param obj
     */
    void update(E env, K instanceId, String property, T obj);

    /**
     * partial update some props of one node
     * @param env
     * @param instanceId
     * @param attributes
     */
    void update(E env, K instanceId, Map<String, T> attributes);

    // modify
    /**
     * force update all props of one node
     * @param env
     * @param instanceId
     * @param attributes
     */
    void modify(E env, K instanceId, Map<String, T> attributes);

    /**
     * copy a new node from one exists node
     * @param env
     * @param instanceId
     * @return
     */
    K copy(E env, K instanceId);

    /**
     * copy from the env to another env
     * @param toEnv
     * @param fromEnv
     * @param instanceIds
     */
    void copy(E toEnv, E fromEnv, K... instanceIds);

    // move
    /**
     * move a property from one instance to another instance
     * @param env
     * @param oldInstanceId
     * @param newInstanceId
     * @param property
     */
    void move(E env, K oldInstanceId, K newInstanceId, String property);

    //delete
    /**
     * delete all node in env
     * @param env
     */
    void delete(E env);

    /**
     * delete a node
     * @param env
     * @param instanceId
     */
    void delete(E env, K instanceId);

    /**
     * delete a property from a node
     * @param env
     * @param instanceId
     * @param property
     */
    void delete(E env, K instanceId, String property);

    /**
     * batch delete nodes
     * @param env
     * @param instanceIds
     */
    void delete(E env, K... instanceIds);
}
