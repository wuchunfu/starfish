package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.SfClass;
import org.metahut.starfish.parser.domain.instance.SfEnvironment;
import org.metahut.starfish.parser.domain.instance.SfMetaResult;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import java.util.Collection;
import java.util.Map;

/**
 * TODO query
 * <T> 是个问题
 */
public interface AbstractApiService<E,K,T> {
    //all
    void delete(E env) throws AbstractMetaParserException;
    SfMetaResult<E,K,T> copy(E env) throws AbstractMetaParserException;
    void copy(E toEnv,E fromEnv,long... classsId) throws AbstractMetaParserException;
    //TODO
    void copy(E toEnv,E fromEnv,K... instanceIds) throws AbstractMetaParserException;
    void copy(E env,K fromInstanceId,K toInstanceId,String property) throws AbstractMetaParserException;
    void move(E env,K fromInstanceId,K toInstanceId,String property) throws AbstractMetaParserException;
    void move(E env,K oldHeadId,K newHeadId,K tailId,String property) throws AbstractMetaParserException;
    //TODO
    void move(E toEnv,E fromEnv,K... instanceIds) throws AbstractMetaParserException;
    //env
    SfEnvironment<E> create() throws AbstractMetaParserException;
    void modify(SfEnvironment<E> env) throws AbstractMetaParserException;
    void merge(SfEnvironment<E>... envs) throws AbstractMetaParserException;
    //classes
    void add(E env, SfClass... classes) throws AbstractMetaParserException;
    void modify(E env,SfClass... sfClass) throws AbstractMetaParserException;
    void delete(E env,long... classId) throws AbstractMetaParserException;
    //graph
    SfMetaResult<E,K,T> all(E env) throws AbstractMetaParserException;
    K create(E env,long classId, String property,T obj) throws AbstractMetaParserException;
    K create(E env,long classId, Map<String,T> attributes) throws AbstractMetaParserException;
    K create(E env,long classId, K parentInstanceId,String property,Map<String,T> attributes) throws AbstractMetaParserException;
    void add(E env,K instanceId,String property,T obj) throws AbstractMetaParserException;
    void link(E env,K headId,K tailId,String property) throws AbstractMetaParserException;
    void update(E env,K instanceId,Map<String,T> attributes) throws AbstractMetaParserException;
    void modify(E env,K instanceId,Map<String,T> attributes) throws AbstractMetaParserException;
    void delete(E env,K instanceId,String property) throws AbstractMetaParserException;
    void crack(E env,K headId,K tailId,String property) throws AbstractMetaParserException;
    void delete(E env, Collection<K> instanceIds);

}
