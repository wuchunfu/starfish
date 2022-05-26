package org.metahut.starfish.unit;

import org.metahut.starfish.unit.expression.ConditionPiece;
import org.metahut.starfish.unit.expression.EachPointer;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class AbstractQueryCondition<U> {

    private Class<U> resultType;

    /**
     * key :propertyName
     */
    private Map<String, EachPointer> eachPointers;

    private List<ConditionPiece> filters;

    public Class<U> getResultType() {
        return resultType;
    }

    public void setResultType(Class<U> resultType) {
        this.resultType = resultType;
    }

    public List<ConditionPiece> getFilters() {
        return filters;
    }

    public void setFilters(List<ConditionPiece> filters) {
        this.filters = filters;
    }

    public Map<String, EachPointer> getEachPointers() {
        return eachPointers;
    }

    public void setEachPointers(Map<String, EachPointer> eachPointers) {
        this.eachPointers = eachPointers;
    }
}
