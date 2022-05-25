package org.metahut.starfish.service;

import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
interface IGraphApi<K,T> extends AbstractQueryService {

    <U> U node(K instanceId,java.lang.Class<U> returnType) throws AbstractMetaParserException;

    <U> Collection<U> nodes(Collection<K> instanceIds,java.lang.Class<U> returnType) throws AbstractMetaParserException;

    <U> Collection<U> nodes(K upperInstanceId,String property,java.lang.Class<U> returnType) throws AbstractMetaParserException;

    <U> Page<U> nodes(Collection<K> instanceIds, Pageable page,java.lang.Class<U> returnType) throws AbstractMetaParserException;

    <U> Page<U> nodes(K upperInstanceId,String property, Pageable page,java.lang.Class<U> returnType) throws AbstractMetaParserException;

    K createNode(String name,Map<String,T> properties) throws AbstractMetaParserException;

    void updateNode(K id,String name,Map<String,T> properties) throws AbstractMetaParserException;

    void deleteNode(K id) throws AbstractMetaParserException;

    void deleteNodes(Collection<K> ids) throws AbstractMetaParserException;

    void link(K headId,K tailId,String property) throws AbstractMetaParserException;

    void crack(K headId,K tailId,String property) throws AbstractMetaParserException;

    void deleteRelation(K id) throws AbstractMetaParserException;

    void deleteRelationByHeadId(K headId) throws AbstractMetaParserException;

    void deleteRelationByTailId(K tailId) throws AbstractMetaParserException;

    void deleteRelationRelatedToIds(Collection<K> ids) throws AbstractMetaParserException;

}
