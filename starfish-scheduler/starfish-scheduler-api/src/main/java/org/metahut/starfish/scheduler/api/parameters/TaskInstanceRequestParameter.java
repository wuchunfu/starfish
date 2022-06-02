package org.metahut.starfish.scheduler.api.parameters;

import org.metahut.starfish.scheduler.api.ExecutionStatus;

import lombok.Data;

import java.util.Date;

@Data
public class TaskInstanceRequestParameter extends PageRequest {

    private String name;

    private String flowInstanceName;

    private ExecutionStatus executionStatus;

    /**
     * the time when the task starts executing.
     */
    private String beginTime;

    /**
     * the time when the task is done.
     */
    private String endTime;

}
