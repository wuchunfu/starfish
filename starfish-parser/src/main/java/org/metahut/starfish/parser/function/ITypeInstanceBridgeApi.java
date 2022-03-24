package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.enums.Type;

import java.util.Collection;
import java.util.Set;

/**
 *
 */
interface ITypeInstanceBridgeApi<E,K> {
    void save(E env, Type<K> rel);

    Set<Type<K>> query(E env);

    Type<K> query(E env,K instanceId);

    Set<Type<K>> query(E env,K... instanceId);

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

    void delete(E env, Type<K> rel);
}
