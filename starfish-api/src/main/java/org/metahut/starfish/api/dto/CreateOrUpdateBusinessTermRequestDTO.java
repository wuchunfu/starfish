package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "MetaData manager Business term create or update request dto")
public class CreateOrUpdateBusinessTermRequestDTO {

    @ApiModelProperty(value = "MetaData manager Business term name")
    private String name;

    @ApiModelProperty(value = "MetaData manager Business term describe")
    private String describe;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
