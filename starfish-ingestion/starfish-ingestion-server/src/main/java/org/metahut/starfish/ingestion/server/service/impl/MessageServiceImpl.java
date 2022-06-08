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

import org.metahut.starfish.ingestion.server.service.MessageService;
import org.metahut.starfish.ingestion.server.utils.JSONUtils;
import org.metahut.starfish.message.api.ConsumerResult;
import org.metahut.starfish.message.api.IMessageConsumer;
import org.metahut.starfish.message.api.IMessageManager;
import org.metahut.starfish.message.api.MessageType;
import org.metahut.starfish.service.AbstractMetaDataService;
import org.metahut.starfish.unit.row.RowData;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.metahut.starfish.message.api.Constants.MESSAGE_CONSUMER_MAP_KEY_META;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    private final IMessageManager messageManager;
    private final AbstractMetaDataService<Long, Object> metaDataService;

    public MessageServiceImpl(IMessageManager messageManager, AbstractMetaDataService<Long, Object> metaDataService) {
        this.messageManager = messageManager;
        this.metaDataService = metaDataService;
    }

    @Async("taskExecutor")
    @Override
    public void toMetaEventConsumer() {
        if (MessageType.none == messageManager.getType()) {
            return;
        }
        IMessageConsumer consumer = messageManager.getConsumer(MESSAGE_CONSUMER_MAP_KEY_META);
        if (Objects.isNull(consumer)) {
            throw new NullPointerException("meta event consumer is null");
        }
        while (true) {
            if (!consumer.isRunning()) {
                break;
            }
            try {
                List<ConsumerResult> result = consumer.batchReceive();
                for (ConsumerResult consumerResult : result) {
                    RowData<Object> rowData = JSONUtils.parseObject(consumerResult.getValue(), RowData.class);
                    metaDataService.batchCreateOrUpdate(rowData);
                }
            } catch (Throwable e) {
                // TODO How to consume or handle exceptions
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
