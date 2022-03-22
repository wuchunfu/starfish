package org.metahut.starfish.store.api;

import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 *
 */
public interface IEntityRepository<E,K,T> {


    /**
     * read all instance key info from env
     * @param env
     * @return
     */
    Set<K> instanceMap(E env);

    Future<Set<K>> instanceMap(Supplier<E> env);

    // valid
    /**
     * valid if it is all instance id illegal
     * @param env
     * @param instanceIds
     */
    void valid(E env,K... instanceIds);

    // create
    /**
     * create a empty node
     * @param env
     * @return
     */
    K create(E env);

    /**
     * copy from the env to another env
     * @param oldEnv
     * @param newEnv
     * @param deleteOld
     */
    void copy(E oldEnv,E newEnv,boolean deleteOld);

    // delete
    /**
     * delete all instance in env
     * @param env
     */
    void delete(E env);

    /**
     * delete a node by id
     * @param env
     * @param instanceId
     */
    void delete(E env,K instanceId);

    /**
     * batch delete instances
     * @param env
     * @param instanceIds
     */
    void delete(E env, K... instanceIds);
}
