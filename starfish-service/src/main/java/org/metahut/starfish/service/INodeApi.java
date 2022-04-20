package org.metahut.starfish.service;

import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
interface INodeApi<K,T> extends AbstractQueryService<T> {

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

}
