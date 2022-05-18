package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "create type request dto")
public class CreateTypeResponseDTO extends PageRequestDTO {

    @ApiModelProperty(value = "source id", required = true)
    private Long sourceId;

    @ApiModelProperty(value = "type id", required = true)
    private Long typeId;

    @ApiModelProperty(value = "class name", required = true)
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
