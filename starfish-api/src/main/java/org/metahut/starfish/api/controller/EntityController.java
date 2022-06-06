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

package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.EntityQueryDTO;
import org.metahut.starfish.api.dto.HiveClusterQueryDTO;
import org.metahut.starfish.api.dto.HiveClusterResponseDTO;
import org.metahut.starfish.api.dto.HiveDBQueryDTO;
import org.metahut.starfish.api.dto.HiveDBResponseDTO;
import org.metahut.starfish.api.dto.HiveTableQueryDTO;
import org.metahut.starfish.api.dto.HiveTableResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.PulsarClusterQueryDTO;
import org.metahut.starfish.api.dto.PulsarClusterResponseDTO;
import org.metahut.starfish.api.dto.PulsarNamespaceQueryDTO;
import org.metahut.starfish.api.dto.PulsarNamespaceResponseDTO;
import org.metahut.starfish.api.dto.PulsarTopicQueryDTO;
import org.metahut.starfish.api.dto.PulsarTopicResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.dto.SourceRequestDTO;
import org.metahut.starfish.api.dto.SourceResponseDTO;
import org.metahut.starfish.unit.IdTypeNameQueryCondition;
import org.metahut.starfish.unit.TypeNameQueryCondition;
import org.metahut.starfish.unit.TypeNameQueryConditionWithPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Map;

@Api(tags = "ENTITY_TAG")
@RequestMapping("entity")
public interface EntityController {

    @GetMapping("queryById")
    @ApiOperation(value = "queryById", notes = "queryById")
    ResultEntity<Map> queryById(Long id);

    @GetMapping("queryTypeByInstanceId")
    @ApiOperation(value = "queryTypeByInstanceId", notes = "queryTypeByInstanceId")
    ResultEntity<Object> queryTypeByInstanceId(Long id);

    @PostMapping("queryByIdAndTypeNameAndCondition")
    @ApiOperation(value = "queryByIdAndTypeNameAndCondition", notes = "QUERY_NODE_BY_ID_AND_TYPENAME_AND_CONDITION")
    ResultEntity<Map> queryByIdAndTypeNameAndCondition(@RequestBody IdTypeNameQueryCondition idTypeNameQueryCondition);

    @GetMapping("queryByTypeNameAndCondition")
    @ApiOperation(value = "queryByTypeNameAndCondition", notes = "QUERY_NODE_BY_TYPENAME_AND_CONDITION")
    ResultEntity<Collection<Map>> queryByTypeNameAndCondition(TypeNameQueryCondition typeNameQueryCondition);

    @GetMapping("queryByTypeNameAndConditionWithPage")
    @ApiOperation(value = "queryByTypeNameAndConditionWithPage", notes = "QUERY_NODE_BY_TYPENAME_AND_CONDITION_WITH_PAGE")
    ResultEntity<PageResponseDTO<Map>> queryByTypeNameAndConditionWithPage(TypeNameQueryConditionWithPage condition);

    @GetMapping("querySample")
    @ApiOperation(value = "querySample", notes = "QUERY_SAMPLE")
    ResultEntity<EntityQueryDTO> querySample(EntityQueryDTO entityQueryDTO);

    @GetMapping("hiveClusters")
    @ApiOperation(value = "hiveClusters", notes = "ALL_HIVE_CLUSTER_INFOS")
    ResultEntity<PageResponseDTO<HiveClusterResponseDTO>> hiveClusters(HiveClusterQueryDTO hiveClusterQueryDTO);

    @GetMapping("hiveDbs")
    @ApiOperation(value = "hiveDbs", notes = "ALL_HIVE_DB_INFOS")
    ResultEntity<PageResponseDTO<HiveDBResponseDTO>> hiveDbs(HiveDBQueryDTO hiveDBQueryDTO);

    @GetMapping("hiveTables")
    @ApiOperation(value = "hiveTables", notes = "HIVE_TABLES_WITH_PAGE")
    ResultEntity<PageResponseDTO<HiveTableResponseDTO>> hiveTables(HiveTableQueryDTO hiveTableQueryDTO);

    @GetMapping("pulsarClusters")
    @ApiOperation(value = "pulsarClusters", notes = "ALL_PULSAR_CLUSTER_INFOS")
    ResultEntity<PageResponseDTO<PulsarClusterResponseDTO>> pulsarClusters(PulsarClusterQueryDTO pulsarClusterQueryDTO);

    @GetMapping("pulsarNamespaces")
    @ApiOperation(value = "pulsarNamespaces", notes = "ALL_PULSAR_NAMESPACE_INFOS")
    ResultEntity<PageResponseDTO<PulsarNamespaceResponseDTO>> pulsarNamespaces(PulsarNamespaceQueryDTO pulsarNamespaceQueryDTO);

    @GetMapping("pulsarTopics")
    @ApiOperation(value = "pulsarTopics", notes = "PULSAR_TOPIC_WITH_PAGE")
    ResultEntity<PageResponseDTO<PulsarTopicResponseDTO>> pulsarTopics(PulsarTopicQueryDTO pulsarTopicQueryDTO);

    @GetMapping("sources")
    @ApiOperation(value = "sources", notes = "ALL_SOURCES_WITH_PAGE")
    ResultEntity<PageResponseDTO<SourceResponseDTO>> sources(SourceRequestDTO sourceRequestDTO);

}
