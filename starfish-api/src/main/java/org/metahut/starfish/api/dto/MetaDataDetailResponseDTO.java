package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "metaData detail response dto")
public class MetaDataDetailResponseDTO extends PageResponseDTO {

    @ApiModelProperty(value = "datasource metaData first level data ,eg:table,topic", required = true)
    private String firstLevelParameter;

    @ApiModelProperty(value = "datasource metaData first level data ,eg:column,schema", required = true)
    private String secondLevelParameter;

    public String getFirstLevelParameter() {
        return firstLevelParameter;
    }

    public void setFirstLevelParameter(String firstLevelParameter) {
        this.firstLevelParameter = firstLevelParameter;
    }

    public String getSecondLevelParameter() {
        return secondLevelParameter;
    }

    public void setSecondLevelParameter(String secondLevelParameter) {
        this.secondLevelParameter = secondLevelParameter;
    }
}
