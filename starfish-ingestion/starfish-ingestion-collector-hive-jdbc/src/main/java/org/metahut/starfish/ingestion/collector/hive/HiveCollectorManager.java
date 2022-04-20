package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.ICollectorManager;

import static org.metahut.starfish.ingestion.collector.hive.Constants.COLLECTOR_TYPE;

public class HiveCollectorManager implements ICollectorManager {

    @Override
    public String getType() {
        return COLLECTOR_TYPE;
    }

    @Override
    public ICollector generateInstance(AbstractCollectorParameter parameter) {
        return new HiveCollector((HiveCollectorParameter) parameter);
    }
}
