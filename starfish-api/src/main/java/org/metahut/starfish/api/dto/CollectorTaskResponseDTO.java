/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.metahut.starfish.api.dto;

import org.metahut.starfish.api.JSONUtils;
import org.metahut.starfish.api.enums.ReleaseStateEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "collector task response dto")
public class CollectorTaskResponseDTO {

    private Long id;

    @ApiModelProperty(value = "collector task name")
    private String name;

    private String description;

    @ApiModelProperty(value = "collector adapter")
    private CollectorAdapterResponseDTO adapter;

    @ApiModelProperty(value = "collector task plugin parameter")
    private Object parameter;

    @ApiModelProperty(value = "cron expression")
    private String cron;

    private ReleaseStateEnum state;

    private String schedulerFlowCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CollectorAdapterResponseDTO getAdapter() {
        return adapter;
    }

    public void setAdapter(CollectorAdapterResponseDTO adapter) {
        this.adapter = adapter;
    }

    public String getParameter() {
        return JSONUtils.toJSONString(parameter);
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public ReleaseStateEnum getState() {
        return state;
    }

    public void setState(ReleaseStateEnum state) {
        this.state = state;
    }

    public String getSchedulerFlowCode() {
        return schedulerFlowCode;
    }

    public void setSchedulerFlowCode(String schedulerFlowCode) {
        this.schedulerFlowCode = schedulerFlowCode;
    }
}
