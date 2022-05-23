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
import org.metahut.starfish.parser.exception.SourceNameNotPresentException;
import org.metahut.starfish.parser.exception.TypeExistsException;
import org.metahut.starfish.parser.exception.TypeNotPresentException;
import org.metahut.starfish.parser.exception.TypeValidException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private List<K> validBatchClassInfos(K sourceId,Collection<Class> updateOrCreatedClassInfos) {
        List<K> typeIds = new ArrayList<>();
        Map<K, Class> existTypes = typeApi().types(sourceId);
        for (Class newClassInfo : updateOrCreatedClassInfos) {
            if (StringUtils.isEmpty(newClassInfo.fullClassName())) {
                throw new TypeValidException("Null class name,classInfo is " + newClassInfo);
            }
            boolean exists = false;
            for (Map.Entry<K, Class> entry : existTypes.entrySet()) {
                if (entry.getValue().fullClassName().equals(newClassInfo.fullClassName())) {
                    exists = true;
                    entry.setValue(newClassInfo);
                    typeApi().update(entry.getKey(),newClassInfo,null);
                    break;
                }
            }
            if (!exists) {
                K typeId = typeApi().create(sourceId, newClassInfo, null);
                existTypes.put(typeId,newClassInfo);
                typeIds.add(typeId);
            }
        }
        List<String> allFullName = existTypes.values().stream().map(Class::fullClassName).collect(Collectors.toList());
        //valid depend
        existTypes.values().forEach(classInfo -> {
            if (classInfo.getAttributes() != null) {
                classInfo.getAttributes().stream().forEach(attribute -> {
                    if (RelType.ENTITY == attribute.getRelType()) {
                        if (!allFullName.contains(attribute.getClassName())) {
                            throw new TypeNotPresentException(attribute.getClassName());
                        }
                    }
                });
            }
        });
        return typeIds;
    }

    private Map<String,T> convertTo(Object object) {
        Map<String,T> map = new ObjectMapper().convertValue(object, Map.class);
        return map;
    }

    @Override
    public K initSourceAndType(BatchTypeBody<T> batchTypeBody) throws AbstractMetaParserException {
        K id = sourceApi().getIdByName(batchTypeBody.getSource().getName());
        K sourceId = createSource(batchTypeBody.getSource().getName(), batchTypeBody.getSource().getAttributes());
        List<K> typeIds = validBatchClassInfos(sourceId,batchTypeBody.getTypes());
        for (K typeId : typeIds) {
            linkApi().link(sourceId,typeId, LinkCategory.SOURCE_TYPE);
        }
        return sourceId;
    }

    @Override
    public K batchInstances(BatchInstanceBody batchInstanceBody) throws AbstractMetaParserException {
        //get all
        K sourceId = sourceApi().getIdByName(batchInstanceBody.getSourceName());
        if (sourceId == null) {
            throw new SourceNameNotPresentException();
        }
        Collection<K> typeIds = linkApi().findChildren(sourceId,LinkCategory.SOURCE_TYPE);
        Map<K, Class> types = typeApi().types(typeIds);
        for (Map.Entry<String, String> entry : batchInstanceBody.getInstances().entrySet()) {
            store(entry.getKey(),entry.getValue(),types);
        }
        return sourceId;
    }

    private void store(String typeName,String json,Map<K,Class> types) {
        InstanceAnalysisStruct<K,T> analysis = JsonExtensionVisitor.analysis(json, typeName, types);
        for (InstanceAnalysisStruct.Instance<K,T> instance : analysis.getInstances().values()) {
            K entityId = createEntity(instance.getTypeId(), instance.getName(), instance.getProperties());
            instance.setId(entityId);
        }
        for (InstanceAnalysisStruct.Link<K, T> link : analysis.getLinks()) {
            link(link.getHead().getId(),link.getTail().getId(),link.getProperty());
        }
    }

    @Override
    public <U> U source(K sourceId, AbstractQueryCondition<U> condition) throws AbstractMetaParserException {
        return sourceApi().source(sourceId,condition);
    }

    @Override
    public <U> Collection<U> sources(AbstractQueryCondition<U> condition) throws AbstractMetaParserException {
        return sourceApi().query(condition);
    }

    @Override
    public <U> Page<U> sources(AbstractQueryCondition<U> condition, Pageable page) throws AbstractMetaParserException {
        return sourceApi().query(condition,page);
    }

    @Override
    public Class type(K typeId) throws AbstractMetaParserException {
        return typeApi().type(typeId);
    }

    @Override
    public Collection<Class> types(K sourceId) throws AbstractMetaParserException {
        return typeApi().types(sourceId).values();
    }

    @Override
    public <U> U instance(K instanceId, AbstractQueryCondition<U> condition) throws AbstractMetaParserException {
        return graphApi().node(instanceId,condition);
    }

    @Override
    public <U> Collection<U> instances(K typeId, AbstractQueryCondition<U> condition) throws AbstractMetaParserException {
        Collection<K> instanceIds = linkApi().findChildren(typeId, LinkCategory.TYPE_ENTITY);
        return graphApi().nodes(instanceIds,condition);
    }

    @Override
    public <U> Collection<U> instancesByName(String typeName, AbstractQueryCondition<U> condition) throws AbstractMetaParserException {
        return instances(typeApi().getIdByName(typeName),condition);
    }

    @Override
    public <U> Page<U> instances(K typeId, AbstractQueryCondition<U> condition, Pageable page) throws AbstractMetaParserException {
        Collection<K> instanceIds = linkApi().findChildren(typeId, LinkCategory.TYPE_ENTITY);
        return graphApi().nodes(instanceIds,condition,page);
    }

    @Override
    public <U> Page<U> instancesByName(String typeName, AbstractQueryCondition<U> condition, Pageable page) throws AbstractMetaParserException {
        return instances(typeApi().getIdByName(typeName),condition,page);
    }

    @Override
    public <U> Collection<U> instances(K upperInstanceId, String property, AbstractQueryCondition<U> condition) throws AbstractMetaParserException {
        return graphApi().nodes(upperInstanceId,property,condition);
    }

    @Override
    public <U> Page<U> instances(K upperInstanceId, String property, AbstractQueryCondition<U> condition, Pageable page) throws AbstractMetaParserException {
        return graphApi().nodes(upperInstanceId,property,condition,page);
    }

    @Override
    public K createSource(String name, Map<String, T> properties) throws AbstractMetaParserException {
        return sourceApi().create(name,properties);
    }

    @Override
    public K createType(K sourceId, Class classInfo, Map<String, T> properties) throws AbstractMetaParserException {
        //TODO valid 创建时候两个type 要不要建立连接？
        List<K> typeIds = validBatchClassInfos(sourceId, Arrays.asList(classInfo));
        for (K typeId : typeIds) {
            linkApi().link(sourceId,typeId, LinkCategory.SOURCE_TYPE);
            return typeId;
        }
        throw new TypeExistsException(classInfo.fullClassName());
    }

    @Override
    public K createEntityByTypeName(String typeName, String name, Map<String, T> properties) throws AbstractMetaParserException {
        K typeId = typeApi().getIdByName(typeName);
        return createEntity(typeId,name,properties);
    }

    @Override
    public K createEntity(K typeId, String name, Map<String, T> properties) throws AbstractMetaParserException {
        //TODO valid
        K entityId = graphApi().createNode(name, properties);
        K sourceId = linkApi().findParent(typeId,LinkCategory.SOURCE_TYPE);
        linkApi().link(sourceId,entityId,LinkCategory.SOURCE_ENTITY);
        linkApi().link(typeId,entityId,LinkCategory.TYPE_ENTITY);
        return entityId;
    }

    @Override
    public K createEntity(K upperInstanceId, String propertyName, String name, Map<String, T> properties) throws AbstractMetaParserException {
        K sourceId = linkApi().findParent(upperInstanceId, LinkCategory.SOURCE_ENTITY);
        Map<K, Class> types = typeApi().types(sourceId);
        K typeId = linkApi().findParent(upperInstanceId, LinkCategory.TYPE_ENTITY);
        Class classInfo = types.get(typeId);
        Attribute attribute = classInfo.findAttributeByName(propertyName);
        String fullName = attribute.getClassName();
        Optional<K> optionalKey = types.entrySet().stream().filter(kClassEntry -> kClassEntry.getValue().fullClassName().equals(fullName)).map(kClassEntry -> kClassEntry.getKey()).findFirst();
        if (optionalKey.isPresent()) {
            K newEntityKey = createEntity(optionalKey.get(),name, properties);
            graphApi().link(upperInstanceId,newEntityKey,propertyName);
            return newEntityKey;
        } else {
            throw new TypeNotPresentException(fullName + " in Source:" + sourceId);
        }
    }

    @Override
    public void updateSource(K id, String name, Map<String, T> properties) throws AbstractMetaParserException {
        //TODO valid
        sourceApi().update(id,name,properties);
    }

    @Override
    public void updateType(K id, Class classInfo, Map<String, T> properties) throws AbstractMetaParserException {
        validBatchClassInfos(id,Arrays.asList(classInfo));
    }

    @Override
    public void updateEntity(K id, String name, Map<String, T> properties) throws AbstractMetaParserException {
        //TODO valid
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
    //TODO deep delete
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
}
