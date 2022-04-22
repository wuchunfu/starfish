package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.IngestionController;
import org.metahut.starfish.api.dto.ResultEntity;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngestionControllerImpl implements IngestionController {

    //collector_name, description, datasourceId, collector_params, crontab, scheduler_code

    public ResultEntity create() {

        return null;
    }

}
