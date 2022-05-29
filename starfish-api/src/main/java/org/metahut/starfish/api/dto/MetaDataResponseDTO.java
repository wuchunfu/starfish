package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "metaData response dto")
public class MetaDataResponseDTO extends PageResponseDTO {

    @ApiModelProperty(value = "datasource type")
    private String type;

    @ApiModelProperty(value = "datasource instance parameter")
    private String parameter;

    @ApiModelProperty(value = "metaData version")
    private String version;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
