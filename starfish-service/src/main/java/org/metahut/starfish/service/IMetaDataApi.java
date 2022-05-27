package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.instance.BatchInstanceBody;
import org.metahut.starfish.parser.domain.instance.BatchTypeBody;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;
import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.row.RowData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Map;

/**
 * TODO query
 */
interface IMetaDataApi<K,T> {

    /**
     *
     * @throws AbstractMetaParserException
     */
    K initSourceAndType(BatchTypeBody<T> batchTypeBody) throws AbstractMetaParserException;

    /**
     *
     * batch create instances
     * @param batchInstanceBody
     * @return
     * @throws AbstractMetaParserException
     */
    K batchInstances(BatchInstanceBody batchInstanceBody) throws AbstractMetaParserException;

    /**
     * batch create or update by qualifyName and typeName
     * @param rowData
     * @return
     * @throws AbstractMetaParserException
     */
    void batchCreateOrUpdate(RowData<T> rowData) throws AbstractMetaParserException;

    /**
     *
     * @param sourceId
     * @param classInfo
     * @param <U>
     * @return
     * @throws AbstractMetaParserException
     */
    <U> U source(K sourceId,java.lang.Class<U> classInfo) throws AbstractMetaParserException;

    /**
     * query all source with conditions
     * @param condition conditions which can be null
     * @return
     * @throws AbstractMetaParserException
     */
    <U> Collection<U> sources(AbstractQueryCondition<U> condition) throws AbstractMetaParserException;

    /**
     * query all sources with conditions (page)
     * @param condition
     * @param page
     * @return
     * @throws AbstractMetaParserException
     */
    <U> Page<U> sources(AbstractQueryCondition<U> condition,Pageable page) throws AbstractMetaParserException;

    /**
     *
     * @param typeId
     * @return
     * @throws AbstractMetaParserException
     */
    Class type(K typeId) throws AbstractMetaParserException;

    /**
     * query all types with conditions
     * @param sourceId
     * @return
     * @throws AbstractMetaParserException
     */
    Collection<Class> types(K sourceId) throws AbstractMetaParserException;

    /**
     * find instance by id
     * TODO how about relation
     * @param returnType
     * @param instanceId
     * @param <U>
     * @throws AbstractMetaParserException
     */
    <U> U instance(K instanceId,java.lang.Class<U> returnType) throws AbstractMetaParserException;

    /**
     * find instances by condition
     * @param condition
     * @param <U>
     * @return
     * @throws AbstractMetaParserException
     */
    <U> Collection<U> instances(AbstractQueryCondition<U> condition) throws AbstractMetaParserException;

    /**
     * find instances by condition
     * @param condition
     * @param page
     * @param <U>
     * @return
     * @throws AbstractMetaParserException
     */
    <U> Page<U> instances(AbstractQueryCondition<U> condition,Pageable page) throws AbstractMetaParserException;

    /**
     * query all instances with conditions
     * @param typeId
     * @param returnType
     * @return
     * @throws AbstractMetaParserException
     */
    <U> Collection<U> instances(K typeId,java.lang.Class<U> returnType) throws AbstractMetaParserException;

    /**
     *
     * @param typeName
     * @param returnType
     * @param <U>
     * @return
     * @throws AbstractMetaParserException
     */
    <U> Collection<U> instancesByTypeName(String typeName,java.lang.Class<U> returnType) throws AbstractMetaParserException;

    /**
     * query all instances with conditions (page)
     * @param typeId
     * @param returnType
     * @param page
     * @return
     * @throws AbstractMetaParserException
     */
    <U> Page<U> instances(K typeId,Pageable page,java.lang.Class<U> returnType) throws AbstractMetaParserException;

    /**
     *
     * @param typeName
     * @param returnType
     * @param page
     * @param <U>
     * @return
     * @throws AbstractMetaParserException
     */
    <U> Page<U> instancesByTypeName(String typeName, Pageable page,java.lang.Class<U> returnType) throws AbstractMetaParserException;

