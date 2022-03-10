package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 *  Graph [Node {Class:{properties}}] - line - Graph
 */
public abstract class AbstractInstanceService<K extends Comparable,E extends Comparable,T> extends AbstractQueryService<T> {

    /**
     * read all instance key info from env
     * @param env
     * @return
     */
    abstract Set<K> instanceMap(E env);

    public Future<Set<K>> instanceMap(Supplier<E> env) {
        return new FakeFuture<>(instanceMap(env.get()));
    }

    // valid
    /**
     * valid if it is all instance id illegal
     * @param env
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    abstract void valid(E env,K... instanceIds) throws StarFishMetaDataOperatingException;

    // create
    /**
     * create a empty node
     * @param env
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    abstract K create(E env) throws StarFishMetaDataOperatingException;

    /**
     * delete a node by id
     * @param env
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    abstract void delete(E env,K instanceId) throws StarFishMetaDataOperatingException;

    /**
     * batch delete instances
     * @param env
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    abstract void delete(E env, Collection<K> instanceIds) throws StarFishMetaDataOperatingException;

}
