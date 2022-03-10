package org.metahut.starfish.parser.function;

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

    abstract void add(E env,K id);

    abstract void delete(E env,K id);

    abstract void update(E env,K id);
}
