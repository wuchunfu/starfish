package org.metahut.starfish.scheduler.dolphinscheduler.entity;

import lombok.Data;

import java.util.List;
/**
 * a complex process definition
 */

@Data
public class ComplexProcessDefinition {

    private ProcessDefinition processDefinition;

    private List<TaskDefinition> taskDefinitionList;

    private List<ProcessTaskRelation> processTaskRelationList;

}
