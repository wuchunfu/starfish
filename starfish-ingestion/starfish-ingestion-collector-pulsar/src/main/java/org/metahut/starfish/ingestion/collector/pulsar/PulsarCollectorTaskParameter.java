package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;

public class PulsarCollectorTaskParameter extends AbstractCollectorParameter {

    @Override
    public boolean checkParameter() {
        return true;
    }

}
