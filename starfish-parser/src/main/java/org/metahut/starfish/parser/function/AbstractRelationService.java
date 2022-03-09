package org.metahut.starfish.parser.function;

import java.util.List;

/**
 *
 */
public abstract class AbstractRelationService<K extends Comparable,E extends Comparable,T> extends AbstractQueryService<T> {
    /**
     *  pId cId properties
     * @param env
     * @return
     */
    abstract List<?> lines(E env);

    abstract void link(E env,K pId,K cId);

    //itetator
    abstract K crack(E env,K pId,String attribute);

    abstract K crack(E env,K pId,K cId);
}
