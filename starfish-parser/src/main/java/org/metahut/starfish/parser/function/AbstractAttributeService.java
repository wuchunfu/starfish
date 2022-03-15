package org.metahut.starfish.parser.function;

/**
 *
 * @param <E> env type
 */
public abstract class AbstractAttributeService<E> {

    /**
     * 创建校验 还是 保存后校验(perfect)
     * @param env
     */
    abstract void valid(E env/*,SfGraph graph */);


}
