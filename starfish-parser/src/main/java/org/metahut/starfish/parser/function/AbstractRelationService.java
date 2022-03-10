package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.SfLine;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;

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

    // link
    /**
     *
     * @param env
     * @param headId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    abstract void link(E env,K headId,K tailId,String property) throws StarFishMetaDataOperatingException;

    // crack
    /**
     * crack the relation between two nodes
     * @param env
     * @param headId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    abstract void crack(E env,K headId,K tailId,String property) throws StarFishMetaDataOperatingException;

    // delete
    /**
     * delete all nodes direct rel to the appointed node
     * @param env
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    abstract void delete(E env,K instanceId) throws StarFishMetaDataOperatingException;

    /**
     * batch delete instances
     * @param env
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    abstract void delete(E env, Collection<K> instanceIds) throws StarFishMetaDataOperatingException;

    // move

    /**
     * repoint the node to another node
     * @param env
     * @param oldHeadId
     * @param newHeadId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    abstract void move(E env,K oldHeadId,K newHeadId,K tailId,String property) throws StarFishMetaDataOperatingException;

}
