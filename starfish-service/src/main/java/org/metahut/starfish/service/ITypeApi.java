package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
interface ITypeApi<K,T> extends AbstractQueryService<T>  {

    K create(K sourceId,Class classInfo, Map<String,T> properties) throws AbstractMetaParserException;

    void update(K id,Class classInfo,Map<String,T> properties) throws AbstractMetaParserException;

    void delete(K id) throws AbstractMetaParserException;

    void delete(Collection<K> ids) throws AbstractMetaParserException;
}
