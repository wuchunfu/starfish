package org.metahut.starfish.scheduler.dolphinscheduler.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * the relation between those tasks in a process
 */

@Data
public class ProcessTaskRelation {

    /**
     * id
     */
    private int id;

    /**
     * name
     */
    private String name;

    /**
     * process version
     */
    private int processDefinitionVersion;

    /**
     * project code
     */
    private long projectCode;

    /**
     * process code
     */
    private long processDefinitionCode;

    /**
     * pre task code
     */
    private long preTaskCode;

    /**
     * pre node version
     */
    private int preTaskVersion;

    /**
     * post task code
     */
    private long postTaskCode;

    /**
     * post node version
     */
    private int postTaskVersion;

    /**
     * condition type
     */
    // private ConditionType conditionType;

    /**
     * condition parameters
     */
    private String conditionParams;

    /**
     * create time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * update time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
}
