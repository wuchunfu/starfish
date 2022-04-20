package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.ICollectorManager;

public class PulsarCollectorManager implements ICollectorManager {

    @Override
    public String getType() {
        return "Pulsar";
    }

    @Override
    public ICollector generateInstance(AbstractCollectorParameter parameter) {
        return new PulsarCollector((PulsarCollectorParameter) parameter);
    }

}
