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

import org.metahut.starfish.api.controller.CollectorTaskController;
import org.metahut.starfish.api.dto.CollectorTaskCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceLogResponseDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceResponseDTO;
import org.metahut.starfish.api.dto.CollectorTaskRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.server.service.CollectorTaskService;

import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CollectorTaskControllerImpl implements CollectorTaskController {

    private final CollectorTaskService collectorTaskService;

    public CollectorTaskControllerImpl(CollectorTaskService collectorTaskService) {
        this.collectorTaskService = collectorTaskService;
    }

    @Override
    public ResultEntity<CollectorTaskResponseDTO> create(CollectorTaskCreateOrUpdateRequestDTO requestDTO) {
        return ResultEntity.success(collectorTaskService.create(requestDTO));
    }

    @Override
    public ResultEntity<CollectorTaskResponseDTO> update(Long id, CollectorTaskCreateOrUpdateRequestDTO requestDTO) {
        return ResultEntity.success(collectorTaskService.update(id, requestDTO));
    }

    @Override
    public ResultEntity deleteById(Long id) {
        collectorTaskService.deleteById(id);
        return ResultEntity.success();
    }

    @Override
    public ResultEntity<CollectorTaskResponseDTO> queryById(Long id) {
        return ResultEntity.success(collectorTaskService.queryById(id));
    }

    @Override
    public ResultEntity<PageResponseDTO<CollectorTaskResponseDTO>> queryListPage(CollectorTaskRequestDTO requestDTO) {
        return ResultEntity.success(collectorTaskService.queryListPage(requestDTO));
    }

    @Override
    public ResultEntity<Collection<CollectorTaskResponseDTO>> queryList(CollectorTaskRequestDTO requestDTO) {
        return ResultEntity.success(collectorTaskService.queryList(requestDTO));
    }

    @Override
    public ResultEntity<PageResponseDTO<CollectorTaskInstanceResponseDTO>> queryInstanceListPage(CollectorTaskInstanceRequestDTO requestDTO) {
        return ResultEntity.success(collectorTaskService.queryInstanceListPage(requestDTO));
    }

    @Override
    public ResultEntity<CollectorTaskInstanceLogResponseDTO> queryInstanceLog(Integer instanceId, Integer offset, Integer limit) {
        return ResultEntity.success(collectorTaskService.queryInstanceLog(instanceId, offset, limit));
    }
}
