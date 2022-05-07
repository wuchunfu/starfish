package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "ingestion collector request dto")
public class IngestionCollectorLogRequestDTO extends PageRequestDTO{

    @ApiModelProperty(value = "collector name")
    private String name;
    @ApiModelProperty(value = "collector type")
    private String type;

    @ApiModelProperty(value = "task name")
    private String collectorTaskName;

    @ApiModelProperty(value = "task execute status")
    private Boolean state;

    @ApiModelProperty(value = "task begin time")
    private Date taskBeginTime;

    @ApiModelProperty(value = "task end time")
    private Date taskEndTime;

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

    public String getCollectorTaskName() {
        return collectorTaskName;
    }

    public void setCollectorTaskName(String collectorTaskName) {
        this.collectorTaskName = collectorTaskName;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Date getTaskBeginTime() {
        return taskBeginTime;
    }

    public void setTaskBeginTime(Date taskBeginTime) {
        this.taskBeginTime = taskBeginTime;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }
}
