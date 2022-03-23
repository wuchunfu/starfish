package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.ICollectorManager;
import org.metahut.starfish.ingestion.collector.api.IngestionException;
import org.metahut.starfish.ingestion.utils.JSONUtils;

import java.util.Objects;

public class HiveCollectorManager implements ICollectorManager {

    @Override
    public String getType() {
        return "hive-jdbc";
    }

    @Override
    public ICollector generateInstance(String parameter) {
        HiveParameter param = JSONUtils.parseObject(parameter, HiveParameter.class);
        if (Objects.isNull(param) || !param.checkParam()) {
            throw new IngestionException("hive paramter is error");
        }
        return new HiveCollector(param);
    }
}
