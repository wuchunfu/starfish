package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.ingestion.collector.api.CollectorException;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollectorAdapter;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.api.PulsarClientException;

import java.util.Objects;

public class PulsarCollectorAdapter implements ICollectorAdapter {

    private final PulsarAdmin pulsarAdmin;

    public PulsarCollectorAdapter(PulsarCollectorAdapterParameter parameter) {
        try {
            pulsarAdmin = PulsarAdmin.builder()
                    .serviceHttpUrl(parameter.getServerUrl())
                    .build();
        } catch (PulsarClientException e) {
            throw new CollectorException("pulsar admin client create error", e);
        }
    }

    @Override
    public CollectorResult testConnection() {
        return Objects.isNull(pulsarAdmin) ? new CollectorResult(false, "pulsar admin client is null") : new CollectorResult(true);
    }

    @Override
    public PulsarAdmin getMetaClient() {
        return pulsarAdmin;
    }

    @Override
    public void close() throws Exception {
        if (Objects.nonNull(pulsarAdmin)) {
            pulsarAdmin.close();
        }
    }
}
