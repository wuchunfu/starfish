package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "collector task instance request dto")
public class CollectorTaskInstanceRequestDTO extends PageRequestDTO {

    @ApiModelProperty(value = "The identifier of the collector adapter")
    private Long adapterId;

    @ApiModelProperty(value = "collector type")
    private String type;

    @ApiModelProperty(value = "The name of a collector task")
    private String name;

    private String executionStatus;

    @ApiModelProperty(value = "execute begin time")
    private Date beginTime;

    @ApiModelProperty(value = "execute end time")
    private Date endTime;

    public Long getAdapterId() {
        return adapterId;
    }

    public void setAdapterId(Long adapterId) {
        this.adapterId = adapterId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(String executionStatus) {
        this.executionStatus = executionStatus;
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
}
