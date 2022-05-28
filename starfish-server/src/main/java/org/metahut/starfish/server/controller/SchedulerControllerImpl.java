package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.SchedulerController;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.dto.SchedulerCronRequestDTO;
import org.metahut.starfish.server.service.SchedulerService;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchedulerControllerImpl implements SchedulerController {

    private SchedulerService schedulerService;

    public SchedulerControllerImpl(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public ResultEntity previewSchedule(SchedulerCronRequestDTO schedulerCronRequestDTO) {
        return ResultEntity.success(schedulerService.previewSchedule(schedulerCronRequestDTO));
    }

    // 创建单个Http任务

    // 查看任务定义

    // 查看任务日志
}
