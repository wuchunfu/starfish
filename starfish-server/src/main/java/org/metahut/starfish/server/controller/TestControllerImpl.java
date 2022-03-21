package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.TestController;
import org.metahut.starfish.api.dto.ResultEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestControllerImpl implements TestController {

    @Override
    public ResultEntity queryList() {
        return ResultEntity.success();
    }
}
