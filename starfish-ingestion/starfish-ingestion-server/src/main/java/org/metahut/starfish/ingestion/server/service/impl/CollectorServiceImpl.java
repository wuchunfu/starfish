package org.metahut.starfish.ingestion.server.service.impl;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.IngestionException;
import org.metahut.starfish.ingestion.collector.api.JSONUtils;
import org.metahut.starfish.ingestion.server.collector.CollectorPluginHelper;
import org.metahut.starfish.ingestion.server.dto.CollectorExecuteRequestDTO;
import org.metahut.starfish.ingestion.server.service.CollectorService;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CollectorServiceImpl implements CollectorService {

    private CollectorPluginHelper collectorPluginHelper;

    public CollectorServiceImpl(CollectorPluginHelper collectorHelper) {
        this.collectorPluginHelper = collectorHelper;
    }

    @Override
    public CollectorResult execute(CollectorExecuteRequestDTO collectorExecuteRequestDTO) {

        // collector_name, description, datasourceId, collector_params, crontab, scheduler_flow_code，scheduler_cron_code???, state

        // request: datasourceId, {"type":"Hive", "xxx":"xxx"}
        AbstractCollectorParameter parameter = JSONUtils.parseObject(collectorExecuteRequestDTO.getParameter(), AbstractCollectorParameter.class);
        if (Objects.isNull(parameter) || !parameter.checkParameter()) {
            throw new IngestionException("collector parameter check exception");
        }

        // TODO query datasource parameter by datasource id
        String datasourceParameter = null;

        parameter.setDatasourceParameter(datasourceParameter);

        ICollector collector = collectorPluginHelper.generateInstance(parameter);
        return collector.execute();
    }
}
