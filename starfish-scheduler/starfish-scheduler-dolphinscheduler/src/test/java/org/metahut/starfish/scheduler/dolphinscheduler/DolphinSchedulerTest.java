package org.metahut.starfish.scheduler.dolphinscheduler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.metahut.starfish.scheduler.api.SchedulerProperties;
import org.metahut.starfish.scheduler.api.SchedulerType;

import java.io.IOException;

@Disabled
public class DolphinSchedulerTest {

    private DolphinScheduler scheduler;

    private String serviceUrl = "http://dolphinscheduler.dev.zhaopin.com/dolphinscheduler";

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
}
