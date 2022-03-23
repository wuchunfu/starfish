package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.dto.TestDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("test")
public interface TestController {

    @GetMapping("list")
    ResultEntity queryList();

    @PostMapping("create")
    ResultEntity create(@RequestBody TestDto testDto);
}
