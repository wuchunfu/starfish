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

package org.metahut.starfish.ingestion.server.message;

import org.metahut.starfish.message.api.ConsumerResult;
import org.metahut.starfish.message.api.MessageConsumer;
import org.metahut.starfish.message.api.MessageException;
import org.metahut.starfish.message.api.MessageManager;
import org.metahut.starfish.message.api.MessageType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.util.List;
import java.util.Objects;

import static org.metahut.starfish.message.api.Constants.MESSAGE_META_EVENT;

@Component
public class MessageHelper {

    private static final Logger logger = LoggerFactory.getLogger(MessageHelper.class);

    private final MessageManager messageManager;

    public MessageHelper(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    @PostConstruct
    private void autoConsumer() {
        autoMetaEventConsumer();
    }

    @Async
    public void autoMetaEventConsumer() {
        if (MessageType.none == messageManager.getType()) {
            return;
        }
        MessageConsumer consumer = messageManager.getConsumer(MESSAGE_META_EVENT);
        if (Objects.isNull(consumer)) {
            throw new NullPointerException("meta event consumer is null");
        }
        while (true) {
            try {
                List<ConsumerResult> result = consumer.batchReceive();

                // TODO Store to metadata

            } catch (MessageException e) {
                // TODO How to consume or handle exceptions
                logger.error(e.getMessage(), e);
            }
        }
    }

    @PreDestroy
    public void close() throws Exception {
        messageManager.close();
    }
}
