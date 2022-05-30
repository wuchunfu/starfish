package org.metahut.starfish.ingestion.server.service.impl;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollectorTask;
import org.metahut.starfish.ingestion.server.collector.CollectorPluginHelper;
import org.metahut.starfish.ingestion.server.dto.CollectorExecuteRequestDTO;
import org.metahut.starfish.ingestion.server.entity.CollectorTaskEntity;
import org.metahut.starfish.ingestion.server.service.CollectorService;
import org.metahut.starfish.ingestion.server.utils.JSONUtils;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class CollectorServiceImpl implements CollectorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectorServiceImpl.class);

    private final CollectorPluginHelper collectorPluginHelper;
    private final AbstractMetaDataService<Long, Object> metaDataService;

    public CollectorServiceImpl(CollectorPluginHelper collectorPluginHelper, AbstractMetaDataService<Long, Object> metaDataService) {
        this.collectorPluginHelper = collectorPluginHelper;
        this.metaDataService = metaDataService;
    }

    @Override
    public CollectorResult execute(CollectorExecuteRequestDTO requestDTO) {

        // collector_name, description, datasourceId, collector_params, crontab, scheduler_flow_code，scheduler_cron_code???, state

        LOGGER.info("query collector task entity, condition:{}", JSONUtils.toJSONString(requestDTO.toCondition()));
        Optional<CollectorTaskEntity> first = metaDataService.instances(requestDTO.toCondition()).stream().findFirst();

        if (!first.isPresent()) {
            return new CollectorResult(false, MessageFormat.format("collector task entity not exists, id:{0}", requestDTO.getId()));
        }

        CollectorTaskEntity instance = first.get();
        LOGGER.info("query collector task entity, result:{}", JSONUtils.toJSONString(instance));

        ICollectorTask collector = collectorPluginHelper.generateTaskInstance(instance.getAdapter().getType(), instance.getAdapter().getParameter(), instance.getParameter());
        return collector.execute();
    }
}
