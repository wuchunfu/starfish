package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "ingestion collector task execute log response dto")
public class IngestionCollectorExecuteLogResponseDTO {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "collector name")
    private String name;
    private String description;
    @ApiModelProperty(value = "task execute status")
    private Boolean state;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
