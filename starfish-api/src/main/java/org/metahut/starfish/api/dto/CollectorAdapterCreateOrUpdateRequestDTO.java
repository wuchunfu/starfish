package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "collector adapter create or update request dto")
public class CollectorAdapterCreateOrUpdateRequestDTO {

    @ApiModelProperty(value = "collector adapter name", required = true)
    private String name;

    @ApiModelProperty(value = "collector adapter parameter to connect", required = true)
    private String parameter;

    @ApiModelProperty(value = "collector adapter description")
    private String description;

    @ApiModelProperty(value = "collector type", required = true)
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
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
