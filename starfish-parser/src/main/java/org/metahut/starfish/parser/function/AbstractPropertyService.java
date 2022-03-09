package org.metahut.starfish.parser.function;

import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract class AbstractPropertyService<K extends Comparable> {

    /**
     * get all Properies by key
     * @return
     */
    abstract Map<K, List<?>> propertyMap();


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
