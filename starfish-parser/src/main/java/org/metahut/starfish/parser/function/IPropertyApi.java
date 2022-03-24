package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Map;

/**
 *
 */
interface IPropertyApi<V, K, T> extends AbstractQueryService<T> {

    /**
     * get all instance property by typeName
     * @return
     *  {
     *      key : instaceId
     *      value : map (propMap) {k1:v1,K2:v2...}
     *  }
     */
    Map<K, Map<String, T>> attributesMap(V typeName) throws StarFishMetaDataQueryException;

    /**
     * get all attributes by typeName and instanceId
     * @param typeName
     * @param instanceId
     * @return
     */
    Map<String, T> attributesMap(V typeName, K instanceId) throws StarFishMetaDataQueryException;

    /**
     * add attribute to the node
     * @param typeName
     * @param id
     * @param property
     * @param obj
     */
    void add(V typeName, K id,String property, T obj) throws StarFishMetaDataOperatingException;

    /**
     * add attributes to the node
     * @param typeName
     * @param instanceId
     * @param attributes
     */
    void add(V typeName, K instanceId,Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    /**
     * update attribute
     * @param typeName
     * @param instanceId
     * @param property
     * @param obj
     * @throws StarFishMetaDataOperatingException
     */
    void update(V typeName, K instanceId,String property, T obj) throws StarFishMetaDataOperatingException;

    /**
     * clever update some props of one node
     * @param typeName
     * @param instanceId
     * @param attributes
     * @throws StarFishMetaDataOperatingException
     */
    void update(V typeName, K instanceId,Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    /**
     * force update all props of one node
     * @param typeName
     * @param instanceId
     * @param attributes
     * @throws StarFishMetaDataOperatingException
     */
    void modify(V typeName, K instanceId,Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    /**
     * move one property from one node to another node
     * @param typeName
     * @param oldInstanceId
     * @param newInstanceId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void move(V typeName, K oldInstanceId, K newInstanceId,String property) throws StarFishMetaDataOperatingException;

    /**
     * copy attributes from one instance to another instance
     * @param typeName
     * @param fromInstanceId
     * @param toInstanceId
     * @throws StarFishMetaDataOperatingException
     */
    void copy(V typeName, K fromInstanceId, K toInstanceId) throws StarFishMetaDataOperatingException;

    /**
     *  copy attributes from the typeName to another typeName
     * @param oldtypeName
     * @param newtypeName
     * @param deleteOld
     * @throws StarFishMetaDataOperatingException
     */
    void copy(V oldtypeName, V newtypeName,boolean deleteOld) throws StarFishMetaDataOperatingException;

    /**
     * delete all property in typeName
     * @param typeName
     * @throws StarFishMetaDataOperatingException
     */
    void delete(V typeName) throws StarFishMetaDataOperatingException;

    /**
     * delete attribute
     * @param typeName
     * @param instanceId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void delete(V typeName, K instanceId,String property) throws StarFishMetaDataOperatingException;

    /**
     * delete attributes
     * @param typeName
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    void delete(V typeName, K instanceId) throws StarFishMetaDataOperatingException;

    /**
     * batch delete attributes
     * @param typeName
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    void delete(V typeName, K... instanceIds) throws StarFishMetaDataOperatingException;
}
