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

package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.CollectorException;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollectorAdapter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.RetryingMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.MetaException;

import java.util.Objects;

public class HiveCollectorAdapter implements ICollectorAdapter {
    private final IMetaStoreClient metaStoreClient;
    private final HiveCollectorAdapterParameter parameter;

    public HiveCollectorAdapter(HiveCollectorAdapterParameter parameter) {
        this.parameter = parameter;
        Configuration conf = new Configuration();
        conf.set("hive.metastore.uris", parameter.getMetastoreUris());
        try {
            metaStoreClient = RetryingMetaStoreClient.getProxy(conf, false);
        } catch (MetaException e) {
            throw new CollectorException("hive create meta store client exception", e);
        }
    }

    @Override
    public CollectorResult testConnection() {
        return Objects.isNull(metaStoreClient) ? new CollectorResult(false, "hive meta store client is null") : new CollectorResult(true);
    }

    @Override
    public IMetaStoreClient getMetaClient() {
        return metaStoreClient;
    }

    @Override
    public HiveCollectorAdapterParameter getParameter() {
        return this.parameter;
    }

    @Override
    public void close() throws Exception {
        if (Objects.nonNull(metaStoreClient)) {
            metaStoreClient.close();
        }
    }
}
