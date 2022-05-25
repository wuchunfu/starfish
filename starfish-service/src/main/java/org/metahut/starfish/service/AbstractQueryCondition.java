package org.metahut.starfish.service;

import org.metahut.starfish.service.expression.ConditionPiece;

import java.util.List;

/**
 *
 */
public class AbstractQueryCondition<U> {

    private Class<U> resultType;

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
}
