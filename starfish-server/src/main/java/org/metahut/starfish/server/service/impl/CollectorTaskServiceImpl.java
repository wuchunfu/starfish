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

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.metahut.starfish.server.utils.Constants.COLLECTOR_TASK_TYPE_NAME;

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
        bodyMap.put("adapterId", requestDTO.getAdapterId());
        bodyMap.put("parameter", requestDTO.getParameter());
        bodyMap.put("name", requestDTO.getName());
        httpTaskParameter.setBody(JSONUtils.toJSONString(bodyMap));

        taskParameter.setTaskParams(JSONUtils.toJSONString(httpTaskParameter));

        // create a schedule flow
        String flowCode = scheduler.createSingleHttpTask(taskParameter);

        // configure a timing expression
        ScheduleParameter scheduleParameter = new ScheduleParameter();
        scheduleParameter.setFlowCode(flowCode);
        ScheduleCronParameter scheduleCronParameter = new ScheduleCronParameter();
        scheduleCronParameter.setCron(requestDTO.getCron());
        scheduleParameter.setScheduleCronParameter(scheduleCronParameter);

        String scheduleCode = scheduler.createSchedule(scheduleParameter);

        // create collector instance
        Map<String, Object> convert = conversionService.convert(requestDTO, Map.class);
        Long entityId = metaDataService.createEntityByTypeName(COLLECTOR_TASK_TYPE_NAME, requestDTO.getName(), convert);

        CollectorTaskResponseDTO collectorTaskResponseDTO = new CollectorTaskResponseDTO();
        collectorTaskResponseDTO.setId(entityId);
        return collectorTaskResponseDTO;
    }

    @Override
    public CollectorTaskResponseDTO update(Long id, CollectorTaskCreateOrUpdateRequestDTO requestDTO) {
        // query collector instance by code

        // compare before and after update

        // determine if the schedule flow instance needs to be updated

        // determine if the schedule cron instance needs to be updated

        // update collector instance
        return null;
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
        return null;
    }

    @Override
    public PageResponseDTO<CollectorTaskResponseDTO> queryListPage(CollectorTaskRequestDTO requestDTO) {
        return null;
    }

    @Override
    public List<CollectorTaskResponseDTO> queryList(CollectorTaskRequestDTO requestDTO) {
        return null;
    }

    @Override
    public CollectorTaskInstanceResponseDTO queryInstanceListPage(CollectorTaskInstanceRequestDTO requestDTO) {
        return null;
    }

    @Override
    public CollectorTaskInstanceLogResponseDTO queryInstanceLog(String instanceId) {
        return null;
    }
}
