package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class IngestionCollectorInstanceSearchRequestDTO extends SearchRequestDTO {

    @ApiModelProperty(value = "collector name")
    private String name;
    @ApiModelProperty(value = "collector name")
    private String type;

    @ApiModelProperty(value = "update begin time")
    private Date updateBeginTime;

    @ApiModelProperty(value = "update end time")
    private Date updateEndTime;

    @Override
    protected void toFilters() {
        SearchFilterCriteriaDTO searchFilterCriteriaDTO = new SearchFilterCriteriaDTO();
        searchFilterCriteriaDTO.setAttributeName("name");



    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdateBeginTime() {
        return updateBeginTime;
    }

    public void setUpdateBeginTime(Date updateBeginTime) {
        this.updateBeginTime = updateBeginTime;
    }

    public Date getUpdateEndTime() {
        return updateEndTime;
    }

    public void setUpdateEndTime(Date updateEndTime) {
        this.updateEndTime = updateEndTime;
    }
}
