package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.enums.SfType;

import java.util.Collection;
import java.util.Set;

/**
 *
 */
interface IClassInstanceBridgeApi<E,K> {
    void save(E env, SfType<K> rel);

    Set<SfType<K>> query(E env);

    SfType<K> query(E env,K instanceId);

    Set<SfType<K>> query(E env,K... instanceId);

    /**
     * copy all c
     * @param toEnv
     * @param fromEnv
     * @param instanceIds
     * @return
     */
    Collection<Long> copy(E toEnv,E fromEnv,K... instanceIds);

    void delete(E env);

    void delete(E env,K... instanceIds);

    void delete(E env,SfType<K> rel);
}
