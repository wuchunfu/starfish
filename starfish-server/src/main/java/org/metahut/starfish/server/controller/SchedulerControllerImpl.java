package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.SchedulerController;
import org.metahut.starfish.api.dto.HttpTaskParameterDto;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.scheduler.api.parameters.HttpTaskParameter;
import org.metahut.starfish.server.scheduler.SchedulerHelper;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchedulerControllerImpl implements SchedulerController {

    private SchedulerHelper schedulerHelper;

    public SchedulerControllerImpl(SchedulerHelper schedulerHelper) {
        this.schedulerHelper = schedulerHelper;
    }

    @Override
    public ResultEntity createSingleHttpTask(HttpTaskParameterDto httpTaskParameterDto) {
        HttpTaskParameter parameter = new HttpTaskParameter();

        schedulerHelper.getScheduler().createSingleHttpTask(parameter);
        return null;
    }
}
