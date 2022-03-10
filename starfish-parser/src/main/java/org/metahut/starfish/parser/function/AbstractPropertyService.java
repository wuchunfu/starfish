package org.metahut.starfish.parser.function;

import java.util.Map;

/**
 *
 */
public abstract class AbstractPropertyService<K extends Comparable,E extends Comparable,T> extends AbstractQueryService<T> {

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
     * @param <M>
     */
    abstract <M> void add(E env,K id,String property,M obj);

    //add

    /**
     *
     * @param id
     * @param property
     * @param obj
     * @param <M>
     */
    abstract <M> void update(E env,K id,String property,M obj);

    //delete

    /**
     * set to null
     * @param id
     * @param property
     */
    abstract void delete(E env,K id,String property);

    /**
     * delete all property
     * @param id
     */
    abstract void delete(E env,K id);

}
