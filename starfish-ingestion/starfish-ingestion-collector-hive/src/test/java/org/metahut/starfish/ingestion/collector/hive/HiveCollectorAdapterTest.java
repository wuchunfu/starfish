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

import org.metahut.starfish.ingestion.collector.api.CollectorResult;

import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.thrift.TException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

@Disabled
public class HiveCollectorAdapterTest {

    @Test
    public void testConnection() throws TException {
        String metaUris = "thrift://xx.xx.xx.xx:9083,thrift://xx.xx.xx.xx:9083";
        HiveCollectorAdapterParameter adapterParameter = new HiveCollectorAdapterParameter();
        adapterParameter.setMetastoreUris(metaUris);
        HiveCollectorAdapter adapter = new HiveCollectorAdapter(adapterParameter);
        CollectorResult collectorResult = adapter.testConnection();
        Assertions.assertNotNull(collectorResult);
        Assertions.assertTrue(collectorResult.getState());
        IMetaStoreClient metaClient = adapter.getMetaClient();
        List<String> allDatabases = metaClient.getAllDatabases();
        Assertions.assertNotNull(allDatabases);

    }
}
