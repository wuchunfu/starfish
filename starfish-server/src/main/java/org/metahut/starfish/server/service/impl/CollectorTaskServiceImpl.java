package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.CollectorTaskCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceLogResponseDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceResponseDTO;
import org.metahut.starfish.api.dto.CollectorTaskRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.scheduler.api.IScheduler;
import org.metahut.starfish.scheduler.api.parameters.HttpTaskParameter;
import org.metahut.starfish.scheduler.api.parameters.ScheduleCronParameter;
import org.metahut.starfish.scheduler.api.parameters.ScheduleParameter;
import org.metahut.starfish.scheduler.api.parameters.TaskParameter;
import org.metahut.starfish.scheduler.dolphinscheduler.JSONUtils;
import org.metahut.starfish.server.config.IngestionConfiguration;
import org.metahut.starfish.server.service.CollectorTaskService;
import org.metahut.starfish.service.AbstractMetaDataService;
import org.metahut.starfish.unit.EntityNameGentrator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.metahut.starfish.api.Constants.COLLECTOR_TASK_TYPE_NAME;
import static org.metahut.starfish.api.Constants.RELATION_PROPERTY_COLLECTOR_TASK_ADAPTER;

@Service
public class CollectorTaskServiceImpl implements CollectorTaskService {

    private final IScheduler scheduler;
    private final IngestionConfiguration ingestionConfiguration;
    private final ConversionService conversionService;

    private final AbstractMetaDataService<Long, Object> metaDataService;

    public CollectorTaskServiceImpl(IScheduler scheduler,
                                    IngestionConfiguration ingestionConfiguration,
                                    ConversionService conversionService,
                                    AbstractMetaDataService<Long, Object> metaDataService) {
        this.scheduler = scheduler;
        this.ingestionConfiguration = ingestionConfiguration;
        this.conversionService = conversionService;
        this.metaDataService = metaDataService;
    }

    //collector_name, description, datasourceId, collector_params, crontab, scheduler_flow_code，scheduler_cron_code???, state

    @Override
    public CollectorTaskResponseDTO create(CollectorTaskCreateOrUpdateRequestDTO requestDTO) {

        String qualifiedName = EntityNameGentrator.generateName(COLLECTOR_TASK_TYPE_NAME, requestDTO.getName());
        // create collector instance
        Map<String, Object> convert = conversionService.convert(requestDTO, Map.class);
        Long entityId = metaDataService.createEntityByTypeName(COLLECTOR_TASK_TYPE_NAME, qualifiedName, convert);

        String schedulerFlowCode = requestDTO.getSchedulerFlowCode();
        if (StringUtils.isBlank(schedulerFlowCode)) {
            // create single http task
            TaskParameter taskParameter = new TaskParameter();
            taskParameter.setTaskType("HTTP");
            taskParameter.setName(requestDTO.getName());
            taskParameter.setDescription(requestDTO.getDescription());

            HttpTaskParameter httpTaskParameter = new HttpTaskParameter();
            httpTaskParameter.setMethod("POST");

            // TODO How to deal with the generated scheduler task instance data when the url is changed?
            httpTaskParameter.setUrl(ingestionConfiguration.getCollectorExecuteRest());

            // TODO use java bean
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("id", entityId);
            bodyMap.put("qualifiedName", qualifiedName);
            httpTaskParameter.setBody(JSONUtils.toJSONString(bodyMap));

            taskParameter.setTaskParams(JSONUtils.toJSONString(httpTaskParameter));

            // create a schedule flow
            String flowCode = scheduler.createSingleHttpTask(taskParameter);

            // configure a timing expression
            ScheduleParameter scheduleParameter = new ScheduleParameter();
            scheduleParameter.setFlowCode(flowCode);
            ScheduleCronParameter scheduleCronParameter = new ScheduleCronParameter();
            scheduleCronParameter.setCrontab(requestDTO.getCron());
            scheduleParameter.setScheduleCronParameter(scheduleCronParameter);

            schedulerFlowCode = scheduler.createSchedule(scheduleParameter);
        }

        //TODO The update method needs to be optimized to support partial update
        convert.put("schedulerFlowCode", schedulerFlowCode);

        // update scheduler flow code in the collector task instance
        metaDataService.updateEntity(entityId, qualifiedName, convert);

        CollectorTaskResponseDTO collectorTaskResponseDTO = new CollectorTaskResponseDTO();
        collectorTaskResponseDTO.setId(entityId);
        return collectorTaskResponseDTO;
    }

