package org.metahut.starfish.api.dto;

import org.metahut.starfish.api.enums.ReleaseStateEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "ingestion collector response dto")
public class IngestionCollectorResponseDTO {

    @ApiModelProperty(value = "collector name", required = true)
    private Long ingestionCollectorId;
    @ApiModelProperty(value = "collector name", required = true)
    private String name;
    private String description;

    @ApiModelProperty(value = "datasource id", required = true)
    private String datasourceId;

    @ApiModelProperty(value = "collector plugin parameter", required = true)
    private String parameter;

    @ApiModelProperty(value = "cron expression", required = true)
    private String cron;
    private ReleaseStateEnum state;

    @ApiModelProperty(value = "create task's congiguration time", required = true)
    private Date taskCreateTime;

    @ApiModelProperty(value = "update task's congiguration time", required = true)
    private Date taskUpdateTime;

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

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
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

    public Date getTaskCreateTime() {
        return taskCreateTime;
    }

    public void setTaskCreateTime(Date taskCreateTime) {
        this.taskCreateTime = taskCreateTime;
    }

    public Date getTaskUpdateTime() {
        return taskUpdateTime;
    }

    public void setTaskUpdateTime(Date taskUpdateTime) {
        this.taskUpdateTime = taskUpdateTime;
    }

    public Long getIngestionCollectorId() {
        return ingestionCollectorId;
    }

    public void setIngestionCollectorId(Long ingestionCollectorId) {
        this.ingestionCollectorId = ingestionCollectorId;
    }
}
