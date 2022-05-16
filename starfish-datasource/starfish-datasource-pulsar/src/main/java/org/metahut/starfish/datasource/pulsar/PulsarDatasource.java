package org.metahut.starfish.datasource.pulsar;

import org.metahut.starfish.datasource.api.DatasourceException;
import org.metahut.starfish.datasource.api.DatasourceResult;
import org.metahut.starfish.datasource.api.IDatasource;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.api.PulsarClientException;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PulsarDatasource implements IDatasource {

    private final PulsarDatasourceParameter parameter;
    private PulsarAdmin pulsarAdmin;

    public PulsarDatasource(PulsarDatasourceParameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public DatasourceResult testConnection() {
        try {
            PulsarAdmin.builder()
                    .serviceHttpUrl(parameter.getServerUrl())
                    .build();
        } catch (PulsarClientException e) {
            return new DatasourceResult(false, e.toString());
        }
        if (Objects.isNull(pulsarAdmin)) {
            return new DatasourceResult(false, "connect is fail!");
        }
        return new DatasourceResult(true, "connect is success!");

    }

    @Override
    public PulsarAdmin getMetaClient() {
        try {
            pulsarAdmin = PulsarAdmin.builder()
                    .serviceHttpUrl(parameter.getServerUrl())
                    .build();
            return pulsarAdmin;
        } catch (PulsarClientException e) {
            throw new DatasourceException("pulsar admin client create error", e);
        }
    }

    @Override
    public void close() throws Exception {
        if (Objects.nonNull(pulsarAdmin)) {
            pulsarAdmin.close();
        }
    }
}
