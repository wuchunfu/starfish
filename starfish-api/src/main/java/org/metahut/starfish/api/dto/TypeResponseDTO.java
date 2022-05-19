package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "type response dto")
public class TypeResponseDTO {

    @ApiModelProperty(value = "source id")
    private Long sourceId;

    @ApiModelProperty(value = "type id")
    private Long typeId;

    @ApiModelProperty(value = "class name")
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
