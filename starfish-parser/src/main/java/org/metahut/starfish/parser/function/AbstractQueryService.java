package org.metahut.starfish.parser.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 
 */
public interface AbstractQueryService<T> {

    /**
     * query by condition
     * @param condition query condition
     * @return
     */
    Collection<T> query(AbstractQueryCondition condition);

    /**
     * TODO ArrayList replace
     * TODO async
     * @param collections
     * @return
     */
    default Collection<T> merge(Collection<T>... collections) {
        return Arrays.stream(collections).flatMap(collection -> collection.stream()).collect(Collectors.toCollection(ArrayList::new));
    }
}
