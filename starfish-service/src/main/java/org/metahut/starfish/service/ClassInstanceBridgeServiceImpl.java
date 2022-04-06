package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.enums.Type;
import org.metahut.starfish.parser.function.AbstractClassInstanceBridgeService;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

/**
 *
 */
@Service
public class ClassInstanceBridgeServiceImpl<E,K> extends AbstractClassInstanceBridgeService<E,K> {

    @Override
    public void save(E env, Type<K> rel) {

    }

    @Override
    public Set<Type<K>> query(E env) {
        return null;
    }

    @Override
    public Type<K> query(E env, K instanceId) {
        return null;
    }

    @Override
    public Set<Type<K>> query(E env, K... instanceId) {
        return null;
    }

    @Override
    public Collection<Long> copy(E toEnv, E fromEnv, K... instanceIds) {
        return null;
    }

    @Override
    public void delete(E env) {

    }

    @Override
    public void delete(E env, K... instanceIds) {

    }

    @Override
    public void delete(E env, Type<K> rel) {

    }
}
