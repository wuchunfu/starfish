package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "datasource create or update request dto")
public class CreateOrUpdateDatasourceDataRequestDTO {

    @ApiModelProperty(value = "datasource type id", required = true)
    private Long typeId;
    //datasouce catagory name
    @ApiModelProperty(value = "datasource name", required = true)
    private String name;
    //datasource paramter instance
    @ApiModelProperty(value = "datasource parameter to connect", required = true)
    private String paramter;
    @ApiModelProperty(value = "datasource description", required = true)
    private String description;
    @ApiModelProperty(value = "datasource type", required = true)
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParamter() {
        return paramter;
    }

    public void setParamter(String paramter) {
        this.paramter = paramter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
