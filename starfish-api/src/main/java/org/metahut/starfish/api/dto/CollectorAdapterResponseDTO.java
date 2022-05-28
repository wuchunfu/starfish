package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@ApiModel(description = "collector adapter response dto")
public class CollectorAdapterResponseDTO {

    @ApiModelProperty(value = "collector adapter id")
    private Long id;

    @ApiModelProperty(value = "collector adapter name")
    private String name;

    @ApiModelProperty(value = "collector type")
    private String type;

    @ApiModelProperty(value = "collector adapter parameter to connect")
    private Map<String,Object> parameter;

    @ApiModelProperty(value = "collector adapter description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String,Object> getParameter() {
        return parameter;
    }

    public void setParameter(Map<String,Object> parameter) {
        this.parameter = parameter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
