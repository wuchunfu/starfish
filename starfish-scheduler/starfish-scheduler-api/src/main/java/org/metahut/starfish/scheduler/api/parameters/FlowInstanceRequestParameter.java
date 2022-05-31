package org.metahut.starfish.scheduler.api.parameters;

import org.metahut.starfish.scheduler.api.ExecutionStatus;

import lombok.Data;

import java.util.Date;

@Data
public class FlowInstanceRequestParameter extends PageRequest {

    private String name;

    private ExecutionStatus executionStatus;

    /**
     * the time when the flow start executing.
     */
    private Date beginTime;

    /**
     * the time when the flow is done.
     */
    private Date endTime;

}
