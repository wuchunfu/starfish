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

package org.metahut.starfish.ingestion.common;

import org.metahut.starfish.message.api.IMessageManager;
import org.metahut.starfish.message.api.MessageType;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;

public class MetaMessageProducerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetaMessageProducerTest.class);

    @Test
    public void testServiceLoader() {

        Map<MessageType, IMessageManager> managerMap = new HashMap<>();
        ServiceLoader.load(IMessageManager.class).forEach(manager -> {

            MessageType type = manager.getType();

            IMessageManager messageManager = managerMap.get(type);

            if (Objects.nonNull(messageManager)) {
                throw new IllegalArgumentException(MessageFormat.format("Duplicate message type exists: {0}", type));
            }
            LOGGER.info("type:{}, message manager:{}", type, manager);
            managerMap.put(type, manager);

        });
    }
}
