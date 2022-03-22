package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.instance.Environment;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;
import org.metahut.starfish.parser.function.AbstractEnvironmentService;
import org.metahut.starfish.parser.function.AbstractQueryCondition;

import javax.annotation.Resource;
import java.util.Collection;

/**
 *
 */
public class EnvironmentServiceImpl<E,K> extends AbstractEnvironmentService<E,K> {


    @Override
    public boolean valid(E... env) throws AbstractMetaParserException {
        return false;
    }

    @Override
    public void testUnderDevelopment(E env, boolean shouldBe) throws AbstractMetaParserException {

    }

    @Override
    public Environment<E> env(E env) throws AbstractMetaParserException {
        return null;
    }

    @Override
    public Environment<E> create() throws AbstractMetaParserException {
        return null;
    }

    @Override
    public Environment<E> copy(E env) throws AbstractMetaParserException {
        return null;
    }

    @Override
    public void modify(Environment<E> env) throws AbstractMetaParserException {

    }

    @Override
    public void delete(E env) throws AbstractMetaParserException {

    }

    @Override
    public Environment<E> merge(E... envs) throws AbstractMetaParserException {
        return null;
    }

    @Override
    public Collection<K> query(AbstractQueryCondition condition) {
        return null;
    }
}
