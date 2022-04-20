package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;

public class HiveCollectorParameter extends AbstractCollectorParameter {

    @Override
    public boolean checkParameter() {
        return true;
    }
}
