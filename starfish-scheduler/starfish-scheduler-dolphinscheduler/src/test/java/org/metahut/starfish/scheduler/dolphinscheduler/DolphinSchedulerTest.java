package org.metahut.starfish.scheduler.dolphinscheduler;

import org.metahut.starfish.scheduler.api.PageResponse;
import org.metahut.starfish.scheduler.api.SchedulerProperties;
import org.metahut.starfish.scheduler.api.SchedulerTypeEnum;
import org.metahut.starfish.scheduler.api.entity.FlowDefinition;
import org.metahut.starfish.scheduler.api.entity.FlowInstance;
import org.metahut.starfish.scheduler.api.parameters.HttpTaskParameter;
import org.metahut.starfish.scheduler.api.parameters.PageRequest;
import org.metahut.starfish.scheduler.api.parameters.ScheduleCronParameter;
import org.metahut.starfish.scheduler.api.parameters.TaskParameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Disabled
public class DolphinSchedulerTest {

    private DolphinScheduler scheduler;

    private static final String SERVICE_URL = "http://localhost:8080/dolphinscheduler";
    private static final String TOKEN = "c6258d4a509df0f1b89f77fb552d8ddf";
    private static final String PROJECT_CODE = "4996418468000";

    public static final String TASK_NAME = "hive-collector-task";

    @BeforeEach
    public void before() {
        SchedulerProperties schedulerProperties = new SchedulerProperties();
        schedulerProperties.setType(SchedulerTypeEnum.dolphinscheduler);

        SchedulerProperties.DolphinScheduler dolphinSchedulerConfig = new SchedulerProperties.DolphinScheduler();
        dolphinSchedulerConfig.setServiceUrl(SERVICE_URL);
        dolphinSchedulerConfig.setToken(TOKEN);
        dolphinSchedulerConfig.setProjectCode(PROJECT_CODE);
        schedulerProperties.setDolphinScheduler(dolphinSchedulerConfig);

        scheduler = new DolphinSchedulerManager(schedulerProperties).getScheduler();

    }

    @Test
    public void testQueryProjectCreatedAndAuthorizedByUser() throws IOException {
        String url = "/projects/created-and-authed";
        String json = scheduler.get(url);
        DolphinResult result = JSONUtils.parseObject(json, DolphinResult.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.getCode());
    }

    @Test
    public void testPreviewSchedule() {
        ScheduleCronParameter parameter = new ScheduleCronParameter();
        parameter.setCron("0 30 * * * ?");
        parameter.setStartTime(new Date());
        parameter.setEndTime(Date.from(LocalDateTime.now().plusDays(2).atZone(ZoneId.systemDefault()).toInstant()));
        List<String> strings = scheduler.previewSchedule(parameter);
        Assertions.assertNotNull(strings);
    }

    @Test
    public void testCreateSingleHttpTask() {
        TaskParameter taskParameter = new TaskParameter();

        taskParameter.setName(TASK_NAME);
        taskParameter.setDescription("http test");
        taskParameter.setTaskType("HTTP");
        HttpTaskParameter httpTaskParameter = new HttpTaskParameter();

        httpTaskParameter.setUrl(SERVICE_URL);
        httpTaskParameter.setMethod("POST");
        // http body
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("type", "Hive");
        bodyMap.put("datasourceId", "1");
        httpTaskParameter.setBody(JSONUtils.toJSONString(bodyMap));

        taskParameter.setTaskParams(JSONUtils.toJSONString(httpTaskParameter));

        String flowCode = scheduler.createSingleHttpTask(taskParameter);

        Assertions.assertNotNull(flowCode);
    }

    @Test
    public void testQueryFlowByCode() {
        FlowDefinition flowDefinition = scheduler.queryFlowByCode("5657947648672");
        Assertions.assertEquals(flowDefinition.getName(), TASK_NAME);
    }

    @Test
    public void testQueryFlowInstanceListPage() {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNo(1);
        pageRequest.setPageSize(10);

        PageResponse<FlowInstance> pageResponse = scheduler.queryFlowInstanceListPage(pageRequest);

        Assertions.assertDoesNotThrow(() -> scheduler.queryFlowInstanceListPage(pageRequest));
    }

}