    @Override
    public CollectorTaskResponseDTO update(Long id, CollectorTaskCreateOrUpdateRequestDTO requestDTO) {
        // query collector instance by code
        CollectorTaskResponseDTO instance = metaDataService.instance(id, CollectorTaskResponseDTO.class);

        // compare before and after update
        if (!instance.getAdapter().getId().equals(requestDTO.getAdapterId())) {
            // add relation
            metaDataService.link(id, requestDTO.getAdapterId(), RELATION_PROPERTY_COLLECTOR_TASK_ADAPTER);
            // delete relation
            metaDataService.crack(id, instance.getAdapter().getId(), RELATION_PROPERTY_COLLECTOR_TASK_ADAPTER);
        }

        // determine if the schedule cron instance needs to be updated
        if (!instance.getCron().equals(requestDTO.getCron())) {
            ScheduleParameter scheduleParameter = new ScheduleParameter();
            scheduleParameter.setFlowCode(instance.getSchedulerFlowCode());
            ScheduleCronParameter scheduleCronParameter = new ScheduleCronParameter();
            scheduleCronParameter.setCrontab(requestDTO.getCron());
            scheduleParameter.setScheduleCronParameter(scheduleCronParameter);
            scheduler.updateSchedule(scheduleParameter);
        }

        String qualifiedName = EntityNameGentrator.generateName(COLLECTOR_TASK_TYPE_NAME, requestDTO.getName());

        Map<String, Object> convert = conversionService.convert(requestDTO, Map.class);
        // update collector instance
        metaDataService.updateEntity(id, qualifiedName, convert);

        CollectorTaskResponseDTO collectorTaskResponseDTO = new CollectorTaskResponseDTO();
        collectorTaskResponseDTO.setId(id);
        return collectorTaskResponseDTO;
    }

    @Override
    public void deleteById(Long id) {
        CollectorTaskResponseDTO instance = metaDataService.instance(id, CollectorTaskResponseDTO.class);
        // delete schedule flow instance
        scheduler.deleteFlowByCode(instance.getSchedulerFlowCode());

        // delete collector instance
        metaDataService.deleteEntity(id);
    }

    @Override
    public CollectorTaskResponseDTO queryById(Long id) {
        return metaDataService.instance(id, CollectorTaskResponseDTO.class);
    }

    @Override
    public PageResponseDTO<CollectorTaskResponseDTO> queryListPage(CollectorTaskRequestDTO requestDTO) {
        Pageable pageable = PageRequest.of(requestDTO.getPageNo() - 1, requestDTO.getPageSize());
        Page<CollectorTaskResponseDTO> page = metaDataService.instances(requestDTO.toQueryCondition(), pageable);
        return PageResponseDTO.of(page.getNumber(), page.getSize(), page.getTotalElements(), page.getContent());
    }

    @Override
    public Collection<CollectorTaskResponseDTO> queryList(CollectorTaskRequestDTO requestDTO) {
        return metaDataService.instances(requestDTO.toQueryCondition());
    }

    @Override
    public PageResponseDTO<CollectorTaskInstanceResponseDTO> queryInstanceListPage(CollectorTaskInstanceRequestDTO requestDTO) {
        return null;
    }

    @Override
    public CollectorTaskInstanceLogResponseDTO queryInstanceLog(String instanceId) {
        return null;
    }
}
