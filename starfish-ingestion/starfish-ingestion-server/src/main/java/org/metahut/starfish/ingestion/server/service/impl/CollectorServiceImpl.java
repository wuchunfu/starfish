package org.metahut.starfish.ingestion.server.service.impl;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollectorTask;
import org.metahut.starfish.ingestion.server.collector.CollectorPluginHelper;
import org.metahut.starfish.ingestion.server.dto.CollectorExecuteRequestDTO;
import org.metahut.starfish.ingestion.server.entity.CollectorTaskEntity;
import org.metahut.starfish.ingestion.server.service.CollectorService;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CollectorServiceImpl implements CollectorService {

    private final CollectorPluginHelper collectorPluginHelper;
    private final AbstractMetaDataService<Long, Object> metaDataService;

    public CollectorServiceImpl(CollectorPluginHelper collectorPluginHelper, AbstractMetaDataService<Long, Object> metaDataService) {
        this.collectorPluginHelper = collectorPluginHelper;
        this.metaDataService = metaDataService;
    }

    @Override
    public CollectorResult execute(CollectorExecuteRequestDTO requestDTO) {

        // collector_name, description, datasourceId, collector_params, crontab, scheduler_flow_code，scheduler_cron_code???, state

        Optional<CollectorTaskEntity> first = metaDataService.instances(requestDTO.toCondition()).stream().findFirst();

        if (first.isPresent()) {
            CollectorTaskEntity instance = first.get();
            ICollectorTask collector = collectorPluginHelper.generateTaskInstance(instance.getAdapter().getType(), instance.getAdapter().getParameter(), instance.getParameter());
            return collector.execute();
        }
        throw new RuntimeException();
    }
}
