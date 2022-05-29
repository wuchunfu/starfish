package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "metaData request dto")
public class MetaDataRequestDTO extends PageRequestDTO {

    @ApiModelProperty(value = "datasource type", required = true)
    private String type;

    @ApiModelProperty(value = "datasource instance parameter", required = true)
    private String parameter;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
