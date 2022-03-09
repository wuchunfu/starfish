package org.metahut.starfish.parser.function;

import java.util.Map;
import java.util.Set;

/**
 * 应该是抽象的
 */
public abstract class AbstractNodeService<K extends Comparable,E extends Comparable> {

    private final AbstractInstanceService<K,E> classService;

    private final AbstractPropertyService<K,E> propertyService;

    public AbstractNodeService(AbstractInstanceService<K,E> classService, AbstractPropertyService<K,E> propertyService) {
        this.classService = classService;
        this.propertyService = propertyService;
    }

    abstract void union(Set<K> keys,Map<K, Map<String,Object>> props);



}
