package org.metahut.starfish.scheduler.dolphinscheduler;

import org.metahut.starfish.scheduler.api.SchedulerProperties;
import org.metahut.starfish.scheduler.api.SchedulerType;
import org.metahut.starfish.scheduler.api.parameters.HttpTaskParameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Disabled
public class DolphinSchedulerTest {

    private DolphinScheduler scheduler;

    private String serviceUrl = "http://dolphinscheduler.dev.zhaopin.com/dolphinscheduler";

    private String projectCode = "4996418468000";

    @BeforeEach
    public void before() {

        String token = "c6258d4a509df0f1b89f77fb552d8ddf";

        SchedulerProperties schedulerProperties = new SchedulerProperties();
        schedulerProperties.setType(SchedulerType.dolphinscheduler);

        SchedulerProperties.DolphinScheduler dolphinProperties = new SchedulerProperties.DolphinScheduler();
        dolphinProperties.setServiceUrl(serviceUrl);
        dolphinProperties.setToken(token);
        schedulerProperties.setDolphinScheduler(dolphinProperties);

        scheduler = new DolphinSchedulerManager(schedulerProperties).getScheduler();

    }

    @Test
    public void testQueryProjectCreatedAndAuthorizedByUser() throws IOException {
        String url = serviceUrl + "/projects/created-and-authed";
        String json = scheduler.get(url);
        DolphinResult result = JSONUtils.parseObject(json, DolphinResult.class);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testHttpCreateTask() throws IOException {
        String url = "http://dolphinscheduler.dev.zhaopin.com/dolphinscheduler/projects/4996418468000/task-definition";
        HttpTaskParameter httpParameter = new HttpTaskParameter();
        httpParameter.setUrl(url);
        httpParameter.setMethod("post");
        httpParameter.setBody(
            "[{\"code\":1111,\"name\":\"testhttp\",\"description\":\"\",\"taskType\":\"HTTP\",\"taskParams\":{\"localParams\":[],\"httpParams\":[],\"url\":\"111\",\"httpMethod\":\"GET\",\"httpCheckCondition\":\"STATUS_CODE_DEFAULT\",\"condition\":\"\",\"connectTimeout\":60000,\"socketTimeout\":60000,\"dependence\":{},\"conditionResult\":{\"successNode\":[],\"failedNode\":[]},\"waitStartTimeout\":{},\"switchResult\":{}},\"flag\":\"YES\",\"taskPriority\":\"MEDIUM\",\"workerGroup\":\"default\",\"failRetryTimes\":\"0\",\"failRetryInterval\":\"1\",\"timeoutFlag\":\"CLOSE\",\"timeoutNotifyStrategy\":\"\",\"timeout\":0,\"delayTime\":\"0\",\"environmentCode\":-1},{\"code\":222,\"name\":\"111\",\"description\":\"\",\"taskType\":\"HTTP\",\"taskParams\":{\"localParams\":[],\"httpParams\":[],\"url\":\"122\",\"httpMethod\":\"GET\",\"httpCheckCondition\":\"STATUS_CODE_DEFAULT\",\"condition\":\"\",\"connectTimeout\":60000,\"socketTimeout\":60000,\"dependence\":{},\"conditionResult\":{\"successNode\":[],\"failedNode\":[]},\"waitStartTimeout\":{},\"switchResult\":{}},\"flag\":\"YES\",\"taskPriority\":\"MEDIUM\",\"workerGroup\":\"default\",\"failRetryTimes\":\"0\",\"failRetryInterval\":\"1\",\"timeoutFlag\":\"CLOSE\",\"timeoutNotifyStrategy\":\"\",\"timeout\":0,\"delayTime\":\"0\",\"environmentCode\":-1},{\"code\":333,\"name\":\"333\",\"description\":\"\",\"taskType\":\"HTTP\",\"taskParams\":{\"localParams\":[],\"httpParams\":[],\"url\":\"333\",\"httpMethod\":\"GET\",\"httpCheckCondition\":\"STATUS_CODE_DEFAULT\",\"condition\":\"\",\"connectTimeout\":60000,\"socketTimeout\":60000,\"dependence\":{},\"conditionResult\":{\"successNode\":[],\"failedNode\":[]},\"waitStartTimeout\":{},\"switchResult\":{}},\"flag\":\"YES\",\"taskPriority\":\"MEDIUM\",\"workerGroup\":\"default\",\"failRetryTimes\":\"0\",\"failRetryInterval\":\"1\",\"timeoutFlag\":\"CLOSE\",\"timeoutNotifyStrategy\":\"\",\"timeout\":0,\"delayTime\":\"0\",\"environmentCode\":-1}]");
        scheduler.createSingleHttpTask(httpParameter);
    }

    @Test
    public void testHttpUpdateTask() throws IOException {
         scheduler.updateTaskDefinition();
    }

    @Test
    public void testHttpgetTasks() throws IOException {
        DolphinResult result = (DolphinResult) scheduler.queryTaskDefinitionPageList();
        Assertions.assertNotNull(result);
    }

    @Test
    public void testQueryTaskDefinitionByCode() throws IOException {
        DolphinResult result = (DolphinResult) scheduler.queryTaskDefinitionByCode();
        Assertions.assertNotNull(result);
    }

    @Test
    public void testQueryTaskInstanceLogs() throws IOException {
        DolphinResult result = (DolphinResult) scheduler.queryTaskInstanceLogs();
        Assertions.assertNotNull(result);
    }

    @Test
    public void testQueryTaskDetailLogs() throws IOException {
        DolphinResult result = (DolphinResult) scheduler.queryTaskDetailLogs();
        Assertions.assertNotNull(result);
    }

    @Test
    public void testDeleteTaskDefinitionByCode() throws IOException {
        DolphinResult result = (DolphinResult) scheduler.deleteTaskDefinitionByCode();
        Assertions.assertNotNull(result);
    }
}
