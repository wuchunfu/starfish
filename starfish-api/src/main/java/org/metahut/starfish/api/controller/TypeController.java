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

import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.dto.TypeRequestBatchCreateOrUpdateDTO;
import org.metahut.starfish.api.dto.TypeResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "TYPE_TAG")
@RequestMapping("type")
public interface TypeController {

    @PostMapping("init")
    @ApiOperation(value = "init", notes = "BATCH_CREATE_TYPE_NOTES")
    ResultEntity<TypeResponseDTO> init(@RequestBody TypeRequestBatchCreateOrUpdateDTO requestDTO);

}
