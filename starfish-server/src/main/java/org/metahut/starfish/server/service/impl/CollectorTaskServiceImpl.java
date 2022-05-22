package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.CollectorTaskCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceLogResponseDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceResponseDTO;
import org.metahut.starfish.api.dto.CollectorTaskRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.scheduler.api.parameters.HttpTaskParameter;
import org.metahut.starfish.scheduler.api.parameters.ScheduleCronParameter;
import org.metahut.starfish.scheduler.api.parameters.ScheduleParameter;
import org.metahut.starfish.scheduler.api.parameters.TaskParameter;
import org.metahut.starfish.scheduler.dolphinscheduler.JSONUtils;
import org.metahut.starfish.server.config.IngestionConfiguration;
import org.metahut.starfish.server.scheduler.SchedulerPluginHelper;
import org.metahut.starfish.server.service.CollectorTaskService;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollectorTaskServiceImpl implements CollectorTaskService {

    private final SchedulerPluginHelper schedulerPluginHelper;
    private final IngestionConfiguration ingestionConfiguration;
    private final ConversionService conversionService;

    private final AbstractMetaDataService metaDataService;

    public CollectorTaskServiceImpl(SchedulerPluginHelper schedulerPluginHelper,
                                    IngestionConfiguration ingestionConfiguration,
                                    ConversionService conversionService,
                                    AbstractMetaDataService metaDataService) {
        this.schedulerPluginHelper = schedulerPluginHelper;
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
        String flowCode = schedulerPluginHelper.getScheduler().createSingleHttpTask(taskParameter);

        // configure a timing expression
        ScheduleParameter scheduleParameter = new ScheduleParameter();
        scheduleParameter.setFlowCode(flowCode);
        ScheduleCronParameter scheduleCronParameter = new ScheduleCronParameter();
        scheduleCronParameter.setCron(requestDTO.getCron());
        scheduleParameter.setScheduleCronParameter(scheduleCronParameter);

        String scheduleCode = schedulerPluginHelper.getScheduler().createSchedule(scheduleParameter);

        // create collector instance
        Object typeId = metaDataService.createEntity("typeId", requestDTO.getName(), null);

        // return conversionService.convert(save, IngestionCollectorResponseDTO.class);
        return null;
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
        String flowCode = "";
        // delete schedule flow instance
        schedulerPluginHelper.getScheduler().deleteFlowByCode(flowCode);

        // delete collector instance
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
