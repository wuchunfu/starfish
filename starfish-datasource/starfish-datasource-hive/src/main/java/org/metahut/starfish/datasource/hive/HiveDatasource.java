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

package org.metahut.starfish.datasource.hive;

import org.metahut.starfish.datasource.api.DatasourceException;
import org.metahut.starfish.datasource.api.DatasourceResult;
import org.metahut.starfish.datasource.api.IDatasource;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.RetryingMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.MetaException;

import java.sql.Connection;
import java.util.Objects;

public class HiveDatasource implements IDatasource {

    private final HiveDatasourceParameter parameter;

    private IMetaStoreClient metaClient;

    private static Connection conn;

    public HiveDatasource(HiveDatasourceParameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public DatasourceResult testConnection() {

        Configuration conf = new Configuration();
        conf.set("hive.metastore.uris", parameter.getJdbcUrl());
        try {
            metaClient = RetryingMetaStoreClient.getProxy(conf, false);
        } catch (MetaException e) {
            return new DatasourceResult(false, e.toString());
        }
        if (Objects.isNull(metaClient)) {
            return new DatasourceResult(false, "connect is error!");
        }
        return new DatasourceResult(true, "connect is success!");
    }

    @Override
    public IMetaStoreClient getMetaClient() {
        Configuration conf = new Configuration();
        conf.set("hive.metastore.uris", parameter.getJdbcUrl());
        try {
            metaClient = RetryingMetaStoreClient.getProxy(conf, false);
            return metaClient;
        } catch (MetaException e) {
            throw new DatasourceException("create hive datasource meta client exception", e);
        }
    }

    @Override
    public void close() throws Exception {
        if (Objects.nonNull(metaClient)) {
            metaClient.close();
        }

    }
}
