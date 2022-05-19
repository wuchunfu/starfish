package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.ingestion.collector.api.CollectorException;
import org.metahut.starfish.ingestion.collector.api.ICollectorManager;
import org.metahut.starfish.ingestion.common.JSONUtils;

import java.util.Objects;

public class PulsarCollectorManager implements ICollectorManager {

    @Override
    public String getType() {
        return "Pulsar";
    }

    @Override
    public PulsarCollectorTask generateTaskInstance(String adapterParameter, String parameter) {
        PulsarCollectorTaskParameter taskParameter = JSONUtils.parseObject(parameter, PulsarCollectorTaskParameter.class);
        PulsarCollectorAdapter adapter = generateAdapterInstance(adapterParameter);
        return new PulsarCollectorTask(adapter, taskParameter);
    }

    @Override
    public PulsarCollectorAdapterParameter deserializeAdapterParameter(String parameter) {
        PulsarCollectorAdapterParameter adapterParameter = JSONUtils.parseObject(parameter, PulsarCollectorAdapterParameter.class);
        if (Objects.isNull(adapterParameter)) {
            throw new CollectorException("Invalid adapter parameters to convert");
        }
        boolean checkParameter = adapterParameter.checkParameter();
        if (!checkParameter) {
            throw new CollectorException("The incoming parameter can not be empty");
        }
        return adapterParameter;
    }

    @Override
    public PulsarCollectorAdapter generateAdapterInstance(String parameter) {
        PulsarCollectorAdapterParameter adapterParameter = deserializeAdapterParameter(parameter);
        return new PulsarCollectorAdapter(adapterParameter);
    }

}
