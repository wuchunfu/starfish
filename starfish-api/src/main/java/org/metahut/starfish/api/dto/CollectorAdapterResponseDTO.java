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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "collector adapter response dto")
public class CollectorAdapterResponseDTO {

    @ApiModelProperty(value = "collector adapter id")
    private Long id;

    @ApiModelProperty(value = "collector adapter name")
    private String name;

    @ApiModelProperty(value = "collector type")
    private String type;

    @ApiModelProperty(value = "collector adapter parameter to connect")
    private Object parameter;

    @ApiModelProperty(value = "collector adapter description")
    private String description;

    @ApiModelProperty(value = "collector adapter create time")
    private Date createTime;

    @ApiModelProperty(value = "collector adapter update time")
    private Date updateTime;

    @ApiModelProperty(value = "collector adapter operator")
    private Integer operator;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getParameter() {
        return this.parameter == null ? "{}" : JSONUtils.toJSONString(this.parameter);
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
