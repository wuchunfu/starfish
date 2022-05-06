package org.metahut.starfish.datasource.pulsar;

import org.metahut.starfish.datasource.api.DatasourceException;
import org.metahut.starfish.datasource.api.IDatasourceManager;
import org.metahut.starfish.datasource.common.JSONUtils;

import java.util.Objects;

public class PulsarDatasourceManager implements IDatasourceManager {
    @Override
    public String getType() {
        return "Pulsar";
    }

    @Override
    public PulsarDatasourceParameter getDefaultParameter() {
        return null;
    }

    @Override
    public PulsarDatasource generateInstance(String parameter) {
        PulsarDatasourceParameter pulsarDatasourceParameter = JSONUtils.parseObject(parameter, PulsarDatasourceParameter.class);
        if (Objects.isNull(pulsarDatasourceParameter) || !pulsarDatasourceParameter.checkParameter()) {
            throw new DatasourceException("pulsar datasource parameter check exception");
        }
        return new PulsarDatasource(pulsarDatasourceParameter);
    }
}
