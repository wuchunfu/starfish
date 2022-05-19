package org.metahut.starfish.service;

import java.util.List;

/**
 *
 */
public class AbstractQueryCondition<U> {

    private Class<U> resultType;

    private SearchFilterCriteria filters;

    private StartType startType;

    public Class<U> getResultType() {
        return resultType;
    }

    public void setResultType(Class<U> resultType) {
        this.resultType = resultType;
    }

    public SearchFilterCriteria getFilters() {
        return filters;
    }

    public void setFilters(SearchFilterCriteria filters) {
        this.filters = filters;
    }

    public static class SearchFilterCriteria {
        private String attributeName;
        private Object attributeValue;
        private FilterOperationSymbolEnum operationSymbolEnum;
        private FilterConditionEnum filterConditionEnum;
        private List<SearchFilterCriteria> criterion;

        public String getAttributeName() {
            return attributeName;
        }

        public void setAttributeName(String attributeName) {
            this.attributeName = attributeName;
        }

        public Object getAttributeValue() {
            return attributeValue;
        }

        public void setAttributeValue(String attributeValue) {
            this.attributeValue = attributeValue;
        }

        public FilterOperationSymbolEnum getOperationSymbolEnum() {
            return operationSymbolEnum;
        }

        public void setOperationSymbolEnum(FilterOperationSymbolEnum operationSymbolEnum) {
            this.operationSymbolEnum = operationSymbolEnum;
        }

        public FilterConditionEnum getFilterConditionEnum() {
            return filterConditionEnum;
        }

        public void setFilterConditionEnum(FilterConditionEnum filterConditionEnum) {
            this.filterConditionEnum = filterConditionEnum;
        }

        public List<SearchFilterCriteria> getCriterion() {
            return criterion;
        }

        public void setCriterion(List<SearchFilterCriteria> criterion) {
            this.criterion = criterion;
        }
    }

    public enum StartType {
        ENTITY,
        PROPERTY,
        RELATION;
    }

    public enum FilterOperationSymbolEnum {
        LT,
        GT,
        LTE,
        GTE,
        EQ,
        IN,
        LIKE
        ;
    }

    public enum FilterConditionEnum {

        AND,
        OR;
    }

}
