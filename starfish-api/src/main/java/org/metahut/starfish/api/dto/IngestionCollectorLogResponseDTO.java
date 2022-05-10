package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "ingestion collector task log response dto")
public class IngestionCollectorLogResponseDTO extends PageResponseDTO {

    @ApiModelProperty(value = "ingestion log id")
    private Long ingestionLogId;
    @ApiModelProperty(value = "datasource name")
    private String datasourceName;
    private String description;

    @ApiModelProperty(value = "datasource type")
    private String datasourceType;

    @ApiModelProperty(value = "collector name")
    private String collectorName;

    @ApiModelProperty(value = "cron expression")
    private String cron;
    @ApiModelProperty(value = "task execute status")
    private Boolean state;

    @ApiModelProperty(value = "start time")
    private Date taskStartTime;

    @ApiModelProperty(value = "end time")
    private Date taskEndTime;

    public Long getIngestionLogId() {
        return ingestionLogId;
    }

    public void setIngestionLogId(Long ingestionLogId) {
        this.ingestionLogId = ingestionLogId;
    }

    public String getDatasourceName() {
        return datasourceName;
    }

    public void setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatasourceType() {
        return datasourceType;
    }

    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType;
    }

    public String getCollectorName() {
        return collectorName;
    }

    public void setCollectorName(String collectorName) {
        this.collectorName = collectorName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Date getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }
}
