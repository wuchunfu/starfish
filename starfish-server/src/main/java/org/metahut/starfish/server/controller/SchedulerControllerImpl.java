package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.SchedulerController;
import org.metahut.starfish.api.dto.HttpTaskParameterDto;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.scheduler.api.parameters.HttpTaskParameter;
import org.metahut.starfish.scheduler.api.parameters.ScheduleParameter;
import org.metahut.starfish.server.scheduler.SchedulerPluginHelper;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchedulerControllerImpl implements SchedulerController {

    private SchedulerPluginHelper schedulerPluginHelper;

    public SchedulerControllerImpl(SchedulerPluginHelper schedulerHelper) {
        this.schedulerPluginHelper = schedulerHelper;
    }

    public ResultEntity prepareSchedule() {
        ScheduleParameter parameter = new ScheduleParameter();
        return ResultEntity.success(schedulerPluginHelper.getScheduler().previewSchedule(parameter));
    }

    @Override
    public ResultEntity createSingleHttpTask(HttpTaskParameterDto httpTaskParameterDto) {
        HttpTaskParameter parameter = new HttpTaskParameter();

        schedulerPluginHelper.getScheduler().createSingleHttpTask(parameter);
        return null;
    }

    // 创建单个Http任务

    // 查看任务定义

    // 查看任务日志
}
