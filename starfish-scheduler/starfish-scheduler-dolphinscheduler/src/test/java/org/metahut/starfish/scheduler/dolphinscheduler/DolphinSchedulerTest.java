package org.metahut.starfish.scheduler.dolphinscheduler;

import org.metahut.starfish.scheduler.api.SchedulerProperties;
import org.metahut.starfish.scheduler.api.SchedulerType;
import org.metahut.starfish.scheduler.api.parameters.HttpTaskParameter;
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

    private static final String SERVICE_URL = "http://dolphinscheduler.dev.zhaopin.com/dolphinscheduler";
    private static final String TOKEN = "c6258d4a509df0f1b89f77fb552d8ddf";
    private static final String PROJECT_CODE = "4996418468000";

    @BeforeEach
    public void before() {
        SchedulerProperties schedulerProperties = new SchedulerProperties();
        schedulerProperties.setType(SchedulerType.dolphinscheduler);

        SchedulerProperties.DolphinScheduler dolphinProperties = new SchedulerProperties.DolphinScheduler();
        dolphinProperties.setServiceUrl(SERVICE_URL);
        dolphinProperties.setToken(TOKEN);
        dolphinProperties.setProjectCode(PROJECT_CODE);
        schedulerProperties.setDolphinScheduler(dolphinProperties);

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

        taskParameter.setName("http-test-1");
        taskParameter.setDescription("http test");
        taskParameter.setTaskType("HTTP");
        HttpTaskParameter httpTaskParameter = new HttpTaskParameter();

        httpTaskParameter.setUrl("http://localhost:8080");
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
}
