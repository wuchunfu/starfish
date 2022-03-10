package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.SfLine;

import java.util.Collection;
import java.util.List;

/**
 *
 */
public abstract class AbstractRelationService<K extends Comparable,E extends Comparable,T> extends AbstractQueryService<T> {
    /**
     * pId cId properties
     * TODO line ？
     * @param env
     * @return
     */
    abstract List<SfLine<K>> lines(E env);

    /**
     *
     * @param env
     * @param headId
     * @param tailId
     * @param property
     */
    abstract void link(E env,K headId,K tailId,String property);

    /**
     *
     * @param env
     * @param headId
     * @param tailId
     * @param property
     */
    abstract void crack(E env,K headId,K tailId,String property);

    /**
     *
     * @param env
     * @param instanceId
     */
    abstract void delete(E env,K instanceId);

    /**
     *
     * @param env
     * @param instanceIds
     */
    abstract void delete(E env, Collection<K> instanceIds);

    /**
     *
     * @param env
     * @param oldHeadId
     * @param newHeadId
     * @param tailId
     * @param property
     */
    abstract void move(E env,K oldHeadId,K newHeadId,K tailId,String property);

}
