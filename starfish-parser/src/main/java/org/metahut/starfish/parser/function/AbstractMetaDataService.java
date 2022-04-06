package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.enums.Type;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.domain.instance.MetaResult;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import java.util.Map;
import java.util.Set;

/**
 * 拼接三大api
 * graph class typeNameironment
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
public abstract class AbstractMetaDataService<V,K,T> implements IMetaDataApi<V,K,T> {

    protected abstract IGraphApi<V,K,T> graphApi();

    protected abstract ITypeApi<V,T> classApi();

    protected abstract ITypeInstanceBridgeApi<V,K> classInstanceBridgeApi();

    @Override
    public void delete(V typeName) throws AbstractMetaParserException {
        classApi().delete(typeName);
        classInstanceBridgeApi().delete(typeName);
        graphApi().delete(typeName);
    }

    @Override
    public void copy(V toTypeName, V fromTypeName, long... classIds) throws AbstractMetaParserException {
        classApi().copy(toTypeName,fromTypeName,classIds);
    }

    @Override
    public void copy(V toTypeName, V fromTypeName, K... instanceIds) throws AbstractMetaParserException {
        classInstanceBridgeApi().copy(toTypeName,fromTypeName,instanceIds);
        graphApi().copy(toTypeName,fromTypeName,instanceIds);
    }


    @Override
    public void add(V typeName, Class... classes) throws AbstractMetaParserException {
        classApi().add(typeName,classes);
    }

    @Override
    public void modify(V typeName, Class... sfClass) throws AbstractMetaParserException {
        classApi().modify(typeName,sfClass);
    }

    @Override
    public void delete(V typeName, long... classIds) throws AbstractMetaParserException {
        classApi().delete(typeName,classIds);
    }

    @Override
    public V create() throws AbstractMetaParserException {
        return classApi().create();
    }

    @Override
    public MetaResult<V, K, T> all(V typeName) throws AbstractMetaParserException {
        MetaResult<V,K,T> result = new MetaResult<>();
        result.setGraph(graphApi().graph(typeName));
        result.setClassInfos(classApi().classes(typeName));
        result.setTypesInfos(classInstanceBridgeApi().query(typeName));
        return result;
    }

    @Override
    public K create(V typeName, long classId, String property, T obj) throws AbstractMetaParserException {
        classApi().valid(typeName,classId);
        return graphApi().create(typeName, property, obj);
    }

    @Override
    public K create(V typeName, long classId, Map<String, T> attributes) throws AbstractMetaParserException {
        classApi().valid(typeName,classId);
        return graphApi().create(typeName, attributes);
    }

    @Override
    public K create(V typeName, long classId, K parentInstanceId, String property, Map<String, T> attributes) throws AbstractMetaParserException {
        classApi().valid(typeName,classId);
        return graphApi().create(typeName,parentInstanceId,property,attributes);
    }

    @Override
    public void add(V typeName, K instanceId, String property, T obj) throws AbstractMetaParserException {
        Type<K> type = classInstanceBridgeApi().query(typeName, instanceId);
        classApi().query(typeName,type.getSerialVersionId());
        graphApi().add(typeName,instanceId,property,obj);

    }

    @Override
    public void link(V typeName, K headId, K tailId, String property) throws AbstractMetaParserException {
        Set<Type<K>> types = classInstanceBridgeApi().query(typeName, headId, tailId);
        long[] ids = types.stream().mapToLong(kSfType -> kSfType.getSerialVersionId()).toArray();
        classApi().query(typeName,ids);
    }

    @Override
    public void update(V typeName, K instanceId, Map<String, T> attributes) throws AbstractMetaParserException {
        Type<K> type = classInstanceBridgeApi().query(typeName, instanceId);
        classApi().query(typeName,type.getSerialVersionId());
        graphApi().update(typeName,instanceId,attributes);
    }

    @Override
    public void modify(V typeName, K instanceId, Map<String, T> attributes) throws AbstractMetaParserException {
        Type<K> type = classInstanceBridgeApi().query(typeName, instanceId);
        classApi().query(typeName,type.getSerialVersionId());
        graphApi().modify(typeName,instanceId,attributes);
    }

    @Override
    public void delete(V typeName, K instanceId, String property) throws AbstractMetaParserException {
        graphApi().delete(typeName,instanceId,property);
    }

    @Override
    public void crack(V typeName, K headId, K tailId, String property) throws AbstractMetaParserException {
        graphApi().crack(typeName,headId,tailId,property);
    }

    @Override
    public void delete(V typeName, K... instanceIds) throws AbstractMetaParserException {
        classInstanceBridgeApi().delete(typeName,instanceIds);
        graphApi().delete(typeName,instanceIds);
    }

    //TODO 校验部分还是全部，是先处理还是后处理
    private void valid(V typeName) throws AbstractMetaParserException {

    }
}
