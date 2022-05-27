package org.metahut.starfish.ingestion.server.service.impl;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollectorTask;
import org.metahut.starfish.ingestion.server.collector.CollectorPluginHelper;
import org.metahut.starfish.ingestion.server.dto.CollectorExecuteRequestDTO;
import org.metahut.starfish.ingestion.server.entity.CollectorTaskEntity;
import org.metahut.starfish.ingestion.server.service.CollectorTaskService;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.springframework.stereotype.Service;

@Service
public class CollectorTaskServiceImpl implements CollectorTaskService {

    private final CollectorPluginHelper collectorPluginHelper;
    private final AbstractMetaDataService<Long, Object> metaDataService;

    public CollectorTaskServiceImpl(CollectorPluginHelper collectorPluginHelper, AbstractMetaDataService<Long, Object> metaDataService) {
        this.collectorPluginHelper = collectorPluginHelper;
        this.metaDataService = metaDataService;
    }

    @Override
    public CollectorResult execute(CollectorExecuteRequestDTO requestDTO) {

        // collector_name, description, datasourceId, collector_params, crontab, scheduler_flow_code，scheduler_cron_code???, state

        CollectorTaskEntity instance = metaDataService.instance(requestDTO.getId(), CollectorTaskEntity.class);

        ICollectorTask collector = collectorPluginHelper.generateTaskInstance(instance.getAdapter().getType(), instance.getAdapter().getParameter(), instance.getParameter());
        return collector.execute();
    }
}
