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
import org.metahut.starfish.api.dto.SelectItemRequestDTO;
import org.metahut.starfish.api.dto.SelectItemResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Api(tags = "SELECT_ITEM_TAG")
@RequestMapping("selectItem")
public interface SelectItemController {

    @ApiOperation(value = "queryList", notes = "QUERY_SELECT_ITEM_LIST_NOTES")
    @GetMapping("queryList")
    ResultEntity<Map<String, List<SelectItemResponseDTO>>> queryList(SelectItemRequestDTO selectItemRequestDTO);
}
