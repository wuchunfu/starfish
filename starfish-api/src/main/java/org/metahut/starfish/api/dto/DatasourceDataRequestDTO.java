package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "datasource request dto")
public class DatasourceDataRequestDTO extends PageRequestDTO {

    //datasouce catagory name
    @ApiModelProperty(value = "datasource name")
    private String name;
    //datasource paramter instance
    @ApiModelProperty(value = "datasource parameter to connect")
    private String paramter;
    @ApiModelProperty(value = "datasource description")
    private String description;
    @ApiModelProperty(value = "datasource type")
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
}
