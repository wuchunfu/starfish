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

import org.metahut.starfish.api.controller.CollectorAdapterController;
import org.metahut.starfish.api.dto.CollectorAdapterCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.server.service.CollectorAdapterService;
import org.metahut.starfish.server.utils.Assert;

import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.metahut.starfish.api.enums.Status.COLLECTOR_ADAPTER_TEST_CONNECTION_FAIL;

@RestController
public class CollectorAdapterControllerImpl implements CollectorAdapterController {

    private final CollectorAdapterService collectorAdapterService;

    public CollectorAdapterControllerImpl(CollectorAdapterService collectorAdapterService) {
        this.collectorAdapterService = collectorAdapterService;
    }

    @Override
    public ResultEntity testConnection(String type, String parameter) {
        CollectorResult collectorResult = collectorAdapterService.testConnection(type, parameter);
        Assert.notTrue(collectorResult.getState(), COLLECTOR_ADAPTER_TEST_CONNECTION_FAIL, collectorResult.getMessage());
        return ResultEntity.success();
    }

    @Override
    public ResultEntity<CollectorAdapterResponseDTO> create(CollectorAdapterCreateOrUpdateRequestDTO requestDTO) {
        return ResultEntity.success(collectorAdapterService.create(requestDTO));
    }

    @Override
    public ResultEntity<CollectorAdapterResponseDTO> update(Long id, CollectorAdapterCreateOrUpdateRequestDTO requestDTO) {
        return ResultEntity.success(collectorAdapterService.update(id, requestDTO));
    }

    @Override
    public ResultEntity deleteById(Long id) {
        collectorAdapterService.deleteById(id);
        return ResultEntity.success();
    }

    @Override
    public ResultEntity<CollectorAdapterResponseDTO> queryById(Long id) {
        return ResultEntity.success(collectorAdapterService.queryById(id));
    }

    @Override
    public ResultEntity<PageResponseDTO<CollectorAdapterResponseDTO>> queryListPage(CollectorAdapterRequestDTO requestDTO) {
        return ResultEntity.success(collectorAdapterService.queryListPage(requestDTO));
    }

    @Override
    public ResultEntity<Collection<CollectorAdapterResponseDTO>> queryList(CollectorAdapterRequestDTO requestDTO) {
        Collection<CollectorAdapterResponseDTO> collection = collectorAdapterService.queryList(requestDTO);
        return ResultEntity.success(collection);
    }
}
