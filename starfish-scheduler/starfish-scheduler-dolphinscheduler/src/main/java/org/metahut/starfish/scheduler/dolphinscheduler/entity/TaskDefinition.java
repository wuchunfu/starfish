package org.metahut.starfish.scheduler.dolphinscheduler.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * the definition of a task
 */
@Data
public class TaskDefinition {
    /**
     * id
     */
    private int id;

    /**
     * code
     */
    private long code;

    /**
     * name
     */
    private String name;

    /**
     * version
     */
    private int version;

    /**
     * description
     */
    private String description;

    /**
     * project code
     */
    private long projectCode;

    /**
     * task user id
     */
    private int userId;

    /**
     * task type
     */
    private String taskType;

    /**
     * user defined parameters
     */
    private TaskParameter taskParams;

    /**
     * task is valid: yes/no
     */
    private String flag = "YES";

    /**
     * user name
     */
    private String userName;

    /**
     * project name
     */
    private String projectName;

    /**
     * worker group
     */
    private String workerGroup;

    /**
     * environment code
     */
    private long environmentCode;

    /**
     * fail retry times
     */
    private int failRetryTimes;

    /**
     * fail retry interval
     */
    private int failRetryInterval;


    /**
     * task warning time out. unit: minute
     */
    private int timeout;

    /**
     * delay execution time.
     */
    private int delayTime;

    /**
     * resource ids
     */
    private String resourceIds;

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

    /**
     * modify user name
     */
    private String modifyBy;

    /**
     * task group id
     */
    private int taskGroupId;
    /**
     * task group id
     */
    private int taskGroupPriority;
}
