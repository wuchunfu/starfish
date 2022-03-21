package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.Environment;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;

/**
 *
 */
interface IEnvironmentApi<E,T> extends AbstractQueryService<T> {

    boolean valid(E... env) throws AbstractMetaParserException;

    /**
     * return this env whether under development
     * @param env
     * @return
     * @throws AbstractMetaParserException
     */
    void testUnderDevelopment(E env,boolean shouldBe) throws AbstractMetaParserException;

    Environment<E> env(E env) throws AbstractMetaParserException;

    Environment<E> create() throws AbstractMetaParserException;

    Environment<E> copy(E env) throws AbstractMetaParserException;

    void modify(Environment<E> env) throws AbstractMetaParserException;

    void delete(E env) throws AbstractMetaParserException;

    Environment<E> merge(E... envs) throws AbstractMetaParserException;
}
