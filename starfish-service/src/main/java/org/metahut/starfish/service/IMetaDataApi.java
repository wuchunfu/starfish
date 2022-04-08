package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.domain.instance.MetaResult;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import java.util.Map;

/**
 * TODO query
 */
interface IMetaDataApi<V,K,T> {
    //all

    /**
     * delete an typeName
     * @param typeName
     * @throws AbstractMetaParserException
     */
    void delete(V typeName) throws AbstractMetaParserException;

    /**
     * TODO core
     * copy classes from one typeName to another typeName
     * @param toTypeName
     * @param fromTypeName
     * @param classIds
     * @throws AbstractMetaParserException
     */
    void copy(V toTypeName, V fromTypeName,long... classIds) throws AbstractMetaParserException;

    /**
     * TODO core
     * copy instances from one typeName to another typeName
     * @param toTypeName
     * @param fromTypeName
     * @param instanceIds
     * @throws AbstractMetaParserException
     */
    void copy(V toTypeName, V fromTypeName,K... instanceIds) throws AbstractMetaParserException;

    /**
     * TODO core
     * move classes from one typeName to another typeName
     * @param toTypeName
     * @param fromTypeName
     * @param classIds
     * @throws AbstractMetaParserException
     */
    void move(V toTypeName, V fromTypeName,long... classIds) throws AbstractMetaParserException;

    /**
     * TODO core
     * move toTypeName from one typeName to another typeName
     * @param toTypeName
     * @param fromTypeName
     * @param instanceIds
     * @throws AbstractMetaParserException
     */
    void move(V toTypeName, V fromTypeName,K... instanceIds) throws AbstractMetaParserException;

    /**
     * add classes to an typeName
     * @param typeName
     * @param classes
     * @throws AbstractMetaParserException
     */
    void add(V typeName, Class... classes) throws AbstractMetaParserException;

    /**
     * modify classes of an typeName
     * @param typeName
     * @param sfClass
     * @throws AbstractMetaParserException
     */
    @Deprecated
    void modify(V typeName, Class... sfClass) throws AbstractMetaParserException;

    /**
     * delete classes from an typeName
     * @param typeName
     * @param classIds
     * @throws AbstractMetaParserException
     */
    void delete(V typeName,long... classIds) throws AbstractMetaParserException;

    /**
     * get graph、classes、typeName from an typeName
     * @param typeName
     * @return
     * @throws AbstractMetaParserException
     */
    MetaResult<V,K,T> all(V typeName) throws AbstractMetaParserException;

    /**
     * create a type name
     * @return
     * @throws AbstractMetaParserException
     */
    V create() throws AbstractMetaParserException;

    /**
     * create an node with property instance of the class
     * @param typeName
     * @param classId
     * @param property
     * @param obj
     * @return
     * @throws AbstractMetaParserException
     */
    K create(V typeName,long classId, String property,T obj) throws AbstractMetaParserException;

    /**
     * create an node with properties instance of the class
     * @param typeName
     * @param classId
     * @param attributes
     * @return
     * @throws AbstractMetaParserException
     */
    K create(V typeName,long classId, Map<String,T> attributes) throws AbstractMetaParserException;

    /**
     * create an node with properties instance of the class and then link to another instance
     * @param typeName
     * @param classId
     * @param parentInstanceId
     * @param property
     * @param attributes
     * @return
     * @throws AbstractMetaParserException
     */
    K create(V typeName,long classId, K parentInstanceId,String property,Map<String,T> attributes) throws AbstractMetaParserException;

    /**
     * add a property to node
     * @param typeName
     * @param instanceId
     * @param property
     * @param obj
     * @throws AbstractMetaParserException
     */
    void add(V typeName,K instanceId,String property,T obj) throws AbstractMetaParserException;

    /**
     * link two instances in an typeName
     * @param typeName
     * @param headId
     * @param tailId
     * @param property
     * @throws AbstractMetaParserException
     */
    void link(V typeName,K headId,K tailId,String property) throws AbstractMetaParserException;

    /**
     * clever update some props of the instance
     * @param typeName
     * @param instanceId
     * @param attributes
     * @throws AbstractMetaParserException
     */
    void update(V typeName,K instanceId,Map<String,T> attributes) throws AbstractMetaParserException;

    /**
     * force update all props of the instance
     * @param typeName
     * @param instanceId
     * @param attributes
     * @throws AbstractMetaParserException
     */
    void modify(V typeName,K instanceId,Map<String,T> attributes) throws AbstractMetaParserException;

    /**
     * delete a property from an instance
     * @param typeName
     * @param instanceId
     * @param property
     * @throws AbstractMetaParserException
     */
    void delete(V typeName,K instanceId,String property) throws AbstractMetaParserException;

    /**
     * crack the relation between two nodes
     * @param typeName
     * @param headId
     * @param tailId
     * @param property
     * @throws AbstractMetaParserException
     */
    void crack(V typeName,K headId,K tailId,String property) throws AbstractMetaParserException;

    /**
     * batch delete instances and relations
     * @param typeName
     * @param instanceIds
     */
    void delete(V typeName, K... instanceIds) throws AbstractMetaParserException;

}
