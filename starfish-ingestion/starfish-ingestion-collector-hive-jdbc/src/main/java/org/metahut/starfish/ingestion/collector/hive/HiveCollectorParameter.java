package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;

import com.fasterxml.jackson.annotation.JsonTypeName;

import static org.metahut.starfish.ingestion.collector.hive.Constants.COLLECTOR_TYPE;

@JsonTypeName(value = COLLECTOR_TYPE)
public class HiveCollectorParameter extends AbstractCollectorParameter {

    @Override
    public boolean checkParameter() {
        return true;
    }
}
