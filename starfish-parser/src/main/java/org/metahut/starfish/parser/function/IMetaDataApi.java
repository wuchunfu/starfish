package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.domain.instance.Environment;
import org.metahut.starfish.parser.domain.instance.MetaResult;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import java.util.Map;

/**
 * TODO query
 */
interface IMetaDataApi<E,K,T> {
    //all

    /**
     * delete an env
     * @param env
     * @throws AbstractMetaParserException
     */
    void delete(E env) throws AbstractMetaParserException;

    /**
     * copy env
     * @param env
     * @return
     * @throws AbstractMetaParserException
     */
    Environment<E> copy(E env) throws AbstractMetaParserException;

    /**
     * TODO core
     * copy classes from one env to another env
     * @param toEnv
     * @param fromEnv
     * @param classIds
     * @throws AbstractMetaParserException
     */
    void copy(E toEnv,E fromEnv,long... classIds) throws AbstractMetaParserException;

    /**
     * TODO core
     * copy instances from one env to another env
     * @param toEnv
     * @param fromEnv
     * @param instanceIds
     * @throws AbstractMetaParserException
     */
    void copy(E toEnv,E fromEnv,K... instanceIds) throws AbstractMetaParserException;

    /**
     * TODO core
     * move classes from one env to another env
     * @param toEnv
     * @param fromEnv
     * @param classIds
     * @throws AbstractMetaParserException
     */
    void move(E toEnv,E fromEnv,long... classIds) throws AbstractMetaParserException;

    /**
     * TODO core
     * move instances from one env to another env
     * @param toEnv
     * @param fromEnv
     * @param instanceIds
     * @throws AbstractMetaParserException
     */
    void move(E toEnv,E fromEnv,K... instanceIds) throws AbstractMetaParserException;

    /**
     * create an environment
     * @return
     * @throws AbstractMetaParserException
     */
    Environment<E> create() throws AbstractMetaParserException;

    /**
     * modify infos or change state of an env
     * @param env
     * @throws AbstractMetaParserException
     */
    void modify(Environment<E> env) throws AbstractMetaParserException;

    /**
     * merge one env with antoher env
     * @param env1
     * @param env2
     * @return a new merged env
     * @throws AbstractMetaParserException
     */
    Environment<E> merge(E env1,E env2) throws AbstractMetaParserException;

    /**
     * add classes to an env
     * @param env
     * @param classes
     * @throws AbstractMetaParserException
     */
    void add(E env, Class... classes) throws AbstractMetaParserException;

    /**
     * modify classes of an env
     * @param env
     * @param sfClass
     * @throws AbstractMetaParserException
     */
    @Deprecated
    void modify(E env, Class... sfClass) throws AbstractMetaParserException;

    /**
     * delete classes from an env
     * @param env
     * @param classIds
     * @throws AbstractMetaParserException
     */
    void delete(E env,long... classIds) throws AbstractMetaParserException;

    /**
     * get graph、classes、env from an env
     * @param env
     * @return
     * @throws AbstractMetaParserException
     */
    MetaResult<E,K,T> all(E env) throws AbstractMetaParserException;

    /**
     * create an node with property instance of the class
     * @param env
     * @param classId
     * @param property
     * @param obj
     * @return
     * @throws AbstractMetaParserException
     */
    K create(E env,long classId, String property,T obj) throws AbstractMetaParserException;

    /**
     * create an node with properties instance of the class
     * @param env
     * @param classId
     * @param attributes
     * @return
     * @throws AbstractMetaParserException
     */
    K create(E env,long classId, Map<String,T> attributes) throws AbstractMetaParserException;

    /**
     * create an node with properties instance of the class and then link to another instance
     * @param env
     * @param classId
     * @param parentInstanceId
     * @param property
     * @param attributes
     * @return
     * @throws AbstractMetaParserException
     */
    K create(E env,long classId, K parentInstanceId,String property,Map<String,T> attributes) throws AbstractMetaParserException;

    /**
     * add a property to node
     * @param env
     * @param instanceId
     * @param property
     * @param obj
     * @throws AbstractMetaParserException
     */
    void add(E env,K instanceId,String property,T obj) throws AbstractMetaParserException;

    /**
     * link two instances in an env
     * @param env
     * @param headId
     * @param tailId
     * @param property
     * @throws AbstractMetaParserException
     */
    void link(E env,K headId,K tailId,String property) throws AbstractMetaParserException;

    /**
     * clever update some props of the instance
     * @param env
     * @param instanceId
     * @param attributes
     * @throws AbstractMetaParserException
     */
    void update(E env,K instanceId,Map<String,T> attributes) throws AbstractMetaParserException;

    /**
     * force update all props of the instance
     * @param env
     * @param instanceId
     * @param attributes
     * @throws AbstractMetaParserException
     */
    void modify(E env,K instanceId,Map<String,T> attributes) throws AbstractMetaParserException;

    /**
     * delete a property from an instance
     * @param env
     * @param instanceId
     * @param property
     * @throws AbstractMetaParserException
     */
    void delete(E env,K instanceId,String property) throws AbstractMetaParserException;

    /**
     * crack the relation between two nodes
     * @param env
     * @param headId
     * @param tailId
     * @param property
     * @throws AbstractMetaParserException
     */
    void crack(E env,K headId,K tailId,String property) throws AbstractMetaParserException;

    /**
     * batch delete instances and relations
     * @param env
     * @param instanceIds
     */
    void delete(E env, K... instanceIds) throws AbstractMetaParserException;

}
