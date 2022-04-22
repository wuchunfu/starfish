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

package org.metahut.starfish.message.pulsar;

import org.metahut.starfish.message.api.ConsumerResult;
import org.metahut.starfish.message.api.IMessageConsumer;
import org.metahut.starfish.message.api.IMessageProducer;
import org.metahut.starfish.message.api.MessageException;
import org.metahut.starfish.message.api.MessageProperties;
import org.metahut.starfish.message.api.MessageType;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Disabled
class PulsarMetaMessageManagerTest {

    private static final Logger logger = LoggerFactory.getLogger(PulsarMetaMessageManagerTest.class);

    private PulsarMessageManager pulsarMessageManager;

    private static final String PULSAR_SERVICE_URL = "http://pulsar-idc-qa.zpidc.com:8080";
    private static final String PULSAR_PRODUCER_1_NAME = "metaProducer";

    @BeforeEach
    public void before() throws MessageException {

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setType(MessageType.pulsar);
        MessageProperties.Pulsar pulsar = messageProperties.getPulsar();
        pulsar.setServiceUrl(PULSAR_SERVICE_URL);

        // set pulsar producer
        Map<String, MessageProperties.PulsarProducer> producerMap = new HashMap<>();
        MessageProperties.PulsarProducer pulsarProducer = new MessageProperties.PulsarProducer();
        pulsarProducer.setTopicName("my-topic");
        pulsarProducer.setProducerName("producer-1");
        producerMap.put(PULSAR_PRODUCER_1_NAME, pulsarProducer);
        pulsar.setProducers(producerMap);

        Map<String, MessageProperties.PulsarConsumer> consumerMap = new HashMap<>();

        pulsar.setConsumers(consumerMap);

        pulsarMessageManager = new PulsarMessageManager(messageProperties);
    }

    @AfterEach
    public void after() throws Exception {
        if (Objects.nonNull(pulsarMessageManager)) {
            pulsarMessageManager.close();
        }
    }

    @Test
    public void testProducer() throws MessageException {
        IMessageProducer producer = pulsarMessageManager.getProducer(PULSAR_PRODUCER_1_NAME);
        producer.send("k1", "v1");
    }

    @Test
    public void testConsumer() throws MessageException {
        IMessageConsumer consumer = pulsarMessageManager.getConsumer(PULSAR_PRODUCER_1_NAME);

        while (true) {
            try {
                List<ConsumerResult> result = consumer.batchReceive();

            } catch (MessageException e) {
                logger.error(e.getMessage(), e);
            }
        }

    }

}