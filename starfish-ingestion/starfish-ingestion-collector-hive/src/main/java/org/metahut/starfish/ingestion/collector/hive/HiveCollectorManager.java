package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.CollectorException;
import org.metahut.starfish.ingestion.collector.api.ICollectorManager;
import org.metahut.starfish.ingestion.common.JSONUtils;

import java.util.Objects;

import static org.metahut.starfish.ingestion.collector.hive.Constants.COLLECTOR_TYPE;

public class HiveCollectorManager implements ICollectorManager {

    public static final String TYPE = "Hive";
    @Override
    public String getType() {
        return COLLECTOR_TYPE;
    }

    @Override
    public HiveCollectorTask generateTaskInstance(String adapterParameter, String parameter) {
        HiveCollectorTaskParameter taskParameter = JSONUtils.parseObject(parameter, HiveCollectorTaskParameter.class);
        HiveCollectorAdapter adapter = generateAdapterInstance(adapterParameter);
        return new HiveCollectorTask(adapter, taskParameter);
    }

    @Override
    public HiveCollectorAdapterParameter deserializeAdapterParameter(String parameter) {
        HiveCollectorAdapterParameter adapterParameter = JSONUtils.parseObject(parameter, HiveCollectorAdapterParameter.class);
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
    public HiveCollectorAdapter generateAdapterInstance(String parameter) {
        HiveCollectorAdapterParameter adapterParameter = deserializeAdapterParameter(parameter);
        return new HiveCollectorAdapter(adapterParameter);
    }

}
