package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.IngestionCollectorRequestDTO;
import org.metahut.starfish.api.dto.ResultEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "INGESTION_TAG")
@RequestMapping("ingestion")
public interface IngestionController {

    @ApiOperation(value = "createCollector", notes = "CREATE_INGESTION_COLLECTOR_INSTANCE_NOTES")
    @PostMapping("createCollector")
    ResultEntity createCollector(@RequestBody IngestionCollectorRequestDTO ingestionCollectorRequestDTO);
}
