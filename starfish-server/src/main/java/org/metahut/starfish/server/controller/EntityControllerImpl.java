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

package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.EntityController;
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
import org.metahut.starfish.service.AbstractMetaDataService;
import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.TypeNameQueryCondition;
import org.metahut.starfish.unit.TypeNameQueryConditionWithPage;
import org.metahut.starfish.unit.expression.ConditionPiece;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RestController
public class EntityControllerImpl implements EntityController {

    @Resource
    private AbstractMetaDataService<Long,Object> abstractMetaDataService;

    @Override
    public ResultEntity<EntityQueryDTO> querySample(EntityQueryDTO entityQueryDTO) {
        return null;
    }

    @Override
    public ResultEntity<Map> queryById(Long id) {
        return ResultEntity.success(abstractMetaDataService.instance(id,Map.class));
    }

    @Override
    public ResultEntity<Collection<Map>> queryByTypeNameAndCondition(TypeNameQueryCondition typeNameQueryCondition) {
        AbstractQueryCondition<Map> condition = typeNameQueryCondition;
        if (condition.getFilters() == null) {
            condition.setFilters(new ArrayList<>());
        }
        condition.getFilters().add(ConditionPiece.entityWithType(typeNameQueryCondition.getTypeName()));
        return ResultEntity.success(abstractMetaDataService.instances(condition));
    }

    @Override
    public ResultEntity<PageResponseDTO<Map>> queryByTypeNameAndConditionWithPage(TypeNameQueryConditionWithPage typeNameQueryCondition) {
        AbstractQueryCondition<Map> condition = typeNameQueryCondition;
        if (condition.getFilters() == null) {
            condition.setFilters(new ArrayList<>());
        }
        condition.getFilters().add(ConditionPiece.entityWithType(typeNameQueryCondition.getTypeName()));
        Page<Map> result = abstractMetaDataService.instances(condition,PageRequest.of(typeNameQueryCondition.getPageNo() - 1, typeNameQueryCondition.getPageNo()));
        return ResultEntity.success(PageResponseDTO.of(result.getNumber(),result.getSize(),result.getTotalElements(),result.getContent()));
    }

    @Override
    public ResultEntity<PageResponseDTO<HiveClusterResponseDTO>> hiveClusters(HiveClusterQueryDTO hiveClusterQueryDTO) {
        PageRequest pageable = PageRequest.of(hiveClusterQueryDTO.getPageNo() - 1, hiveClusterQueryDTO.getPageSize());
        Page<HiveClusterResponseDTO> pageResult = abstractMetaDataService.instances(hiveClusterQueryDTO.toCondition(),pageable);
        return ResultEntity.success(PageResponseDTO.of(pageResult.getNumber(),pageResult.getSize(),pageResult.getTotalElements(),pageResult.getContent()));

    }

    @Override
    public ResultEntity<PageResponseDTO<HiveDBResponseDTO>> hiveDbs(HiveDBQueryDTO hiveDBQueryDTO) {
        PageRequest pageable = PageRequest.of(hiveDBQueryDTO.getPageNo() - 1, hiveDBQueryDTO.getPageSize());
        Page<HiveDBResponseDTO> pageResult = abstractMetaDataService.instances(hiveDBQueryDTO.toCondition(),pageable);
        return ResultEntity.success(PageResponseDTO.of(pageResult.getNumber(),pageResult.getSize(),pageResult.getTotalElements(),pageResult.getContent()));
    }

    @Override
    public ResultEntity<PageResponseDTO<HiveTableResponseDTO>> hiveTables(HiveTableQueryDTO hiveTableQueryDTO) {
        PageRequest pageable = PageRequest.of(hiveTableQueryDTO.getPageNo() - 1, hiveTableQueryDTO.getPageSize());
        Page<HiveTableResponseDTO> pageResult = abstractMetaDataService.instances(hiveTableQueryDTO.toCondition(),pageable);
        return ResultEntity.success(PageResponseDTO.of(pageResult.getNumber(),pageResult.getSize(),pageResult.getTotalElements(),pageResult.getContent()));
    }

    @Override
    public ResultEntity<PageResponseDTO<PulsarClusterResponseDTO>> pulsarClusters(PulsarClusterQueryDTO pulsarClusterQueryDTO) {
        PageRequest pageable = PageRequest.of(pulsarClusterQueryDTO.getPageNo() - 1, pulsarClusterQueryDTO.getPageSize());
        Page<PulsarClusterResponseDTO> pageResult = abstractMetaDataService.instances(pulsarClusterQueryDTO.toCondition(),pageable);
        return ResultEntity.success(PageResponseDTO.of(pageResult.getNumber(),pageResult.getSize(),pageResult.getTotalElements(),pageResult.getContent()));
    }

    @Override
    public ResultEntity<PageResponseDTO<PulsarNamespaceResponseDTO>> pulsarNamespaces(PulsarNamespaceQueryDTO pulsarNamespaceQueryDTO) {
        PageRequest pageable = PageRequest.of(pulsarNamespaceQueryDTO.getPageNo() - 1, pulsarNamespaceQueryDTO.getPageSize());
        Page<PulsarNamespaceResponseDTO> pageResult = abstractMetaDataService.instances(pulsarNamespaceQueryDTO.toCondition(),pageable);
        return ResultEntity.success(PageResponseDTO.of(pageResult.getNumber(),pageResult.getSize(),pageResult.getTotalElements(),pageResult.getContent()));

    }

    @Override
    public ResultEntity<PageResponseDTO<PulsarTopicResponseDTO>> pulsarTopics(PulsarTopicQueryDTO pulsarTopicQueryDTO) {
        PageRequest pageable = PageRequest.of(pulsarTopicQueryDTO.getPageNo() - 1, pulsarTopicQueryDTO.getPageSize());
        Page<PulsarTopicResponseDTO> pageResult = abstractMetaDataService.instances(pulsarTopicQueryDTO.toCondition(),pageable);
        return ResultEntity.success(PageResponseDTO.of(pageResult.getNumber(),pageResult.getSize(),pageResult.getTotalElements(),pageResult.getContent()));
    }

    @Override
    public ResultEntity<PageResponseDTO<SourceResponseDTO>> sources(SourceRequestDTO sourceRequestDTO) {
        PageRequest pageable = PageRequest.of(sourceRequestDTO.getPageNo() - 1, sourceRequestDTO.getPageSize());
        Page<SourceResponseDTO> pageResult = abstractMetaDataService.instances(sourceRequestDTO.toCondition(),pageable);
        return ResultEntity.success(PageResponseDTO.of(pageResult.getNumber(),pageResult.getSize(),pageResult.getTotalElements(),pageResult.getContent()));
    }
}
