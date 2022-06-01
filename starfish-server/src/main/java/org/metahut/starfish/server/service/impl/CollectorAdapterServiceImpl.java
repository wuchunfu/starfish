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

package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.CollectorAdapterCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.server.collector.CollectorPluginParameterHelper;
import org.metahut.starfish.server.service.CollectorAdapterService;
import org.metahut.starfish.server.utils.Assert;
import org.metahut.starfish.service.AbstractMetaDataService;
import org.metahut.starfish.unit.EntityNameGentrator;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

import static org.metahut.starfish.api.Constants.COLLECTOR_ADAPTER_TYPE_NAME;
import static org.metahut.starfish.api.enums.Status.COLLECTOR_ADAPTER_TEST_CONNECTION_FAIL;

@Service
public class CollectorAdapterServiceImpl implements CollectorAdapterService {

    private final CollectorPluginParameterHelper collectorPluginParameterHelper;
    private final ConversionService conversionService;
    private final AbstractMetaDataService<Long, Object> metaDataService;

    public CollectorAdapterServiceImpl(CollectorPluginParameterHelper collectorPluginParameterHelper, ConversionService conversionService, AbstractMetaDataService<Long, Object> metaDataService) {
        this.collectorPluginParameterHelper = collectorPluginParameterHelper;
        this.conversionService = conversionService;
        this.metaDataService = metaDataService;
    }

    @Override
    public CollectorResult testConnection(String type, String parameter) {
        return collectorPluginParameterHelper.testAdapterConnection(type, parameter);
    }

    @Override
    public CollectorAdapterResponseDTO create(CollectorAdapterCreateOrUpdateRequestDTO requestDTO) {
        CollectorResult collectorResult = this.testConnection(requestDTO.getType(), requestDTO.getParameter());
        Assert.notTrue(collectorResult.getState(), COLLECTOR_ADAPTER_TEST_CONNECTION_FAIL, collectorResult.getMessage());
        Map<String, Object> convert = conversionService.convert(requestDTO, Map.class);
        Long entityId = metaDataService.createEntityByTypeName(COLLECTOR_ADAPTER_TYPE_NAME, EntityNameGentrator.generateName(COLLECTOR_ADAPTER_TYPE_NAME, requestDTO.getName()), convert);
        CollectorAdapterResponseDTO collectorAdapterResponseDTO = new CollectorAdapterResponseDTO();
        collectorAdapterResponseDTO.setId(entityId);
        return collectorAdapterResponseDTO;
    }

    @Override
    public CollectorAdapterResponseDTO update(Long id, CollectorAdapterCreateOrUpdateRequestDTO requestDTO) {
        CollectorResult collectorResult = this.testConnection(requestDTO.getType(), requestDTO.getParameter());
        Assert.notTrue(collectorResult.getState(), COLLECTOR_ADAPTER_TEST_CONNECTION_FAIL, collectorResult.getMessage());
        Map<String, Object> convert = conversionService.convert(requestDTO, Map.class);
        metaDataService.updateEntity(id, EntityNameGentrator.generateName(COLLECTOR_ADAPTER_TYPE_NAME, requestDTO.getName()), convert);
        CollectorAdapterResponseDTO collectorAdapterResponseDTO = new CollectorAdapterResponseDTO();
        collectorAdapterResponseDTO.setId(id);
        return collectorAdapterResponseDTO;
    }

    @Override
    public void deleteById(Long id) {
        metaDataService.deleteEntity(id);
    }

    @Override
    public CollectorAdapterResponseDTO queryById(Long id) {
        return metaDataService.instance(id, CollectorAdapterResponseDTO.class);
    }

    @Override
    public PageResponseDTO<CollectorAdapterResponseDTO> queryListPage(CollectorAdapterRequestDTO requestDTO) {
        Pageable pageable = PageRequest.of(requestDTO.getPageNo() - 1, requestDTO.getPageSize());
        // TODO Assembly conditions
        Page<CollectorAdapterResponseDTO> page = metaDataService.instances(requestDTO.toQueryCondition(),pageable);
        return PageResponseDTO.of(page.getNumber(), page.getSize(), page.getTotalElements(), page.getContent());
    }

    @Override
    public Collection<CollectorAdapterResponseDTO> queryList(CollectorAdapterRequestDTO requestDTO) {
        // TODO Assembly conditions
        return metaDataService.instances(requestDTO.toQueryCondition());
    }

}
