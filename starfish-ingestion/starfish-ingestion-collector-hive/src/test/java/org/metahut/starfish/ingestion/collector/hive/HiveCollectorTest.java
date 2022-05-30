package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class HiveCollectorTest {

    @Test
    public void testHiveCollectorTask() {
        CollectorResult result = new HiveCollectorManager()
                .generateTaskInstance("{\"hiveMetastoreUris\":\"thrift://172.21.100.219:9083,thrift://172.21.100.231:9083\"}", "{\"clusterName\":\"hive\"}")
                .execute();
        Assertions.assertEquals(true, result.getState());
    }
}
