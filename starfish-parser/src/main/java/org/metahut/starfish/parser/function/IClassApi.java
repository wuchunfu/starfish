package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.SfClass;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import java.util.Collection;

/**
 *
 */
interface IClassApi<E,T> extends AbstractQueryService<T>  {

    /**
     * all classes in env
     * @param env
     * @return
     */
    Collection<SfClass> classes(E env);

    /**
     * query class by ids
     * @param env
     * @param classIds
     * @return
     */
    Collection<SfClass> query(E env,long... classIds);

    /**
     * query class
     * @param env
     * @param classId
     * @return
     */
    SfClass query(E env,long classId);

    /**
     * valid
     * @param env
     * @param classIds
     * @throws AbstractMetaParserException
     */
    void valid(E env,long... classIds) throws AbstractMetaParserException;

    /**
     * add classes to an env
     * @param env
     * @param classes
     */
    void add(E env,SfClass... classes);

    /**
     *
     * @param toEnv
     * @param fromEnv
     * @param classIds
     * @throws AbstractMetaParserException
     */
    void copy(E toEnv, E fromEnv, long... classIds) throws AbstractMetaParserException;

    /**
     * TODO 修改是问题 ？
     * @param env
     * @param classes
     * @return
     */
    E modify(E env, SfClass... classes);

    void delete(E env);

    void delete(E env,long... classIds);
}
