package org.metahut.starfish.service;

import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 * Graph [Node {Class:{properties}}] - line - Graph
 */
interface IInstanceApi<V,K,T> extends AbstractQueryService<T> {

    /**
     * read all instance key info from typeName
     * @param typeName
     * @return
     */
    Set<K> instanceMap(V typeName) throws StarFishMetaDataQueryException;

    Future<Set<K>> instanceMap(Supplier<V> typeName) throws StarFishMetaDataQueryException;

    // valid
    /**
     * valid if it is all instance id illegal
     * @param typeName
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    void valid(V typeName,K... instanceIds) throws StarFishMetaDataOperatingException;

    // create
    /**
     * create a empty node
     * @param typeName
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    K create(V typeName) throws StarFishMetaDataOperatingException;

    /**
     * copy from the typeName to another typeName
     * @param oldtypeName
     * @param newtypeName
     * @param deleteOld
     * @throws StarFishMetaDataOperatingException
     */
    void copy(V oldtypeName, V newtypeName,boolean deleteOld) throws StarFishMetaDataOperatingException;

    // delete
    /**
     * delete all instance in typeName
     * @param typeName
     * @throws StarFishMetaDataOperatingException
     */
    void delete(V typeName) throws StarFishMetaDataOperatingException;

    /**
     * delete a node by id
     * @param typeName
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    void delete(V typeName,K instanceId) throws StarFishMetaDataOperatingException;

    /**
     * batch delete instances
     * @param typeName
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    void delete(V typeName, K... instanceIds) throws StarFishMetaDataOperatingException;

}
