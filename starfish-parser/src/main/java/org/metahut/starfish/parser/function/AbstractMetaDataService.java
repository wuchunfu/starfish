package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.enums.Type;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.domain.instance.Environment;
import org.metahut.starfish.parser.domain.instance.MetaResult;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import java.util.Map;
import java.util.Set;

/**
 * 拼接三大api
 * graph class environment
 * TODO 最核心问题 valid
 * TODO 先校验：数据移动改写，但是意味着自己要进行数据维护，并且要保证数据格式一致性(难实现)
 * TODO 局部校验： ？
 * TODO 后校验：需要回滚
 * TODO 如果迁移准备json 节点迁移 带有新的数据格式 带有rootInstanceId
 * TODO 问题是class没有强关联
 * TODO 拷贝图会有问题 -> 局部校验意味着失效了（问题是带着层级校验）
 *  分开递归校验？
 *  如果只拷贝节点， 局部校验节点， 然后？
 */
public abstract class AbstractMetaDataService<E,K,T> implements IMetaDataApi<E,K,T> {

    protected abstract IGraphApi<E,K,T> graphApi();

    protected abstract IEnvironmentApi<E,T> environmentApi();

    protected abstract IClassApi<E,T> classApi();

    protected abstract IClassInstanceBridgeApi<E,K> classInstanceBridgeApi();

    @Override
    public void delete(E env) throws AbstractMetaParserException {
        environmentApi().delete(env);
        classApi().delete(env);
        classInstanceBridgeApi().delete(env);
        graphApi().delete(env);
    }

    @Override
    public Environment<E> copy(E env) throws AbstractMetaParserException {
        Environment<E> copy = environmentApi().copy(env);
        return copy;
    }

    @Override
    public void copy(E toEnv, E fromEnv, long... classIds) throws AbstractMetaParserException {
        environmentApi().valid(toEnv,fromEnv);
        environmentApi().testUnderDevelopment(toEnv,true);
        classApi().copy(toEnv,fromEnv,classIds);
    }

    @Override
    public void copy(E toEnv, E fromEnv, K... instanceIds) throws AbstractMetaParserException {
        environmentApi().valid(toEnv,fromEnv);
        environmentApi().testUnderDevelopment(toEnv,false);
        classInstanceBridgeApi().copy(toEnv,fromEnv,instanceIds);
        graphApi().copy(toEnv,fromEnv,instanceIds);
    }

    @Override
    public Environment<E> create() throws AbstractMetaParserException {
        return environmentApi().create();
    }

    @Override
    public void modify(Environment<E> env) throws AbstractMetaParserException {
        environmentApi().valid(env.getEnv());
        environmentApi().modify(env);
    }

    @Override
    public Environment<E> merge(E env1, E env2) throws AbstractMetaParserException {
        return environmentApi().merge(env1, env2);
    }

    @Override
    public void add(E env, Class... classes) throws AbstractMetaParserException {
        environmentApi().valid(env);
        environmentApi().testUnderDevelopment(env,true);
        classApi().add(env,classes);
    }

    @Override
    public void modify(E env, Class... sfClass) throws AbstractMetaParserException {
        environmentApi().valid(env);
        environmentApi().testUnderDevelopment(env,true);
        classApi().modify(env,sfClass);
    }

    @Override
    public void delete(E env, long... classIds) throws AbstractMetaParserException {
        environmentApi().valid(env);
        environmentApi().testUnderDevelopment(env,true);
        classApi().delete(env,classIds);
    }

    @Override
    public MetaResult<E, K, T> all(E env) throws AbstractMetaParserException {
        MetaResult<E,K,T> result = new MetaResult<>();
        result.setEnvironment(environmentApi().env(env));
        result.setGraph(graphApi().graph(env));
        result.setClassInfos(classApi().classes(env));
        result.setTypesInfos(classInstanceBridgeApi().query(env));
        return result;
    }

    @Override
    public K create(E env, long classId, String property, T obj) throws AbstractMetaParserException {
        environmentApi().valid(env);
        environmentApi().testUnderDevelopment(env,false);
        classApi().valid(env,classId);
        return graphApi().create(env, property, obj);
    }

    @Override
    public K create(E env, long classId, Map<String, T> attributes) throws AbstractMetaParserException {
        environmentApi().valid(env);
        environmentApi().testUnderDevelopment(env,false);
        classApi().valid(env,classId);
        return graphApi().create(env, attributes);
    }

    @Override
    public K create(E env, long classId, K parentInstanceId, String property, Map<String, T> attributes) throws AbstractMetaParserException {
        environmentApi().valid(env);
        environmentApi().testUnderDevelopment(env,false);
        classApi().valid(env,classId);
        return graphApi().create(env,parentInstanceId,property,attributes);
    }

    @Override
    public void add(E env, K instanceId, String property, T obj) throws AbstractMetaParserException {
        environmentApi().valid(env);
        environmentApi().testUnderDevelopment(env,false);
        Type<K> type = classInstanceBridgeApi().query(env, instanceId);
        classApi().query(env,type.getSerialVersionId());
        graphApi().add(env,instanceId,property,obj);

    }

    @Override
    public void link(E env, K headId, K tailId, String property) throws AbstractMetaParserException {
        environmentApi().valid(env);
        environmentApi().testUnderDevelopment(env,false);
        Set<Type<K>> types = classInstanceBridgeApi().query(env, headId, tailId);
        long[] ids = types.stream().mapToLong(kSfType -> kSfType.getSerialVersionId()).toArray();
        classApi().query(env,ids);
    }

    @Override
    public void update(E env, K instanceId, Map<String, T> attributes) throws AbstractMetaParserException {
        environmentApi().valid(env);
        environmentApi().testUnderDevelopment(env,false);
        Type<K> type = classInstanceBridgeApi().query(env, instanceId);
        classApi().query(env,type.getSerialVersionId());
        graphApi().update(env,instanceId,attributes);
    }

    @Override
    public void modify(E env, K instanceId, Map<String, T> attributes) throws AbstractMetaParserException {
        environmentApi().valid(env);
        environmentApi().testUnderDevelopment(env,false);
        Type<K> type = classInstanceBridgeApi().query(env, instanceId);
        classApi().query(env,type.getSerialVersionId());
        graphApi().modify(env,instanceId,attributes);
    }

    @Override
    public void delete(E env, K instanceId, String property) throws AbstractMetaParserException {
        environmentApi().valid(env);
        environmentApi().testUnderDevelopment(env,false);
        graphApi().delete(env,instanceId,property);
    }

    @Override
    public void crack(E env, K headId, K tailId, String property) throws AbstractMetaParserException {
        environmentApi().valid(env);
        environmentApi().testUnderDevelopment(env,false);
        graphApi().crack(env,headId,tailId,property);
    }

    @Override
    public void delete(E env, K... instanceIds) throws AbstractMetaParserException {
        environmentApi().valid(env);
        environmentApi().testUnderDevelopment(env,false);
        classInstanceBridgeApi().delete(env,instanceIds);
        graphApi().delete(env,instanceIds);
    }

    //TODO 校验部分还是全部，是先处理还是后处理
    private void valid(E env) throws AbstractMetaParserException {

    }
}
