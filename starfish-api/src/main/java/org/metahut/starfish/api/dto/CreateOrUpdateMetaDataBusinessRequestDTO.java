package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "MetaData manager Business create or update request dto")
public class CreateOrUpdateMetaDataBusinessRequestDTO {

    @ApiModelProperty(value = "MetaData manager Business name")
    private String name;

    @ApiModelProperty(value = "MetaData manager Business describe")
    private String describe;

}
