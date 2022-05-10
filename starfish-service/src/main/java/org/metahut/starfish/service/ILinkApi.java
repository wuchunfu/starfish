package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.enums.LinkCategory;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
interface ILinkApi<K> {

    /**
     * link two diff link type nodes
     * @param parentId
     * @param childId
     * @param linkCategory
     * @throws AbstractMetaParserException
     */
    void link(K parentId,K childId, LinkCategory linkCategory) throws AbstractMetaParserException;

    /**
     * delete all link info related to ids
     * @param headOrTailIds
     * @throws AbstractMetaParserException
     */
    void deleteLinkRelatedToIds(Collection<K> headOrTailIds) throws AbstractMetaParserException;

    /**
     *
     * @param childId
     * @param linkCategory
     * @return
     * @throws AbstractMetaParserException
     */
    K findParent(K childId,LinkCategory linkCategory) throws AbstractMetaParserException;

    /**
     *
     * @param childId
     * @return
     * @throws AbstractMetaParserException
     */
    Map<LinkCategory,K> findParents(K childId) throws AbstractMetaParserException;

    /**
     *
     * @param parentId
     * @return
     * @throws AbstractMetaParserException
     */
    Map<LinkCategory, Collection<K>> findChildren(K parentId) throws AbstractMetaParserException;

    /**
     *
     * @param parentId
     * @return
     * @throws AbstractMetaParserException
     */
    Collection<K> findChildren(K parentId,LinkCategory linkCategory) throws AbstractMetaParserException;
}
