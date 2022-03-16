package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.SfClass;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import java.util.Collection;

/**
 *
 */
interface IClassApi<E,T> extends AbstractQueryService<T>  {

    Collection<SfClass> classes(E env);

    Collection<SfClass> query(E env,long... classIds);

    SfClass query(E env,long classId);

    void valid(E env,long classId) throws AbstractMetaParserException;

    void add(E env,SfClass... classes);

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
