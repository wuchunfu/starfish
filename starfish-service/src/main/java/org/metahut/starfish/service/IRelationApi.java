package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.instance.Relation;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Collection;
import java.util.List;

/**
 *
 */
public interface IRelationApi<V,K,T> extends AbstractQueryService<T> {
    /**
     * pId cId properties
     * TODO line ？
     * @param typeName
     * @return
     */
    List<Relation<K>> lines(V typeName) throws StarFishMetaDataQueryException;

    // link
    /**
     *
     * @param typeName
     * @param headId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void link(V typeName,K headId,K tailId,String property) throws StarFishMetaDataOperatingException;

    // crack
    /**
     * crack the relation between two nodes
     * @param typeName
     * @param headId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void crack(V typeName,K headId,K tailId,String property) throws StarFishMetaDataOperatingException;

    // delete
    /**
     * delete all relation in typeName
     * @param typeName
     * @throws StarFishMetaDataOperatingException
     */
    void delete(V typeName) throws StarFishMetaDataOperatingException;

    /**
     * delete all nodes direct rel to the appointed node
     * @param typeName
     * @param instanceId
     * @throws StarFishMetaDataOperatingException
     */
    void delete(V typeName,K instanceId) throws StarFishMetaDataOperatingException;

    /**
     * batch delete instances
     * @param typeName
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    void delete(V typeName, Collection<K> instanceIds) throws StarFishMetaDataOperatingException;

    // move

    /**
     * repoint the node to another node
     * @param typeName
     * @param oldHeadId
     * @param newHeadId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void move(V typeName,K oldHeadId,K newHeadId,K tailId,String property) throws StarFishMetaDataOperatingException;

    //copy

    /**
     * copy relations from one typeName to another typeName
     * @param oldTypeName
     * @param newTypeName
     * @param instanceIds
     * @throws StarFishMetaDataOperatingException
     */
    void copy(V oldTypeName, V newTypeName, Collection<K> instanceIds) throws StarFishMetaDataOperatingException;
}
