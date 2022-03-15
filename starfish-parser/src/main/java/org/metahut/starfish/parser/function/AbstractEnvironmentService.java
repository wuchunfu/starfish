package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.SfEnvironment;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;

import java.util.Collection;

/**
 * 好像有点脱离元数据了
 *
 * 但是这个是最上层的api 更类似业务api
 * so move to sf api
 */
public abstract class AbstractEnvironmentService<E,T> extends AbstractQueryService<T>{

    /**
     * valid whether the env exists
     * @param env
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    abstract boolean valid(E... env) throws StarFishMetaDataOperatingException;

    /**
     * return this env whether under development
     * @param env
     * @return
     * @throws StarFishMetaDataOperatingException
     */
    abstract void testUnderDevelopment(E env,boolean shouldBe) throws StarFishMetaDataOperatingException;

    abstract SfEnvironment<E> create() throws StarFishMetaDataOperatingException;

    abstract SfEnvironment<E> copy(E env) throws StarFishMetaDataOperatingException;

    abstract void modify(SfEnvironment<E> env) throws StarFishMetaDataOperatingException;

    abstract void delete(E env) throws StarFishMetaDataOperatingException;

    abstract SfEnvironment<E> merge(E... envs) throws StarFishMetaDataOperatingException;
}
