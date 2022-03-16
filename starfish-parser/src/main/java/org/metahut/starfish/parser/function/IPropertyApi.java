package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Map;

/**
 *
 */
interface IPropertyApi<E, K, T> extends AbstractQueryService<T> {

    /**
     * get all instance property by env
     * @return
     *  {
     *      key : instaceId
     *      value : map (propMap) {k1:v1,K2:v2...}
     *  }
     */
    Map<K, Map<String, T>> attributesMap(E env) throws StarFishMetaDataQueryException;

    /**
     * get all attributes by env and instanceId
     * @param env
     * @param instanceId
     * @return
     */
    Map<String, T> attributesMap(E env, K instanceId) throws StarFishMetaDataQueryException;

    /**
     * add attribute to the node
     * @param env
     * @param id
     * @param property
     * @param obj
     */
    void add(E env, K id,String property, T obj) throws StarFishMetaDataOperatingException;

    /**
     * add attributes to the node
     * @param env
     * @param instanceId
     * @param attributes
     */
    void add(E env, K instanceId,Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    /**
     * update attribute
     * @param env
     * @param instanceId
     * @param property
     * @param obj
     * @throws StarFishMetaDataOperatingException
     */
    void update(E env, K instanceId,String property, T obj) throws StarFishMetaDataOperatingException;

    /**
     * clever update some props of one node
     * @param env
     * @param instanceId
     * @param attributes
     * @throws StarFishMetaDataOperatingException
     */
    void update(E env, K instanceId,Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    /**
     * force update all props of one node
     * @param env
     * @param instanceId
     * @param attributes
     * @throws StarFishMetaDataOperatingException
     */
    void modify(E env, K instanceId,Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    /**
     * move one property from one node to another node
     * @param env
     * @param oldInstanceId
     * @param newInstanceId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void move(E env, K oldInstanceId, K newInstanceId,String property) throws StarFishMetaDataOperatingException;

    /**
     * copy attributes from one instance to another instance
     * @param env
     * @param fromInstanceId
     * @param toInstanceId
     * @throws StarFishMetaDataOperatingException
     */
    void copy(E env, K fromInstanceId, K toInstanceId) throws StarFishMetaDataOperatingException;

    /**
     *  copy attributes from the env to another env
     * @param oldEnv
     * @param newEnv
     * @param deleteOld
     * @throws StarFishMetaDataOperatingException
     */
    void copy(E oldEnv, E newEnv,boolean deleteOld) throws StarFishMetaDataOperatingException;

    /**
     * delete all property in env
     * @param env
     * @throws StarFishMetaDataOperatingException
     */
    void delete(E env) throws StarFishMetaDataOperatingException;

    /**
     * delete attribute
     * @param env
     * @param instanceId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void delete(E env, K instanceId,String property) throws StarFishMetaDataOperatingException;

    /**
     * delete attributes
     * @param env
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    void delete(E env, K instanceId) throws StarFishMetaDataOperatingException;

    /**
     * batch delete attributes
     * @param env
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    void delete(E env, K... instanceIds) throws StarFishMetaDataOperatingException;
}
