package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
interface ITypeApi<K,T> extends AbstractQueryService  {

    K create(K sourceId,Class classInfo, Map<String,T> properties) throws AbstractMetaParserException;

    void update(K id,Class classInfo,Map<String,T> properties) throws AbstractMetaParserException;

    void delete(K id) throws AbstractMetaParserException;

    void delete(Collection<K> ids) throws AbstractMetaParserException;

    Collection<Class> types(K sourceId);

    Map<K,Class> types(Collection<K> typeIds);

    K getIdByName(String name);
}
