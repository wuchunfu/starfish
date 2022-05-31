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

package org.metahut.starfish.ingestion.server.service.impl;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollectorTask;
import org.metahut.starfish.ingestion.server.collector.CollectorPluginHelper;
import org.metahut.starfish.ingestion.server.dto.CollectorExecuteRequestDTO;
import org.metahut.starfish.ingestion.server.entity.CollectorTaskEntity;
import org.metahut.starfish.ingestion.server.exception.BusinessException;
import org.metahut.starfish.ingestion.server.service.CollectorService;
import org.metahut.starfish.ingestion.server.utils.JSONUtils;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class CollectorServiceImpl implements CollectorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectorServiceImpl.class);

    private final CollectorPluginHelper collectorPluginHelper;
    private final AbstractMetaDataService<Long, Object> metaDataService;

    public CollectorServiceImpl(CollectorPluginHelper collectorPluginHelper, AbstractMetaDataService<Long, Object> metaDataService) {
        this.collectorPluginHelper = collectorPluginHelper;
        this.metaDataService = metaDataService;
    }

    @Override
    public CollectorResult execute(CollectorExecuteRequestDTO requestDTO) {

        // collector_name, description, datasourceId, collector_params, crontab, scheduler_flow_code，scheduler_cron_code???, state

        LOGGER.info("query collector task entity, condition:{}", JSONUtils.toJSONString(requestDTO.toCondition()));
        Optional<CollectorTaskEntity> first = metaDataService.instances(requestDTO.toCondition()).stream().findFirst();

        if (!first.isPresent()) {
            throw new BusinessException(MessageFormat.format("collector task entity not exists, id:{0}", requestDTO.getId()));
        }

        CollectorTaskEntity instance = first.get();
        LOGGER.info("query collector task entity, result:{}", JSONUtils.toJSONString(instance));

        ICollectorTask collector = collectorPluginHelper.generateTaskInstance(instance.getAdapter().getType(), instance.getAdapter().getParameter(), instance.getParameter());
        return collector.execute();
    }
}
