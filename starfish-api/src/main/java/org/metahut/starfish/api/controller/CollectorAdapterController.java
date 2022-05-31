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

import org.metahut.starfish.api.dto.CollectorAdapterCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Api(tags = "COLLECTOR_ADAPTER_TAG")
@RequestMapping("collectorAdapter")
@Validated
public interface CollectorAdapterController {

    @GetMapping("testConnection")
    @ApiOperation(value = "testConnection", notes = "TEST_CONNECTION_COLLECTOR_ADAPTER_NOTES")
    ResultEntity testConnection(@RequestParam String type, @RequestParam String parameter);

    @PostMapping
    @ApiOperation(value = "create", notes = "CREATE_COLLECTOR_ADAPTER_NOTES")
    ResultEntity<CollectorAdapterResponseDTO> create(@RequestBody @Validated CollectorAdapterCreateOrUpdateRequestDTO requestDTO);

    @PutMapping("{id}")
    @ApiOperation(value = "update", notes = "UPDATE_COLLECTOR_ADAPTER_NOTES")
    ResultEntity<CollectorAdapterResponseDTO> update(@PathVariable(value = "id") Long id, @RequestBody @Validated CollectorAdapterCreateOrUpdateRequestDTO requestDTO);

    @DeleteMapping("{id}")
    @ApiOperation(value = "deleteById", notes = "DELETE_COLLECTOR_ADAPTER_BY_ID_NOTES")
    ResultEntity deleteById(@PathVariable(value = "id") Long id);

    @GetMapping("{id}")
    @ApiOperation(value = "queryDatasourceById", notes = "QUERY_COLLECTOR_ADAPTER_BY_ID_NOTES")
    ResultEntity<CollectorAdapterResponseDTO> queryById(@PathVariable(value = "id") Long id);

    @GetMapping("queryListPage")
    @ApiOperation(value = "queryListPage", notes = "QUERY_COLLECTOR_ADAPTER_PAGE_NOTES")
    ResultEntity<PageResponseDTO<CollectorAdapterResponseDTO>> queryListPage(CollectorAdapterRequestDTO requestDTO);

    @GetMapping("queryList")
    @ApiOperation(value = "queryList", notes = "QUERY_COLLECTOR_ADAPTER_LIST_NOTES")
    ResultEntity<Collection<CollectorAdapterResponseDTO>> queryList(CollectorAdapterRequestDTO requestDTO);

}
