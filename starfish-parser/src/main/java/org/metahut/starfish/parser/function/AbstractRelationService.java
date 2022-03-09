package org.metahut.starfish.parser.function;

import java.util.List;

/**
 *
 */
public abstract class AbstractRelationService<K extends Comparable>{
    /**
     *  pId cId properties
     * @param env
     * @return
     */
    abstract List<?> lines(String env);


    abstract void link(String pId,String cId);
    //itetator

    abstract K crack(K pId,String attribute);

    abstract K crack(K pId,K cId);
}
