package org.metahut.starfish.scheduler.dolphinscheduler;

import org.metahut.starfish.scheduler.api.SchedulerManager;
import org.metahut.starfish.scheduler.api.SchedulerProperties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ConditionalOnProperty(prefix = "starfish.scheduler", name = "type", havingValue = "DOLPHINSCHEDULER")
public class DolphinSchedulerManager implements SchedulerManager {

    private final SchedulerProperties.DolphinScheduler dolphinSchedulerProperties;

    private final DolphinScheduler dolphinScheduler;

    public DolphinSchedulerManager(SchedulerProperties.DolphinScheduler dolphinSchedulerProperties) {
        this.dolphinSchedulerProperties = dolphinSchedulerProperties;

        dolphinScheduler = null;
    }

    @Override
    public DolphinScheduler getScheduler() {
        return dolphinScheduler;
    }

    @Override
    public void close() throws IOException {

    }
}
