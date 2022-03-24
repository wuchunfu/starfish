package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.HttpTaskParameterDto;
import org.metahut.starfish.api.dto.ResultEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("scheduler")
public interface SchedulerController {

    @PostMapping("createSingleHttpTask")
    ResultEntity createSingleHttpTask(@RequestBody HttpTaskParameterDto httpTaskDto);

}
