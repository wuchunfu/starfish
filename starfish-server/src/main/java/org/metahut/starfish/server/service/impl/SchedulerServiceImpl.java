package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.ScheduleCronRequestDTO;
import org.metahut.starfish.scheduler.api.parameters.ScheduleCronParameter;
import org.metahut.starfish.server.scheduler.SchedulerPluginHelper;
import org.metahut.starfish.server.service.SchedulerService;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private SchedulerPluginHelper schedulerPluginHelper;

    public SchedulerServiceImpl(SchedulerPluginHelper schedulerHelper) {
        this.schedulerPluginHelper = schedulerHelper;
    }

    @Override
    public Collection<String> previewSchedule(ScheduleCronRequestDTO scheduleCronRequestDTO) {
        ScheduleCronParameter parameter = new ScheduleCronParameter();
        parameter.setCron(scheduleCronRequestDTO.getCron());
        parameter.setStartTime(scheduleCronRequestDTO.getStartTime());
        parameter.setEndTime(scheduleCronRequestDTO.getEndTime());
        parameter.setTimezoneId(scheduleCronRequestDTO.getTimezoneId());
        return schedulerPluginHelper.getScheduler().previewSchedule(parameter);
    }

}
