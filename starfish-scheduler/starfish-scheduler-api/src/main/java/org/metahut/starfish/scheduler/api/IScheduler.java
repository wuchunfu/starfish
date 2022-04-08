package org.metahut.starfish.scheduler.api;

import org.metahut.starfish.scheduler.api.parameters.HttpTaskParameter;

public interface IScheduler extends AutoCloseable {

    void createSingleHttpTask(HttpTaskParameter httpTaskParameter);

    // 生成任务

    // 执行任务
}
