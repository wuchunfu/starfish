package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.enums.SfType;

import java.util.Set;

/**
 *
 */
public abstract class AbstractRelToClassAndInstance<E,K> {

    abstract void save(E env,SfType<K> rel);

    abstract Set<SfType<K>> query(E env);

    abstract void delete(E env);

    abstract void delete(E env,SfType<K> rel);
}
