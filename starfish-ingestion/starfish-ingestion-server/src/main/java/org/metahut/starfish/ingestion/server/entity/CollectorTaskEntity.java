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

package org.metahut.starfish.ingestion.server.entity;

import org.metahut.starfish.ingestion.server.utils.JSONUtils;

public class CollectorTaskEntity {

    private Long id;

    private String name;

    private String description;

    private CollectorAdapterEntity adapter;

    private Object parameter;

    private String cron;

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

    public CollectorAdapterEntity getAdapter() {
        return adapter;
    }

    public void setAdapter(CollectorAdapterEntity adapter) {
        this.adapter = adapter;
    }

    public String getParameter() {
        return this.parameter == null ? "{}" : JSONUtils.toJSONString(this.parameter);
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

    public String getSchedulerFlowCode() {
        return schedulerFlowCode;
    }

    public void setSchedulerFlowCode(String schedulerFlowCode) {
        this.schedulerFlowCode = schedulerFlowCode;
    }
}
