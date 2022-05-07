package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "datasource response type dto")
public class DatasourceTypeResponseDTO {

    //datasource paramter instance
    @ApiModelProperty(value = "datasource parameter to connect")
    private String paramter;
    @ApiModelProperty(value = "datasource type")
    private String type;

    public String getParamter() {
        return paramter;
    }

    public void setParamter(String paramter) {
        this.paramter = paramter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
