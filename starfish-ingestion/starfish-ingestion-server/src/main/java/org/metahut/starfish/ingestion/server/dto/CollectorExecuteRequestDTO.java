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

package org.metahut.starfish.ingestion.server.dto;

import org.metahut.starfish.ingestion.server.entity.CollectorTaskEntity;
import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.enums.LinkCategory;
import org.metahut.starfish.unit.enums.RelationType;
import org.metahut.starfish.unit.expression.ConditionPiece;
import org.metahut.starfish.unit.expression.EachPointer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.metahut.starfish.ingestion.server.utils.Constants.RELATION_PROPERTY_COLLECTOR_TASK_ADAPTER;
import static org.metahut.starfish.ingestion.server.utils.Constants.TYPE_NAME_COLLECTOR_TASK;

public class CollectorExecuteRequestDTO {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AbstractQueryCondition<CollectorTaskEntity> toCondition() {
        AbstractQueryCondition<CollectorTaskEntity> condition = new AbstractQueryCondition<>();
        condition.setResultType(CollectorTaskEntity.class);
        condition.setEachPointers(eachPointerMap());
        condition.setFilters(Arrays.asList(collectorTaskCondition()));
        return condition;
    }

    private Map<String, EachPointer> eachPointerMap() {
        Map<String, EachPointer> map = new HashMap<>();
        EachPointer topicParent = new EachPointer(LinkCategory.RELATIONSHIP, RelationType.CHILD);
        map.put(RELATION_PROPERTY_COLLECTOR_TASK_ADAPTER, topicParent);
        return map;
    }

    private ConditionPiece collectorTaskCondition() {
        ConditionPiece conditionPiece = ConditionPiece.entityWithTypeAndQualifiedName(TYPE_NAME_COLLECTOR_TASK, this.id, null);
        return conditionPiece;
    }
}
