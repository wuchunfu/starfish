package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class CollectorTaskInstanceResponseDTO {

    @ApiModelProperty(value = "The identifier of the task instance")
    private Long id;

    @ApiModelProperty(value = "The flow code of the task instance")
    private Long flowCode;

    @ApiModelProperty(value = "The type of the collector adapter")
    private String collectorAdapterType;

    @ApiModelProperty(value = "The name of the collector adapter")
    private String collectorAdapterName;

    @ApiModelProperty(value = "The name of the task instance")
    private String collectorTaskName;

    @ApiModelProperty(value = "The description of the task instance")
    private String collectorTaskDesc;

    @ApiModelProperty(value = "The time when the task start running")
    private Date beginTime;

    @ApiModelProperty(value = "The time when the task finish running")
    private Date endTime;

    @ApiModelProperty(value = "The execution status of the task instance")
    private String executionStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(Long flowCode) {
        this.flowCode = flowCode;
    }

    public String getCollectorAdapterType() {
        return collectorAdapterType;
    }

    public void setCollectorAdapterType(String collectorAdapterType) {
        this.collectorAdapterType = collectorAdapterType;
    }

    public String getCollectorAdapterName() {
        return collectorAdapterName;
    }

    public void setCollectorAdapterName(String collectorAdapterName) {
        this.collectorAdapterName = collectorAdapterName;
    }

    public String getCollectorTaskName() {
        return collectorTaskName;
    }

    public void setCollectorTaskName(String collectorTaskName) {
        this.collectorTaskName = collectorTaskName;
    }

    public String getCollectorTaskDesc() {
        return collectorTaskDesc;
    }

    public void setCollectorTaskDesc(String collectorTaskDesc) {
        this.collectorTaskDesc = collectorTaskDesc;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(String executionStatus) {
        this.executionStatus = executionStatus;
    }
}
