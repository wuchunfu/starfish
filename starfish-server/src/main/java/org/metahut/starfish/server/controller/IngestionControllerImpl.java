package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.IngestionController;
import org.metahut.starfish.api.dto.IngestionCollectorExecuteLogResponseDTO;
import org.metahut.starfish.api.dto.IngestionCollectorLogRequestDTO;
import org.metahut.starfish.api.dto.IngestionCollectorLogResponseDTO;
import org.metahut.starfish.api.dto.IngestionCollectorRequestDTO;
import org.metahut.starfish.api.dto.IngestionCollectorResponseDTO;
import org.metahut.starfish.api.dto.QueryIngestionCollectorRequestDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.server.service.IngestionService;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Override
    public ResultEntity updateCollector(Long collectorId, IngestionCollectorRequestDTO ingestionCollectorRequestDTO) {
        return null;
    }

    @Override
    public ResultEntity deleteCollector(Long collectorId) {
        return null;
    }

    @Override
    public ResultEntity batchDeleteCollector(List<Long> collectorIdList) {
        return null;
    }

    @Override
    public ResultEntity queryCollectorPage(QueryIngestionCollectorRequestDTO queryIngestionCollectorRequestDTO) {
        return null;
    }

    @Override
    public ResultEntity<IngestionCollectorResponseDTO> queryCollectorList(QueryIngestionCollectorRequestDTO queryIngestionCollectorRequestDTO) {
        return null;
    }

    @Override
    public ResultEntity<IngestionCollectorLogResponseDTO> queryCollectorLogPage(IngestionCollectorLogRequestDTO ingestionCollectorLogRequestDTO) {
        return null;
    }

    @Override
    public ResultEntity<IngestionCollectorLogResponseDTO> queryCollectorLogList(IngestionCollectorLogRequestDTO ingestionCollectorLogRequestDTO) {
        return null;
    }

    @Override
    public ResultEntity<IngestionCollectorExecuteLogResponseDTO> queryCollectorExecuteLog(Long taskId) {
        return null;
    }

}
