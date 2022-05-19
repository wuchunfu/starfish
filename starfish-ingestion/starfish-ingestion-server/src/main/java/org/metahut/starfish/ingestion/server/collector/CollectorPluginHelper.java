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

package org.metahut.starfish.ingestion.server.collector;

import org.metahut.starfish.ingestion.collector.api.ICollectorTask;
import org.metahut.starfish.ingestion.collector.api.ICollectorManager;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;

@Component
public class CollectorPluginHelper {

    private static final Map<String, ICollectorManager> COLLECTOR_MANAGER_MAP = new HashMap<>();

    @PostConstruct
    public void init() {
        ServiceLoader.load(ICollectorManager.class).forEach(manager -> {

            String type = manager.getType();

            ICollectorManager collectorManager = COLLECTOR_MANAGER_MAP.get(type);

            if (Objects.nonNull(collectorManager)) {
                throw new IllegalArgumentException(MessageFormat.format("Duplicate ingestion collector type exists: {0}", type));
            }

            COLLECTOR_MANAGER_MAP.put(type, manager);

        });
    }

    public ICollectorManager getCollector(String type) {
        return COLLECTOR_MANAGER_MAP.get(type);
    }

    public ICollectorTask generateTaskInstance(String type, String adapterParameter, String parameter) {
        return getCollector(type).generateTaskInstance(adapterParameter, parameter);
    }
}
