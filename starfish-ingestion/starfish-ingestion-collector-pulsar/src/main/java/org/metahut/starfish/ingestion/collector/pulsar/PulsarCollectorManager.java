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

package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.ingestion.collector.api.CollectorException;
import org.metahut.starfish.ingestion.collector.api.ICollectorManager;
import org.metahut.starfish.ingestion.collector.api.TaskContext;
import org.metahut.starfish.ingestion.common.JSONUtils;

import java.util.Objects;

public class PulsarCollectorManager implements ICollectorManager {

    @Override
    public String getType() {
        return "Pulsar";
    }

    @Override
    public PulsarCollectorTask generateTaskInstance(TaskContext taskContext) {
        PulsarCollectorTaskParameter taskParameter = JSONUtils.parseObject(taskContext.getTaskParameter(), PulsarCollectorTaskParameter.class);
        PulsarCollectorAdapter adapter = generateAdapterInstance(taskContext.getAdapterParameter());
        return new PulsarCollectorTask(adapter, taskParameter, taskContext.getAdapterId());
    }

    @Override
    public PulsarCollectorAdapterParameter deserializeAdapterParameter(String parameter) {
        PulsarCollectorAdapterParameter adapterParameter = JSONUtils.parseObject(parameter, PulsarCollectorAdapterParameter.class);
        if (Objects.isNull(adapterParameter)) {
            throw new CollectorException("Invalid adapter parameters to convert");
        }
        boolean checkParameter = adapterParameter.checkParameter();
        if (!checkParameter) {
            throw new CollectorException("The incoming parameter can not be empty");
        }
        return adapterParameter;
    }

    @Override
    public PulsarCollectorAdapter generateAdapterInstance(String parameter) {
        PulsarCollectorAdapterParameter adapterParameter = deserializeAdapterParameter(parameter);
        return new PulsarCollectorAdapter(adapterParameter);
    }

}
