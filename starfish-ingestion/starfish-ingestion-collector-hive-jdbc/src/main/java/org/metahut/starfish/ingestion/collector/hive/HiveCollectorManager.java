package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.ICollectorManager;

public class HiveCollectorManager implements ICollectorManager {

    @Override
    public String getType() {
        return "Hive";
    }

    @Override
    public ICollector generateInstance(AbstractCollectorParameter parameter) {
        return new HiveCollector((HiveCollectorParameter) parameter);
    }
}
