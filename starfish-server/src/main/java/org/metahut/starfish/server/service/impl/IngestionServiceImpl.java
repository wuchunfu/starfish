package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.IngestionCollectorCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.IngestionCollectorResponseDTO;
import org.metahut.starfish.api.dto.NodePropertiesRequestDTO;
import org.metahut.starfish.scheduler.api.parameters.HttpTaskParameter;
import org.metahut.starfish.scheduler.api.parameters.ScheduleCronParameter;
import org.metahut.starfish.scheduler.api.parameters.ScheduleParameter;
import org.metahut.starfish.scheduler.api.parameters.TaskParameter;
import org.metahut.starfish.scheduler.dolphinscheduler.JSONUtils;
import org.metahut.starfish.server.config.IngestionConfiguration;
import org.metahut.starfish.server.scheduler.SchedulerPluginHelper;
import org.metahut.starfish.server.service.IngestionService;

import org.metahut.starfish.service.AbstractMetaDataService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngestionServiceImpl implements IngestionService {

    private final SchedulerPluginHelper schedulerPluginHelper;
    private final IngestionConfiguration ingestionConfiguration;
    private final ConversionService conversionService;

    private final AbstractMetaDataService metaDataService;

    public IngestionServiceImpl(SchedulerPluginHelper schedulerPluginHelper, IngestionConfiguration ingestionConfiguration, ConversionService conversionService, AbstractMetaDataService metaDataService) {
        this.schedulerPluginHelper = schedulerPluginHelper;
        this.ingestionConfiguration = ingestionConfiguration;
        this.conversionService = conversionService;
        this.metaDataService = metaDataService;
    }

//collector_name, description, datasourceId, collector_params, crontab, scheduler_flow_code，scheduler_cron_code???, state

    public void testConnection(String type, String parameter) {

    }
    @Override
    public IngestionCollectorResponseDTO createCollector(IngestionCollectorCreateOrUpdateRequestDTO requestDTO) {

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
        bodyMap.put("datasourceId", requestDTO.getDatasourceId());
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
        NodePropertiesRequestDTO convert = conversionService.convert(requestDTO, NodePropertiesRequestDTO.class);
        Object typeId = metaDataService.createEntity("typeId", requestDTO.getName(), convert.getProperties());


        // return conversionService.convert(save, IngestionCollectorResponseDTO.class);
        return null;
    }

    public void updateCollector(IngestionCollectorCreateOrUpdateRequestDTO ingestionCollectorCreateOrUpdateRequestDTO) {

        // query collector instance by code

        // compare before and after update

        // determine if the schedule flow instance needs to be updated

        // determine if the schedule cron instance needs to be updated

        // update collector instance

    }

    public void deleteCollector() {

        String flowCode = "";
        // delete schedule flow instance
        schedulerPluginHelper.getScheduler().deleteFlowByCode(flowCode);

        // delete collector instance

    }
}
