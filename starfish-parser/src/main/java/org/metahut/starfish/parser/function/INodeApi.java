package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.Node;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Map;

/**
 *
 */
public interface INodeApi<V,K,T> extends AbstractQueryService<T> {

    Map<K, Node<K, T>> nodes(V typeName) throws StarFishMetaDataQueryException;

    /**
     * creat a node
     * @param typeName
     * @param attributes
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    K create(V typeName, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    /**
     * add a property info to a node
     * @param typeName
     * @param instanceId
     * @param property
     * @param obj
     * @throws StarFishMetaDataOperatingException
     */
    void add(V typeName, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException;

    // update
    /**
     * update property in the node
     * @param typeName
     * @param instanceId
     * @param property
     * @param obj
     * @throws StarFishMetaDataOperatingException
     */
    void update(V typeName, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException;

    /**
     * partial update some props of one node
     * @param typeName
     * @param instanceId
     * @param attributes
     * @throws StarFishMetaDataOperatingException
     */
    void update(V typeName, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    // modify
    /**
     * force update all props of one node
     * @param typeName
     * @param instanceId
     * @param attributes
     * @throws StarFishMetaDataOperatingException
     */
    void modify(V typeName, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    /**
     * copy a new node from one exists node
     * @param typeName
     * @param instanceId
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    K copy(V typeName, K instanceId) throws StarFishMetaDataOperatingException;

    /**
     * copy from the typeName to another typeName
     * @param totypeName
     * @param fromtypeName
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    void copy(V totypeName, V fromtypeName, K... instanceIds) throws StarFishMetaDataOperatingException;

    // move
    /**
     * move a property from one instance to another instance
     * @param typeName
     * @param oldInstanceId
     * @param newInstanceId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void move(V typeName, K oldInstanceId, K newInstanceId, String property) throws StarFishMetaDataOperatingException;

    //delete
    /**
     * delete all node in typeName
     * @param typeName
     * @throws StarFishMetaDataOperatingException
     */
    void delete(V typeName) throws StarFishMetaDataOperatingException;

    /**
     * delete a node
     * @param typeName
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    void delete(V typeName, K instanceId) throws StarFishMetaDataOperatingException;

    /**
     * delete a property from a node
     * @param typeName
     * @param instanceId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void delete(V typeName, K instanceId, String property) throws StarFishMetaDataOperatingException;

    /**
     * batch delete nodes
     * @param typeName
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    void delete(V typeName, K... instanceIds) throws StarFishMetaDataOperatingException;
}
