package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModelProperty;

public class CollectorTaskInstanceLogResponseDTO {

    @ApiModelProperty(value = "The identifier of the task instance")
    private Integer taskInstanceId;

    @ApiModelProperty(value = "The log content of the task instance")
    private String content;

    public Integer getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(Integer taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
