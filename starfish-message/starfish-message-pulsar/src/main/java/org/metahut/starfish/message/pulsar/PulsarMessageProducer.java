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

import org.metahut.starfish.message.api.IMessageProducer;
import org.metahut.starfish.message.api.MessageException;
import org.metahut.starfish.message.api.MessageResult;

import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;

import java.text.MessageFormat;
import java.util.Objects;

public class PulsarMessageProducer implements IMessageProducer {

    private final Producer producer;

    public PulsarMessageProducer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public MessageResult send(String key, String value) throws MessageException {
        MessageId messageId = null;
        try {
            messageId = producer.newMessage()
                    .key(key)
                    .value(value.getBytes())
                    .send();
            return MessageResult.of(producer.getTopic(), messageId.toString(), null);
        } catch (PulsarClientException e) {
            throw new MessageException(MessageFormat.format("Pulsar producer send data exception, topic:{0}, messageId:{1}, key:{2}, value:{3}",
                    producer.getTopic(), Objects.nonNull(messageId) ? messageId.toString() : null, key, value), e);
        }
    }

    @Override
    public void close() throws Exception {
        if (Objects.nonNull(producer)) {
            producer.close();
        }

    }
}
