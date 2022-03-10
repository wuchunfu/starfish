package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
public abstract class AbstractPropertyService<K extends Comparable,E extends Comparable,T> extends AbstractQueryService<T> {

    /**
     * get all instance property by env
     * @return
     *  {
     *      key : instaceId
     *      value : map (propMap) {k1:v1,K2:v2...}
     *  }
     */
    abstract <M> Map<K, Map<String,M>> attributesMap(E env);

    /**
     * get all attributes by env and instanceId
     * @param env
     * @param instanceId
     * @param <M>
     * @return
     */
    abstract <M> Map<String,M> attributesMap(E env,K instanceId);

    // add
    /**
     * add attribute to the node
     * @param env
     * @param id
     * @param property
     * @param obj
     * @param <M>
     */
    abstract <M> void add(E env,K id,String property,M obj);

    /**
     * add attributes to the node
     * @param env
     * @param instanceId
     * @param attributes
     * @param <M>
     */
    abstract <M> void add(E env,K instanceId,Map<String,M> attributes);

    // update
    /**
     * update attribute
     * @param env
     * @param instanceId
     * @param property
     * @param obj
     * @param <M>
     * @throws StarFishMetaDataOperatingException
     */
    abstract <M> void update(E env,K instanceId,String property,M obj) throws StarFishMetaDataOperatingException;

    /**
     * clever update some props of one node
     * @param env
     * @param instanceId
     * @param attributes
     * @param <M>
     * @throws StarFishMetaDataOperatingException
     */
    abstract <M> void update(E env,K instanceId,Map<String,M> attributes) throws StarFishMetaDataOperatingException;

    // modify
    /**
     * force update all props of one node
     * @param env
     * @param instanceId
     * @param attributes
     * @param <M>
     * @throws StarFishMetaDataOperatingException
     */
    abstract <M> void modify(E env,K instanceId,Map<String,M> attributes) throws StarFishMetaDataOperatingException;

    // move
    /**
     * move one property from one node to another node
     * @param env
     * @param oldInstanceId
     * @param newInstanceId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    abstract void move(E env,K oldInstanceId,K newInstanceId,String property) throws StarFishMetaDataOperatingException;

    // copy
    /**
     * copy attributes from one instance to another instance
     * @param env
     * @param fromInstanceId
     * @param toInstanceId
     * @throws StarFishMetaDataOperatingException
     */
    abstract void copy(E env,K fromInstanceId,K toInstanceId) throws StarFishMetaDataOperatingException;

    // delete
    /**
     * delete attribute
     * @param env
     * @param instanceId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    abstract void delete(E env,K instanceId,String property) throws StarFishMetaDataOperatingException;

    /**
     * delete attributes
     * @param env
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    abstract void delete(E env,K instanceId) throws StarFishMetaDataOperatingException;

    /**
     * batch delete attributes
     * @param env
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    abstract void delete(E env, Collection<K> instanceIds) throws StarFishMetaDataOperatingException;

}
