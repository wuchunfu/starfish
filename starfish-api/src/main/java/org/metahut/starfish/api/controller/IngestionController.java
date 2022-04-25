package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.IngestionCollectorRequestDTO;
import org.metahut.starfish.api.dto.ResultEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("ingestion")
public interface IngestionController {

    @PostMapping("createCollector")
    ResultEntity createCollector(@RequestBody IngestionCollectorRequestDTO ingestionCollectorRequestDTO);
}
