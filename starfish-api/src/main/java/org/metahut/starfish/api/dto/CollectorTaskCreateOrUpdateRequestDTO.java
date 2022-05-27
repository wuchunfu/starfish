package org.metahut.starfish.api.dto;

import org.metahut.starfish.api.enums.ReleaseStateEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(description = "collector task request dto")
public class CollectorTaskCreateOrUpdateRequestDTO {

    @ApiModelProperty(value = "collector task name", required = true)
    @NotEmpty(message = "{parameter.not.null}")
    private String name;

    @ApiModelProperty(value = "collector description", required = true)
    private String description;

    @ApiModelProperty(value = "collector adapter id", required = true)
    @NotNull(message = "{parameter.not.null}")
    private Long adapterId;

    @ApiModelProperty(value = "collector task plugin parameter", required = true)
    private String parameter;

    @ApiModelProperty(value = "cron expression", required = true)
    @NotEmpty(message = "{parameter.not.null}")
    private String cron;

    @NotEmpty(message = "{parameter.not.null}", groups = Update.class)
    private String schedulerFlowCode;

    private ReleaseStateEnum state;

    public interface Update {

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

    public String getSchedulerFlowCode() {
        return schedulerFlowCode;
    }

    public void setSchedulerFlowCode(String schedulerFlowCode) {
        this.schedulerFlowCode = schedulerFlowCode;
    }
}
