package org.metahut.starfish.ingestion.server.collector;

import org.metahut.starfish.ingestion.collector.api.ICollectorManager;
import org.metahut.starfish.ingestion.collector.api.ICollectorTask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Disabled
class CollectorPluginHelperTest  {

    @Autowired
    private CollectorPluginHelper collectorPluginHelper;

    @Test
    void getCollector() {
        ICollectorManager hiveCollector = collectorPluginHelper.getCollector("Hive");
        Assertions.assertNotNull(hiveCollector);
    }

    @Test
    void generateTaskInstance() {
        ICollectorTask collector = collectorPluginHelper.generateTaskInstance("parameter", "", "");
        Assertions.assertNotNull(collector);
    }
}