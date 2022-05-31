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
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollectorAdapter;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.api.PulsarClientException;

import java.util.Objects;

public class PulsarCollectorAdapter implements ICollectorAdapter {

    private final PulsarAdmin pulsarAdmin;

    public PulsarCollectorAdapter(PulsarCollectorAdapterParameter parameter) {
        try {
            pulsarAdmin = PulsarAdmin.builder()
                    .serviceHttpUrl(parameter.getServerUrl())
                    .build();
        } catch (PulsarClientException e) {
            throw new CollectorException("pulsar admin client create error", e);
        }
    }

    @Override
    public CollectorResult testConnection() {
        return Objects.isNull(pulsarAdmin) ? new CollectorResult(false, "pulsar admin client is null") : new CollectorResult(true);
    }

    @Override
    public PulsarAdmin getMetaClient() {
        return pulsarAdmin;
    }

    @Override
    public void close() throws Exception {
        if (Objects.nonNull(pulsarAdmin)) {
            pulsarAdmin.close();
        }
    }
}
