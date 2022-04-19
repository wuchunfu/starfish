package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.ICollectorManager;
import org.metahut.starfish.ingestion.collector.api.IngestionException;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.api.PulsarClient;

import java.util.Objects;

public class PulsarCollectorManager implements ICollectorManager {

    @Override
    public String getType() {
        return "pulsar";
    }

    @Override
    public ICollector generateInstance(String parameter) {
        if (Objects.isNull(parameter)) {
            throw new IngestionException("hive paramter is error");
        }
        return new PulsarCollector(parameter);
    }
}
