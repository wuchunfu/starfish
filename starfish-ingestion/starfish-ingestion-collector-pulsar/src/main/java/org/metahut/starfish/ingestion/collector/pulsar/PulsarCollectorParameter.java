package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;

public class PulsarCollectorParameter extends AbstractCollectorParameter {

    @Override
    public boolean checkParameter() {
        return false;
    }
}
