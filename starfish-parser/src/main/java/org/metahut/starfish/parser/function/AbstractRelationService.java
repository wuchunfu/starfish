package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.SfLine;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Collection;
import java.util.List;

/**
 *
 */
public abstract class AbstractRelationService<E,K,T> extends AbstractQueryService<T> {
    /**
     * pId cId properties
     * TODO line ？
     * @param env
     * @return
     */
    abstract List<SfLine<K>> lines(E env) throws StarFishMetaDataQueryException;

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
     * delete all relation in env
     * @param env
     * @throws StarFishMetaDataOperatingException
     */
    abstract void delete(E env) throws StarFishMetaDataOperatingException;

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

    //copy
    /**
     * copy relations from one instance to another instance
     * @param oldEnv
     * @param newEnv
     * @param deleteOld
     * @throws StarFishMetaDataOperatingException
     */
    abstract void copy(E oldEnv,E newEnv,boolean deleteOld) throws StarFishMetaDataOperatingException;
}