    /**
     *
     * @param upperInstanceId
     * @param property
     * @param returnType
     * @return
     * @throws AbstractMetaParserException
     */
    <U> Collection<U> instances(K upperInstanceId,String property,java.lang.Class<U> returnType) throws AbstractMetaParserException;

    /**
     *
     * @param upperInstanceId
     * @param property
     * @param returnType
     * @param page
     * @return
     * @throws AbstractMetaParserException
     */
    <U> Page<U> instances(K upperInstanceId,String property,Pageable page,java.lang.Class<U> returnType) throws AbstractMetaParserException;

    /**
     * create a source with properties
     * @param name
     * @param properties
     * @return
     */
    K createSource(String name,Map<String,T> properties) throws AbstractMetaParserException;

    /**
     * create a type with properties under a source
     * @param sourceId
     * @param classInfo
     * @param properties
     * @return
     */
    K createType(K sourceId,Class classInfo,Map<String,T> properties) throws AbstractMetaParserException;

    /**
     *
     * @param typeName
     * @param name
     * @param properties
     * @return
     * @throws AbstractMetaParserException
     */
    K createEntityByTypeName(String typeName,String name,Map<String,T> properties) throws AbstractMetaParserException;

    /**
     * create a entity of type
     * @param typeId
     * @param name
     * @param properties
     * @return
     * @throws AbstractMetaParserException
     */
    K createEntity(K typeId,String name,Map<String,T> properties) throws AbstractMetaParserException;

    K createEntity(K upperInstanceId,String propertyName,String name,Map<String,T> properties) throws AbstractMetaParserException;

    /**
     * update source
     * @param id
     * @param name
     * @param properties
     * @throws AbstractMetaParserException
     */
    void updateSource(K id,String name, Map<String,T> properties) throws AbstractMetaParserException;

    /**
     * update classInfo
     * @param id
     * @param classInfo
     * @param properties
     * @throws AbstractMetaParserException
     */
    void updateType(K id,Class classInfo,Map<String,T> properties) throws AbstractMetaParserException;

    /**
     * update entity
     * @param id
     * @param name
     * @param properties
     * @throws AbstractMetaParserException
     */
    void updateEntity(K id,String name,Map<String,T> properties) throws AbstractMetaParserException;

    /**
     * delete an node
     * @param id
     * @throws AbstractMetaParserException
     */
    void deleteSource(K id) throws AbstractMetaParserException;

    /**
     *
     * @param id
     * @throws AbstractMetaParserException
     */
    void deleteType(K id) throws AbstractMetaParserException;

    /**
     * delete type by ids
     * @param ids
     * @throws AbstractMetaParserException
     */
    void deleteType(Collection<K> ids) throws AbstractMetaParserException;

    /**
     * delete entity by id and all relation info and children tree
     * @param id
     * @throws AbstractMetaParserException
     */
    void deleteEntity(K id) throws AbstractMetaParserException;

    /**
     * delete entity by ids whether they exists or not
     * @param ids
     * @throws AbstractMetaParserException
     */
    void deleteEntity(Collection<K> ids) throws AbstractMetaParserException;

    /**
     * TODO core
     * copy nodes from one sourceName to another sourceName
     * @param toSourceName
     * @param fromSourceName
     * @param ids
     * @throws AbstractMetaParserException
     */
    void copy(String toSourceName, String fromSourceName,Collection<K> ids) throws AbstractMetaParserException;

    /**
     * TODO core
     * move classes from one sourceName to another sourceName
     * @param toSourceName
     * @param fromSourceName
     * @param ids
     * @throws AbstractMetaParserException
     */
    void move(String toSourceName, String fromSourceName,Collection<K> ids) throws AbstractMetaParserException;

    /**
     * link two instances in an sourceName
     * @param headId
     * @param tailId
     * @param property
     * @throws AbstractMetaParserException
     */
    void link(K headId,K tailId,String property) throws AbstractMetaParserException;

    /**
     * crack the relation between two nodes
     * @param headId
     * @param tailId
     * @param property
     * @throws AbstractMetaParserException
     */
    void crack(K headId,K tailId,String property) throws AbstractMetaParserException;
    //TODO batch need a pojo
}
