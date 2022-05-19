package org.metahut.starfish.ingestion.server.service.impl;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollectorTask;
import org.metahut.starfish.ingestion.server.collector.CollectorPluginHelper;
import org.metahut.starfish.ingestion.server.dto.CollectorExecuteRequestDTO;
import org.metahut.starfish.ingestion.server.service.CollectorAdapterService;
import org.metahut.starfish.ingestion.server.service.CollectorTaskService;

import org.springframework.stereotype.Service;

@Service
public class CollectorTaskServiceImpl implements CollectorTaskService {

    private final CollectorPluginHelper collectorPluginHelper;
    private final CollectorAdapterService collectorAdapterService;

    public CollectorTaskServiceImpl(CollectorPluginHelper collectorPluginHelper, CollectorAdapterService collectorAdapterService) {
        this.collectorPluginHelper = collectorPluginHelper;
        this.collectorAdapterService = collectorAdapterService;
    }

    @Override
    public CollectorResult execute(CollectorExecuteRequestDTO requestDTO) {

        // collector_name, description, datasourceId, collector_params, crontab, scheduler_flow_code，scheduler_cron_code???, state

        // request: adapterId, {"xxx":"xxx"}

        // TODO query datasource parameter by datasource id
        String adapterParameter = null;

        ICollectorTask collector = collectorPluginHelper.generateTaskInstance("parameter", adapterParameter, requestDTO.getParameter());
        return collector.execute();
    }
}
