package org.metahut.starfish.parser.function;

import java.util.*;

/**
 *
 */
public abstract class AbstractNodeService<K extends Comparable,E extends Comparable,T> implements AbstractQueryService<T> {

    private final AbstractInstanceService<K,E,T> classService;

    private final AbstractPropertyService<K,E,T> propertyService;

    /**
     * TODO check not null
     * @param classService
     * @param propertyService
     */
    public AbstractNodeService(AbstractInstanceService<K,E,T> classService, AbstractPropertyService<K,E,T> propertyService) {
        this.classService = classService;
        this.propertyService = propertyService;
    }

    abstract void union(Set<K> keys,Map<K, Map<String,Object>> props);

    @Override
    public Collection<T> query(AbstractQueryCondition condition) {
        return merge(classService.query(condition),propertyService.query(condition));
    }
}
