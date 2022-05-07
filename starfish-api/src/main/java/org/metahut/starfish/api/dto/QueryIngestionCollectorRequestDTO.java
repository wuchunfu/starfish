package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "ingestion collector request dto")
public class QueryIngestionCollectorRequestDTO {

    @ApiModelProperty(value = "collector name")
    private String name;
    @ApiModelProperty(value = "collector name")
    private String type;

    @ApiModelProperty(value = "datasource name")
    private String datasourceName;

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

    public String getDatasourceName() {
        return datasourceName;
    }

    public void setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
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
