package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.ICollectorManager;

public class HiveCollectorManager implements ICollectorManager {
    @Override
    public String getType() {
        return "hive-jdbc";
    }

    @Override
    public ICollector generateInstance(String parameter) {
        return null;
    }
}
