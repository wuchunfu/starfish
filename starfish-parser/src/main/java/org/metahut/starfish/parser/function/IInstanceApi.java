package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 * Graph [Node {Class:{properties}}] - line - Graph
 */
interface IInstanceApi<E,K,T> extends AbstractQueryService<T> {

    /**
     * read all instance key info from env
     * @param env
     * @return
     */
    Set<K> instanceMap(E env) throws StarFishMetaDataQueryException;

    Future<Set<K>> instanceMap(Supplier<E> env) throws StarFishMetaDataQueryException;

    // valid
    /**
     * valid if it is all instance id illegal
     * @param env
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    void valid(E env,K... instanceIds) throws StarFishMetaDataOperatingException;

    // create
    /**
     * create a empty node
     * @param env
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    K create(E env) throws StarFishMetaDataOperatingException;

    /**
     * copy from the env to another env
     * @param oldEnv
     * @param newEnv
     * @param deleteOld
     * @throws StarFishMetaDataOperatingException
     */
    void copy(E oldEnv,E newEnv,boolean deleteOld) throws StarFishMetaDataOperatingException;

    // delete
    /**
     * delete all instance in env
     * @param env
     * @throws StarFishMetaDataOperatingException
     */
    void delete(E env) throws StarFishMetaDataOperatingException;

    /**
     * delete a node by id
     * @param env
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    void delete(E env,K instanceId) throws StarFishMetaDataOperatingException;

    /**
     * batch delete instances
     * @param env
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    void delete(E env, K... instanceIds) throws StarFishMetaDataOperatingException;

}
