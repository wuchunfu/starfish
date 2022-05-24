package org.metahut.starfish.api.dto;

import org.metahut.starfish.api.enums.ReleaseStateEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "collector task request dto")
public class CollectorTaskCreateOrUpdateRequestDTO {

    @ApiModelProperty(value = "collector task name", required = true)
    private String name;

    @ApiModelProperty(value = "collector description", required = true)
    private String description;

    @ApiModelProperty(value = "collector adapter id", required = true)
    private Long adapterId;

    @ApiModelProperty(value = "collector task plugin parameter", required = true)
    private String parameter;

    @ApiModelProperty(value = "cron expression", required = true)
    private String cron;

    private ReleaseStateEnum state;

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

    public Long getAdapterId() {
        return adapterId;
    }

    public void setAdapterId(Long adapterId) {
        this.adapterId = adapterId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public ReleaseStateEnum getState() {
        return state;
    }

    public void setState(ReleaseStateEnum state) {
        this.state = state;
    }
}
