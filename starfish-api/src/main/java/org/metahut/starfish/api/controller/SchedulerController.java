package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.ResultEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("scheduler")
public interface SchedulerController {

    ResultEntity createSingleHttpTask();

}
