package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.enums.Type;

import java.util.Set;

/**
 *
 */
public abstract class AbstractClassInstanceBridgeService<E,K> {

    abstract void save(E env, Type<K> rel);

    abstract Set<Type<K>> query(E env);

    abstract void delete(E env);

    abstract void delete(E env, Type<K> rel);
}
