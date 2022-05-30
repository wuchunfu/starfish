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
        adapterParameter.setHiveMetastoreUris(metaUris);
        HiveCollectorAdapter adapter = new HiveCollectorAdapter(adapterParameter);
        CollectorResult collectorResult = adapter.testConnection();
        Assertions.assertNotNull(collectorResult);
        Assertions.assertTrue(collectorResult.getState());
        IMetaStoreClient metaClient = adapter.getMetaClient();
        List<String> allDatabases = metaClient.getAllDatabases();
        Assertions.assertNotNull(allDatabases);

    }
}
