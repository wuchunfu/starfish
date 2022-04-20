package org.metahut.starfish.server.scheduler;

import org.metahut.starfish.scheduler.api.IScheduler;
import org.metahut.starfish.scheduler.api.ISchedulerManager;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class SchedulerPluginHelper {

    private final ISchedulerManager schedulerManager;

    public SchedulerPluginHelper(ISchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    public IScheduler getScheduler() {
        return schedulerManager.getScheduler();
    }

    @PreDestroy
    public void close() throws Exception {
        schedulerManager.close();
    }

}
