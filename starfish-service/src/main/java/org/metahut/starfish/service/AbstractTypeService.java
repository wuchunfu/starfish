package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import java.util.Collection;

/**
 * for implements
 */
public abstract class AbstractTypeService<V,T> implements ITypeApi<V,T> {

    @Override
    public V create() {
        return null;
    }

    @Override
    public Collection<Class> classes(V typeName) {
        return null;
    }

    @Override
    public Collection<Class> query(V typeName, long... classIds) {
        return null;
    }

    @Override
    public Class query(V typeName, long classId) {
        return null;
    }

    @Override
    public void valid(V typeName, long... classIds) throws AbstractMetaParserException {

    }

    @Override
    public void add(V typeName, Class... classes) {

    }

    @Override
    public void copy(V totypeName, V fromtypeName, long... classIds) throws AbstractMetaParserException {

    }

    @Override
    public V modify(V typeName, Class... classes) {
        return null;
    }

    @Override
    public void delete(V typeName) {

    }

    @Override
    public void delete(V typeName, long... classIds) {

    }

    @Override
    public Collection<T> query(AbstractQueryCondition condition) {
        return null;
    }
}
