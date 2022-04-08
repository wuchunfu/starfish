package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.ResultEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("test")
public interface TestController {

    @GetMapping("list")
    ResultEntity queryList();
}
