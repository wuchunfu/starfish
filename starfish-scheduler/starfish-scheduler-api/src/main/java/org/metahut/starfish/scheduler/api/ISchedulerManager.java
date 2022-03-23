package org.metahut.starfish.scheduler.api;

public interface ISchedulerManager extends AutoCloseable {

    IScheduler getScheduler();
}
