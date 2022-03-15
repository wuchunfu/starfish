package org.metahut.starfish.api.controller.v1;

import org.metahut.starfish.api.dto.ResultEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("v1/test")
public interface TestController {

    @GetMapping("list")
    ResultEntity queryList();
}
