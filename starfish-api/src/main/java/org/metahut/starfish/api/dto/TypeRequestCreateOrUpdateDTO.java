package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "create type request dto")
public class TypeRequestCreateOrUpdateDTO {
    @ApiModelProperty(value = "source name", required = true)
    private String sourceName;

    @ApiModelProperty(value = "class name", required = true)
    private String className;

    @ApiModelProperty(value = "class field", required = true)
    private List<String> classField;

    @ApiModelProperty(value = "class name attribute rel", required = true)
    private String classRelName;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getClassField() {
        return classField;
    }

    public void setClassField(List<String> classField) {
        this.classField = classField;
    }

    public String getClassRelName() {
        return classRelName;
    }

    public void setClassRelName(String classRelName) {
        this.classRelName = classRelName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
