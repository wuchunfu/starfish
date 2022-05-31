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

package org.metahut.starfish.ingestion.collector.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringJoiner;

public abstract class AbstractCollectorTask implements ICollectorTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCollectorTask.class);

    private final StringJoiner messageJoiner = new StringJoiner("\n");

    protected void println(String message) {
        LOGGER.info(message);
        messageJoiner.add(message);
    }

    protected void isThrowException(String message, Throwable throwable, boolean isThrowException) {
        LOGGER.error(message, throwable);
        messageJoiner.add(message);
        if (isThrowException) {
            throw new CollectorException(message, throwable);
        }
    }

    protected String getMessage() {
        return messageJoiner.toString();
    }
}
