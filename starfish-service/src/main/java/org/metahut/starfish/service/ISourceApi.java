package org.metahut.starfish.service;

import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import java.util.Map;

/**
 *
 */
interface ISourceApi<K,T> extends AbstractQueryService {

    /**
     *
     * @param name
     * @param properties
     * @return
     */
    K create(String name, Map<String,T> properties) throws AbstractMetaParserException;

    /**
     *
     * @param id
     * @param name
     * @param properties
     * @throws AbstractMetaParserException
     */
    void update(K id, String name,Map<String,T> properties) throws AbstractMetaParserException;

    /**
     *
     * @param id
     */
    void delete(K id);

    /**
     *
     * @param name
     * @return
     * @throws AbstractMetaParserException
     */
    @Deprecated
    K getIdByName(String name) throws AbstractMetaParserException;
}
