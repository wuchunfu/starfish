package org.metahut.starfish.scheduler.dolphinscheduler.entity;

import org.metahut.starfish.scheduler.dolphinscheduler.parameter.HttpProperty;

import java.util.List;

/**
 * @author Hua Jiang
 */

public class TaskParameter {

    /**
     * url
     */
    private String url;

    /**
     * httpMethod
     */
    private String httpMethod;

    /**
     *  http params
     */
    private List<HttpProperty> httpParams;

    /**
     * condition
     */
    private String condition;

    /**
     * Connect Timeout
     * Unit: ms
     */
    private int connectTimeout;

    /**
     * Socket Timeout
     * Unit: ms
     */
    private int socketTimeout;

}
