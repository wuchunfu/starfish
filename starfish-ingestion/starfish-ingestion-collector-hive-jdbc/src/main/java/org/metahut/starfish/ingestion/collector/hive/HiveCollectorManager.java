package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.Collector;
import org.metahut.starfish.ingestion.collector.api.CollectorManager;

public class HiveCollectorManager implements CollectorManager {
    @Override
    public String getType() {
        return "hive-jdbc";
    }

    @Override
    public Collector generateInstance(String parameter) {
        return null;
    }
}
