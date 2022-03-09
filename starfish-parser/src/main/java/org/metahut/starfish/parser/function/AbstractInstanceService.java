package org.metahut.starfish.parser.function;

import java.util.Set;

/**
 *  Graph [Node {Class:{properties}}] - line - Graph
 */
public abstract class AbstractInstanceService<K extends Comparable,E extends Comparable,T> implements AbstractQueryService<T> {

    /**
     * read all instance key info from env
     * @param env
     * @return
     */
    abstract Set<K> instanceMap(E env);


}
