package org.metahut.starfish.parser.function;

import java.util.Map;

/**
 *
 */
public abstract class AbstractPropertyService<K extends Comparable,E extends Comparable,T> implements AbstractQueryService<T> {

    /**
     * get all instance property by env
     * @return
     *  {
     *      key : instaceId
     *      value : map (propMap) {k1:v1,K2:v2...}
     *  }
     */
    abstract Map<K, Map<String,Object>> propertyMap(E env);

    // update

    /**
     *
     * @param id
     * @param property
     * @param obj
     * @param <T>
     */
    abstract <T> void add(K id,String property,T obj);

    //add

    /**
     *
     * @param id
     * @param property
     * @param obj
     * @param <T>
     */
    abstract <T> void update(K id,String property,T obj);

    //delete

    /**
     * set to null
     * @param id
     * @param property
     */
    abstract void delete(K id,String property);

}
