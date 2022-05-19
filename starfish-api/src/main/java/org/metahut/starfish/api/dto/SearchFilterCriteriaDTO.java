package org.metahut.starfish.api.dto;

import org.metahut.starfish.api.enums.FilterConditionEnum;
import org.metahut.starfish.api.enums.FilterOperationSymbolEnum;

import java.util.List;

public class SearchFilterCriteriaDTO {

    private String   attributeName;
    private FilterOperationSymbolEnum operationSymbolEnum;
    private String   attributeValue;

    private FilterConditionEnum conditionEnum;
    private List<SearchFilterCriteriaDTO> criterion;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public FilterOperationSymbolEnum getOperationSymbolEnum() {
        return operationSymbolEnum;
    }

    public void setOperationSymbolEnum(FilterOperationSymbolEnum operationSymbolEnum) {
        this.operationSymbolEnum = operationSymbolEnum;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public FilterConditionEnum getConditionEnum() {
        return conditionEnum;
    }

    public void setConditionEnum(FilterConditionEnum conditionEnum) {
        this.conditionEnum = conditionEnum;
    }

    public List<SearchFilterCriteriaDTO> getCriterion() {
        return criterion;
    }

    public void setCriterion(List<SearchFilterCriteriaDTO> criterion) {
        this.criterion = criterion;
    }
}
