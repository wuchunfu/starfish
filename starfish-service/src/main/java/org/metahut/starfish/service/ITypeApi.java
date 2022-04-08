package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import java.util.Collection;

/**
 *
 */
interface ITypeApi<V,T> extends AbstractQueryService<T>  {

    /**
     * create a type
     * @return
     */
    V create();

    /**
     * all classes in typeName
     * @param typeName
     * @return
     */
    Collection<Class> classes(V typeName);

    /**
     * query class by ids
     * @param typeName
     * @param classIds
     * @return
     */
    Collection<Class> query(V typeName,long... classIds);

    /**
     * query class
     * @param typeName
     * @param classId
     * @return
     */
    Class query(V typeName,long classId);

    /**
     * valid
     * @param typeName
     * @param classIds
     * @throws AbstractMetaParserException
     */
    void valid(V typeName,long... classIds) throws AbstractMetaParserException;

    /**
     * add classes to an typeName
     * @param typeName
     * @param classes
     */
    void add(V typeName, Class... classes);

    /**
     *
     * @param totypeName
     * @param fromtypeName
     * @param classIds
     * @throws AbstractMetaParserException
     */
    void copy(V totypeName, V fromtypeName, long... classIds) throws AbstractMetaParserException;

    /**
     * TODO 修改是问题 ？
     * @param typeName
     * @param classes
     * @return
     */
    V modify(V typeName, Class... classes);

    void delete(V typeName);

    void delete(V typeName,long... classIds);
}
