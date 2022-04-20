package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.ICollectorManager;

import static org.metahut.starfish.ingestion.collector.pulsar.Constants.COLLECTOR_TYPE;

public class PulsarCollectorManager implements ICollectorManager {

    @Override
    public String getType() {
        return COLLECTOR_TYPE;
    }

    @Override
    public ICollector generateInstance(AbstractCollectorParameter parameter) {
        return new PulsarCollector((PulsarCollectorParameter) parameter);
    }

}
