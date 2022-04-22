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

package org.metahut.starfish.ingestion.server.controller;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.IngestionException;
import org.metahut.starfish.ingestion.collector.api.JSONUtils;
import org.metahut.starfish.ingestion.server.collector.CollectorPluginHelper;
import org.metahut.starfish.ingestion.server.dto.CollectorExecuteRequestDTO;
import org.metahut.starfish.ingestion.server.dto.ResultEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("collector")
public class CollectorController {

    private CollectorPluginHelper collectorPluginHelper;

    public CollectorController(CollectorPluginHelper collectorHelper) {
        this.collectorPluginHelper = collectorHelper;
    }

    @PostMapping("execute")
    public ResultEntity execute(@RequestBody CollectorExecuteRequestDTO collectorExecuteRequestDTO) {

        AbstractCollectorParameter parameter = JSONUtils.parseObject(collectorExecuteRequestDTO.getParameter(), AbstractCollectorParameter.class);
        if (Objects.isNull(parameter) || !parameter.checkParameter()) {
            throw new IngestionException("collector parameter check exception");
        }

        // TODO
        String datasourceParameter = null;

        parameter.setDatasourceParameter(datasourceParameter);

        ICollector collector = collectorPluginHelper.generateInstance(parameter);
        return ResultEntity.success(collector.execute());
    }
}
