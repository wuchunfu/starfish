package org.metahut.starfish.service;

import org.metahut.starfish.parser.antlr4.json.JsonExtensionVisitor;
import org.metahut.starfish.parser.domain.enums.LinkCategory;
import org.metahut.starfish.parser.domain.enums.RelType;
import org.metahut.starfish.parser.domain.instance.Attribute;
import org.metahut.starfish.parser.domain.instance.BatchInstanceBody;
import org.metahut.starfish.parser.domain.instance.BatchTypeBody;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.domain.instance.InstanceAnalysisStruct;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;
import org.metahut.starfish.parser.exception.InstanceNameNullException;
import org.metahut.starfish.parser.exception.SourceNameNotPresentException;
import org.metahut.starfish.parser.exception.TypeValidException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Transactional
public abstract class AbstractMetaDataService<K,T> implements IMetaDataApi<K,T> {

    protected abstract ISourceApi<K,T> sourceApi();

    protected abstract IGraphApi<K,T> graphApi();

    protected abstract ITypeApi<K,T> typeApi();

    protected abstract ILinkApi<K> linkApi();

    @Override
    public K initSourceAndType(BatchTypeBody<T> batchTypeBody) throws AbstractMetaParserException {
        K id = sourceApi().getIdByName(batchTypeBody.getSource().getName());
        if (id != null) {
            deleteSource(id);
        }
        K sourceId = createSource(batchTypeBody.getSource().getName(), batchTypeBody.getSource().getAttributes());
        for (Class type : batchTypeBody.getTypes()) {
            createType(sourceId,type,null);
        }
        return sourceId;
    }

    @Override
    public K batchInstances(BatchInstanceBody batchInstanceBody) throws AbstractMetaParserException {
        Map<String, K> classMap = new HashMap<>();
        //get all
        K sourceId = sourceApi().getIdByName(batchInstanceBody.getSourceName());
        if (sourceId == null) {
            throw new SourceNameNotPresentException();
        }
        Collection<K> typeIds = linkApi().findChildren(sourceId,LinkCategory.SOURCE_TYPE);
        Map<K, Class> types = typeApi().types(typeIds);
        Collection<Class> classInfos = types.values();
        types.entrySet().forEach(entry -> classMap.put(entry.getValue().fullClassName(),entry.getKey()));
        for (Map.Entry<String, String> entry : batchInstanceBody.getInstances().entrySet()) {
            InstanceAnalysisStruct<K,T> analysis = JsonExtensionVisitor.analysis(entry.getValue(), entry.getKey(), types);
            for (InstanceAnalysisStruct.Instance<K,T> instance : analysis.getInstances().values()) {
                K entityId = createEntity(instance.getTypeId(), instance.getName(), instance.getProperties());
                instance.setId(entityId);
            }
            for (InstanceAnalysisStruct.Link<K, T> link : analysis.getLinks()) {
                link(link.getHead().getId(),link.getTail().getId(),link.getProperty());
            }
        }
        return sourceId;
    }

    @Override
    public List<?> sources(AbstractQueryCondition condition) throws AbstractMetaParserException {
        return null;
    }

    @Override
    public Page<?> sources(AbstractQueryCondition condition, Pageable page) throws AbstractMetaParserException {
        return null;
    }

    @Override
    public List<?> types(K sourceId, AbstractQueryCondition condition) throws AbstractMetaParserException {
        return null;
    }

    @Override
    public List<?> types(K sourceId, AbstractQueryCondition condition, Pageable page) throws AbstractMetaParserException {
        return null;
    }

    @Override
    public List<?> instances(K typeId, AbstractQueryCondition condition) throws AbstractMetaParserException {
        return null;
    }

    @Override
    public Page<?> instances(K typeId, AbstractQueryCondition condition, Pageable page) throws AbstractMetaParserException {
        return null;
    }

    @Override
    public List<?> instances(K upperTypeId, String property, AbstractQueryCondition condition) throws AbstractMetaParserException {
        return null;
    }

    @Override
    public Page<?> instances(K upperTypeId, String property, AbstractQueryCondition condition, Pageable page) throws AbstractMetaParserException {
        return null;
    }

    @Override
    public K createSource(String name, Map<String, T> properties) throws AbstractMetaParserException {
        return sourceApi().create(name,properties);
    }

    @Override
    public K createType(K sourceId, Class classInfo, Map<String, T> properties) throws AbstractMetaParserException {
        //TODO valid 创建时候两个type 要不要建立连接？
        K typeId = typeApi().create(sourceId,classInfo,properties);
        linkApi().link(sourceId,typeId, LinkCategory.SOURCE_TYPE);
        return typeId;
    }

