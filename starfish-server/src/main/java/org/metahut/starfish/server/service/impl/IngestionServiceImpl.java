package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.IngestionCollectorRequestDTO;
import org.metahut.starfish.scheduler.api.parameters.HttpTaskParameter;
import org.metahut.starfish.scheduler.api.parameters.ScheduleCronParameter;
import org.metahut.starfish.scheduler.api.parameters.ScheduleParameter;
import org.metahut.starfish.scheduler.api.parameters.TaskParameter;
import org.metahut.starfish.scheduler.dolphinscheduler.JSONUtils;
import org.metahut.starfish.server.config.IngestionConfiguration;
import org.metahut.starfish.server.scheduler.SchedulerPluginHelper;
import org.metahut.starfish.server.service.IngestionService;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngestionServiceImpl implements IngestionService {

    private final SchedulerPluginHelper schedulerPluginHelper;
    private final IngestionConfiguration ingestionConfiguration;

    public IngestionServiceImpl(SchedulerPluginHelper schedulerPluginHelper, IngestionConfiguration ingestionConfiguration) {
        this.schedulerPluginHelper = schedulerPluginHelper;
        this.ingestionConfiguration = ingestionConfiguration;
    }

    //collector_name, description, datasourceId, collector_params, crontab, scheduler_flow_code，scheduler_cron_code???, state

    @Override
    public String createCollector(IngestionCollectorRequestDTO ingestionCollectorRequestDTO) {

        // create single http task
        TaskParameter taskParameter = new TaskParameter();
        taskParameter.setTaskType("HTTP");
        taskParameter.setName(ingestionCollectorRequestDTO.getName());
        taskParameter.setDescription(ingestionCollectorRequestDTO.getDescription());

        HttpTaskParameter httpTaskParameter = new HttpTaskParameter();
        httpTaskParameter.setMethod("POST");

        // TODO How to deal with the generated scheduler task instance data when the url is changed?
        httpTaskParameter.setUrl(ingestionConfiguration.getCollectorExecuteRest());

        // TODO use java bean
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("datasourceId", ingestionCollectorRequestDTO.getDatasourceId());
        bodyMap.put("parameter", ingestionCollectorRequestDTO.getParameter());
        bodyMap.put("name", ingestionCollectorRequestDTO.getName());
        httpTaskParameter.setBody(JSONUtils.toJSONString(bodyMap));

        taskParameter.setTaskParams(JSONUtils.toJSONString(httpTaskParameter));

        // create a schedule flow
        String flowCode = schedulerPluginHelper.getScheduler().createSingleHttpTask(taskParameter);

        // configure a timing expression
        ScheduleParameter scheduleParameter = new ScheduleParameter();
        scheduleParameter.setFlowCode(flowCode);
        ScheduleCronParameter scheduleCronParameter = new ScheduleCronParameter();
        scheduleCronParameter.setCron(ingestionCollectorRequestDTO.getCron());
        scheduleParameter.setScheduleCronParameter(scheduleCronParameter);

        String scheduleCode = schedulerPluginHelper.getScheduler().createSchedule(scheduleParameter);

        // create collector instance
        return null;
    }

    public void updateCollector(IngestionCollectorRequestDTO ingestionCollectorRequestDTO) {

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
