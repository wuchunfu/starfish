package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.enums.LinkCategory;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;

import java.util.Collection;

/**
 *
 */
public interface IRelationApi<K,T> extends AbstractQueryService {
    // link
    /**
     *
     * @param headId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void link(K headId,K tailId, LinkCategory linkCategory,String property) throws StarFishMetaDataOperatingException;

    // crack
    /**
     * crack the relation between two nodes
     * @param headId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void crack(K headId,K tailId,String property) throws StarFishMetaDataOperatingException;

    // delete
    /**
     * delete by id
     * @param id
     * @throws StarFishMetaDataOperatingException
     */
    void delete(K id) throws StarFishMetaDataOperatingException;

    /**
     * delete all nodes direct rel to the appointed node
     * @param ids
     * @throws StarFishMetaDataOperatingException
     */
    void delete(Collection<K> ids) throws StarFishMetaDataOperatingException;

    /**
     * delete
     * @param headId
     * @throws StarFishMetaDataOperatingException
     */
    void deleteByHeadId(K headId) throws StarFishMetaDataOperatingException;

    /**
     *
     * @param tailId
     * @throws StarFishMetaDataOperatingException
     */
    void deleteByTailId(K tailId) throws StarFishMetaDataOperatingException;

    /**
     * delete relations
     * @param headOrTailIds
     * @throws StarFishMetaDataOperatingException
     */
    void deleteRelationRelatedToIds(Collection<K> headOrTailIds) throws StarFishMetaDataOperatingException;

    // move
    /**
     * repoint the node to another node
     * @param oldHeadId
     * @param newHeadId
     * @param tailId
     * @param property
     * @throws StarFishMetaDataOperatingException
     */
    void move(K oldHeadId,K newHeadId,K tailId,String property) throws StarFishMetaDataOperatingException;

    Collection<K> findChildren(K headId,LinkCategory category,String property) throws StarFishMetaDataOperatingException;
}
