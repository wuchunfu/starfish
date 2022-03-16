package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.SfEnvironment;
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

    SfEnvironment<E> env(E env) throws AbstractMetaParserException;

    SfEnvironment<E> create() throws AbstractMetaParserException;

    SfEnvironment<E> copy(E env) throws AbstractMetaParserException;

    void modify(SfEnvironment<E> env) throws AbstractMetaParserException;

    void delete(E env) throws AbstractMetaParserException;

    SfEnvironment<E> merge(E... envs) throws AbstractMetaParserException;
}
