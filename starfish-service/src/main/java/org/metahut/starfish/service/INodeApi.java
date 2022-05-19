package org.metahut.starfish.service;

import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
interface INodeApi<K,T> extends AbstractQueryService {

    /**
     * create node
     * @param name
     * @param properties
     * @return
     * @throws AbstractMetaParserException
     */
    K create(String name,Map<String,T> properties) throws AbstractMetaParserException;

    /**
     * update info
     * @param id
     * @param name
     * @param properties
     * @throws AbstractMetaParserException
     */
    void update(K id,String name,Map<String,T> properties) throws AbstractMetaParserException;

    /**
     * delete  by id
     * @param id
     * @throws AbstractMetaParserException
     */
    void delete(K id) throws AbstractMetaParserException;

    /**
     * delete batch ids
     * @param ids
     * @throws AbstractMetaParserException
     */
    void delete(Collection<K> ids) throws AbstractMetaParserException;

    <U> Collection<U> nodes(Collection<K> nodeIds,AbstractQueryCondition<U> condition) throws AbstractMetaParserException;

    <U> Page<U> nodes(Collection<K> nodeIds,AbstractQueryCondition<U> condition, Pageable page) throws AbstractMetaParserException;

}