    private K createEntity(K typeId,String name,Map<String,T> properties) throws AbstractMetaParserException {
        K entityId = graphApi().createNode(name, properties);
        K sourceId = linkApi().findParent(typeId,LinkCategory.SOURCE_TYPE);
        linkApi().link(sourceId,entityId,LinkCategory.SOURCE_ENTITY);
        linkApi().link(typeId,entityId,LinkCategory.TYPE_ENTITY);
        return entityId;
    }

    @Override
    public K createEntity(K typeId, Map<String, T> properties) throws AbstractMetaParserException {
        Class type = typeApi().type(typeId);
        String name = validAndObtainName(type,properties);
        return createEntity(typeId,name,properties);
    }

    @Override
    public void updateSource(K id, String name, Map<String, T> properties) throws AbstractMetaParserException {
        //TODO valid
        sourceApi().update(id,name,properties);
    }

    @Override
    public void updateType(K id, Class classInfo, Map<String, T> properties) throws AbstractMetaParserException {
        //TODO valid
        //1. valid all entity implements this type
        //2.
        typeApi().update(id,classInfo,properties);
    }

    @Override
    public void updateEntity(K id, Map<String, T> properties) throws AbstractMetaParserException {
        K typeId = linkApi().findParent(id,LinkCategory.TYPE_ENTITY);
        Class type = typeApi().type(typeId);
        String name = validAndObtainName(type,properties);
        graphApi().updateNode(id,name,properties);
    }

    @Override
    public void deleteSource(K id) throws AbstractMetaParserException {
        Map<LinkCategory, Collection<K>> children = linkApi().findChildren(id);
        List<K> ids = new ArrayList<>();
        if (children != null) {
            for (Map.Entry<LinkCategory, Collection<K>> entry : children.entrySet()) {
                LinkCategory linkCategory = entry.getKey();
                Collection<K> childrenIds = entry.getValue();
                if (linkCategory != null && childrenIds != null && childrenIds.size() > 0) {
                    ids.addAll(childrenIds);
                    switch (linkCategory) {
                        case SOURCE_TYPE:
                            deleteType(ids);
                            break;
                        case SOURCE_ENTITY:
                            //deleteEntity(childrenIds);
                            break;
                        default:
                    }
                }
            }
        }
        sourceApi().delete(id);
        linkApi().deleteLinkRelatedToIds(ids);
    }

    @Override
    public void deleteType(K id) throws AbstractMetaParserException {
        //TODO valid  find other type whether import this type
        //TODO delete all entity implements this type
        Map<LinkCategory, Collection<K>> children = linkApi().findChildren(id);
        List<K> ids = new ArrayList<>();
        if (children != null) {
            for (Map.Entry<LinkCategory, Collection<K>> entry : children.entrySet()) {
                LinkCategory linkCategory = entry.getKey();
                Collection<K> childrenIds = entry.getValue();
                if (linkCategory != null && childrenIds != null && childrenIds.size() > 0) {
                    ids.addAll(childrenIds);
                    switch (linkCategory) {
                        case TYPE_ENTITY:
                            deleteEntity(childrenIds);
                            break;
                        default:
                    }
                }
            }
        }
        typeApi().delete(id);
        linkApi().deleteLinkRelatedToIds(ids);
    }

    @Override
    public void deleteType(Collection<K> ids) throws AbstractMetaParserException {
        if (ids != null) {
            for (K id : ids) {
                deleteType(id);
            }
        }
    }

    @Override
    public void deleteEntity(K id) throws AbstractMetaParserException {
        graphApi().deleteNode(id);
        linkApi().deleteLinkRelatedToIds(Arrays.asList(id));
    }

    @Override
    public void deleteEntity(Collection<K> ids) throws AbstractMetaParserException {
        graphApi().deleteNodes(ids);
        linkApi().deleteLinkRelatedToIds(ids);
    }

    @Override
    public void copy(String toSourceName, String fromSourceName, Collection<K> ids) throws AbstractMetaParserException {

    }

    @Override
    public void move(String toSourceName, String fromSourceName, Collection<K> ids) throws AbstractMetaParserException {

    }

    @Override
    public void link(K headId, K tailId, String property) throws AbstractMetaParserException {
        graphApi().link(headId,tailId,property);
    }

    @Override
    public void crack(K headId, K tailId, String property) throws AbstractMetaParserException {
        graphApi().crack(headId,tailId,property);
    }

    private String validAndObtainName(Class typeInfo,Map<String,T> properties) {
        Map<String,T> result = new HashMap<>();
        String name = null;
        for (Attribute attribute : typeInfo.getAttributes()) {
            if (RelType.PRIMITIVE == attribute.getRelType()) {
                T remove = properties.remove(attribute.getName());
                if (typeInfo.getNameAttributeRel().equals(attribute.getName())) {
                    name = String.valueOf(remove);
                } else {
                    result.put(attribute.getName(),remove);
                }
            }
        }
        properties.clear();
        properties.putAll(result);
        if (StringUtils.isBlank(name)) {
            throw new InstanceNameNullException();
        }
        return name;
    }
}
