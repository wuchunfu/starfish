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

import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.enums.LinkCategory;
import org.metahut.starfish.unit.enums.RelationType;
import org.metahut.starfish.unit.enums.TableType;
import org.metahut.starfish.unit.expression.ConditionPiece;
import org.metahut.starfish.unit.expression.EachPointer;
import org.metahut.starfish.unit.expression.Expression;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.metahut.starfish.api.Constants.COLLECTOR_TASK_TYPE_NAME;
import static org.metahut.starfish.api.Constants.PROPERTY_COLLECTOR_TASK_ADAPTER_RELATION;

@ApiModel(description = "collector task request dto")
public class CollectorTaskRequestDTO extends PageRequestDTO {

    @ApiModelProperty(value = "collector adapter name")
    private String adapterName;

    @ApiModelProperty(value = "collector adapter type")
    private String type;

    @ApiModelProperty(value = "collector task name")
    private String name;

    @ApiModelProperty(value = "update begin time")
    private Date updateBeginTime;

    @ApiModelProperty(value = "update end time")
    private Date updateEndTime;

    @ApiModelProperty(value = "collector task id")
    private Long taskId;

    public String getAdapterName() {
        return adapterName;
    }

    public void setAdapterName(String adapterName) {
        this.adapterName = adapterName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdateBeginTime() {
        return updateBeginTime;
    }

    public void setUpdateBeginTime(Date updateBeginTime) {
        this.updateBeginTime = updateBeginTime;
    }

    public Date getUpdateEndTime() {
        return updateEndTime;
    }

    public void setUpdateEndTime(Date updateEndTime) {
        this.updateEndTime = updateEndTime;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public AbstractQueryCondition<CollectorTaskResponseDTO> toQueryCondition() {
        AbstractQueryCondition<CollectorTaskResponseDTO> condition = new AbstractQueryCondition<>();
        condition.setResultType(CollectorTaskResponseDTO.class);
        condition.setFilters(Arrays.asList(collectorTaskPiece()));
        condition.setEachPointers(eachPointerMap());
        return condition;
    }

    private Map<String, EachPointer> eachPointerMap() {
        Map<String, EachPointer> map = new HashMap<>();
        EachPointer eachPointer = new EachPointer();
        eachPointer.setCategory(LinkCategory.RELATIONSHIP);
        eachPointer.setRelationType(RelationType.CHILD);
        map.put(PROPERTY_COLLECTOR_TASK_ADAPTER_RELATION, eachPointer);
        return map;
    }

    /**
     * ENTITY name
     *
     * @return
     */
    private ConditionPiece collectorTaskPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setExpressions(new ArrayList<>());
        conditionPiece.getExpressions().addAll(Expression.entity());
        if (Objects.nonNull(this.taskId)) {
            conditionPiece.getExpressions().addAll(Arrays.asList(Expression.id(taskId)));
        }
        Map<String, List<ConditionPiece>> map = new HashMap<>();
        map.putAll(rel1());
        if (StringUtils.isNotEmpty(this.name)) {
            map.putAll(rel0());
        }
        if (StringUtils.isNotEmpty(adapterName) || StringUtils.isNotEmpty(type)) {
            map.putAll(rel3());
        }
        conditionPiece.setNextConditionChain(map);
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> rel0() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.PROPERTIES, Arrays.asList(propertyNamePiece()));
        return result;
    }

    private ConditionPiece propertyNamePiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(
            Arrays.asList(Expression.and(Expression.keyValueLike(Expression.NAME, this.name))));
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> rel1() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.PARENT, Arrays.asList(typeEntityRelationPiece()));
        return result;
    }

    private ConditionPiece typeEntityRelationPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(
            Expression.rel(LinkCategory.TYPE_ENTITY, LinkCategory.TYPE_ENTITY.name()));
        conditionPiece.setNextConditionChain(rel2());
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> rel2() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.START_NODE_ENTITY, Arrays.asList(collectorTaskTypePiece()));
        return result;
    }

    private ConditionPiece collectorTaskTypePiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setExpressions(Expression.type(COLLECTOR_TASK_TYPE_NAME));
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> rel3() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put("children", Arrays.asList(entityRel()));
        return result;
    }

    private ConditionPiece entityRel() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(
            Expression.rel(LinkCategory.RELATIONSHIP, PROPERTY_COLLECTOR_TASK_ADAPTER_RELATION));
        conditionPiece.setNextConditionChain(rel4());
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> rel4() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put("endNodeEntity", Arrays.asList(collectorAdapterPiece()));
        return result;
    }

    private ConditionPiece collectorAdapterPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setNextConditionChain(rel6());
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> rel6() {
        Map<String, List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.PROPERTIES, Arrays.asList(propertyPiece1()));
        return result;
    }

    private ConditionPiece propertyPiece1() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(new ArrayList<>());
        if (StringUtils.isNotEmpty(this.adapterName)) {
            conditionPiece.getExpressions()
                .add(Expression.and(Expression.keyValueLike("name", this.adapterName)));
        }
        if (StringUtils.isNotEmpty(this.type)) {
            conditionPiece.getExpressions()
                .add(Expression.and(Expression.keyValueLike("type", this.type)));
        }
        return conditionPiece;
    }

}
