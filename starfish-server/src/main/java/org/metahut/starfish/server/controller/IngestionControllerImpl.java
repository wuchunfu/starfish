package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.IngestionController;
import org.metahut.starfish.api.dto.IngestionCollectorRequestDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.server.service.IngestionService;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngestionControllerImpl implements IngestionController {

    private final IngestionService ingestionService;

    public IngestionControllerImpl(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @Override
    public ResultEntity createCollector(IngestionCollectorRequestDTO ingestionCollectorRequestDTO) {
        return ResultEntity.success(ingestionService.createCollector(ingestionCollectorRequestDTO));
    }

}
