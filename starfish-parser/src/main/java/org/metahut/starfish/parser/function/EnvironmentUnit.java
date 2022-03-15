package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.SymbolConstants;
import org.metahut.starfish.parser.domain.instance.SfClass;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 必须单例 how
 * 解决多环境的问题？
 */
abstract class EnvironmentUnit<E> {

    /**
     * 需要绑定
     */
    private final Map<E,AtomicLong> versionHolder = new ConcurrentHashMap<>();

    /**
     * 环境工作用的 这个类
     * 1。包括class
     * 2。包括rel 等信息
     * 3。
     */
    private final Map<E,?> storage = new ConcurrentHashMap<>();

    /**
     * 环境装载 (包含更新和卸载 )
     */
    public abstract void load(E env, List<SfClass> structModels);

    /**
     *
     * @param env
     * @return
     */
    public abstract List<SfClass> search(E env);

    /**
     *
     * @param env
     * @param versionId
     * @return
     */
    abstract SfClass search(E env,long versionId);

    /**
     * 卸载环境
     * @param env
     */
    public abstract void unload(E env);

    public final synchronized String rewrite(E env) {
        AtomicLong variableContainer = versionHolder.get(env);
        if (variableContainer == null) {
            variableContainer = new AtomicLong();
            versionHolder.put(env,variableContainer);
        }
        return env + SymbolConstants.PACKAGE_SPLIT + SymbolConstants.PREFIX + variableContainer.incrementAndGet();
    }
}
