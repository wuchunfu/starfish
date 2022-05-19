package org.metahut.starfish.api.dto;

public abstract class SearchRequestDTO extends PageRequestDTO {

    private SearchFilterCriteriaDTO filters;

    protected abstract void toFilters();

    public SearchFilterCriteriaDTO getFilters() {
        return filters;
    }

    public void setFilters(SearchFilterCriteriaDTO filters) {
        this.filters = filters;
    }
}
