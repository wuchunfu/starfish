package org.metahut.starfish.scheduler.api;

import java.io.Closeable;

public interface SchedulerManager extends Closeable {
    Scheduler getScheduler();
}
