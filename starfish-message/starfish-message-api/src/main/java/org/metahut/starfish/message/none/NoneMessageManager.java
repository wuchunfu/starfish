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

package org.metahut.starfish.message.none;

import org.metahut.starfish.message.api.IMessageConsumer;
import org.metahut.starfish.message.api.IMessageManager;
import org.metahut.starfish.message.api.IMessageProducer;
import org.metahut.starfish.message.api.MessageException;
import org.metahut.starfish.message.api.MessageProperties;
import org.metahut.starfish.message.api.MessageType;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static org.metahut.starfish.message.api.Constants.MESSAGE_CONFIG_PREFIX;
import static org.metahut.starfish.message.api.MessageType.none;

/**
 * none meta message manager
 */
@Component
@ConditionalOnProperty(prefix = MESSAGE_CONFIG_PREFIX, name = "type", havingValue = "none")
public class NoneMessageManager implements IMessageManager {

    @Override
    public MessageType getType() {
        return none;
    }

    @Override
    public void init(MessageProperties messageProperties) throws MessageException {

    }

    @Override
    public IMessageProducer getProducer(String name) {
        return null;
    }

    @Override
    public IMessageConsumer getConsumer(String name) {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
