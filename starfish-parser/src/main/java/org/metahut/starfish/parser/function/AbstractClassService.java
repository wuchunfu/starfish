package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.SfClass;

import java.util.Collection;
import java.util.List;

/**
 *
 */
public abstract class AbstractClassService<E,T> extends AbstractQueryService<T> {

    abstract SfClass query(E env,long classId);

    abstract void add(E env,SfClass... classes);

    /**
     * TODO 修改是问题 ？
     * @param env
     * @param classes
     * @return
     */
    abstract E modify(E env,List<SfClass> classes);

    abstract void delete(E env);

    abstract void delete(E env,long classId);


}
