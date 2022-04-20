package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;

import com.fasterxml.jackson.annotation.JsonTypeName;

import static org.metahut.starfish.ingestion.collector.pulsar.Constants.COLLECTOR_TYPE;

@JsonTypeName(value = COLLECTOR_TYPE)
public class PulsarCollectorParameter extends AbstractCollectorParameter {

    @Override
    public boolean checkParameter() {
        return false;
    }
}
