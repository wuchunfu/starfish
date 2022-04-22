package org.metahut.starfish.scheduler.api;

import org.metahut.starfish.scheduler.api.parameters.HttpTaskParameter;
import org.metahut.starfish.scheduler.api.parameters.ScheduleParameter;

import java.util.List;

public interface IScheduler extends AutoCloseable {

    List<String> previewSchedule(ScheduleParameter scheduleParameter);

    Object createSingleHttpTask(HttpTaskParameter httpTaskParameter);

    //查看任务定义（分页）
    Object queryTaskDefinitionPageList();

    //查看任务定义详情
    Object queryTaskDefinitionByCode();

    //查看任务日志（分页）
    Object queryTaskInstanceLogs();

    //查看任务日志（详情）
    Object queryTaskDetailLogs();

    //删除任务定义
    Object deleteTaskDefinitionByCode();

    //修改任务定义
    Object updateTaskDefinition();
    // 生成任务

    // 执行任务
}
