package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.SchedulerCronRequestDTO;
import org.metahut.starfish.scheduler.api.IScheduler;
import org.metahut.starfish.scheduler.api.parameters.ScheduleCronParameter;
import org.metahut.starfish.server.service.SchedulerService;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private final IScheduler scheduler;

    public SchedulerServiceImpl(IScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public Collection<String> previewSchedule(SchedulerCronRequestDTO schedulerCronRequestDTO) {
        ScheduleCronParameter parameter = new ScheduleCronParameter();
        parameter.setCrontab(schedulerCronRequestDTO.getCron());
        parameter.setStartTime(schedulerCronRequestDTO.getStartTime());
        parameter.setEndTime(schedulerCronRequestDTO.getEndTime());
        parameter.setTimezoneId(schedulerCronRequestDTO.getTimezoneId());
        return scheduler.previewSchedule(parameter);
    }

}
