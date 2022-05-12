package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.dto.ScheduleCronRequestDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("scheduler")
public interface SchedulerController {

    @GetMapping("previewSchedule")
    ResultEntity previewSchedule(ScheduleCronRequestDTO scheduleCronRequestDTO);
}
