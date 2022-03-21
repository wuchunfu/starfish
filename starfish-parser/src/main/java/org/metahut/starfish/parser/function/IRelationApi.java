package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.Relation;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.List;

/**
 *
 */
public interface IRelationApi<E,K,T> extends AbstractQueryService<T> {
    /**
     * pId cId properties
     * TODO line ？
     * @param env
     * @return
     */
    List<Relation<K>> lines(E env) throws StarFishMetaDataQueryException;

    // link
    /**
     *
     * @param env
     * @param headId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void link(E env,K headId,K tailId,String property) throws StarFishMetaDataOperatingException;

    // crack
    /**
     * crack the relation between two nodes
     * @param env
     * @param headId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void crack(E env,K headId,K tailId,String property) throws StarFishMetaDataOperatingException;

    // delete
    /**
     * delete all relation in env
     * @param env
     * @throws StarFishMetaDataOperatingException
     */
    void delete(E env) throws StarFishMetaDataOperatingException;

    /**
     * delete all nodes direct rel to the appointed node
     * @param env
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    void delete(E env,K instanceId) throws StarFishMetaDataOperatingException;

    /**
     * batch delete instances
     * @param env
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    void delete(E env, K... instanceIds) throws StarFishMetaDataOperatingException;

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
    void move(E env,K oldHeadId,K newHeadId,K tailId,String property) throws StarFishMetaDataOperatingException;

    //copy

    /**
     * copy relations from one env to another env
     * @param oldEnv
     * @param newEnv
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    void copy(E oldEnv,E newEnv,K... instanceIds) throws StarFishMetaDataOperatingException;
}
