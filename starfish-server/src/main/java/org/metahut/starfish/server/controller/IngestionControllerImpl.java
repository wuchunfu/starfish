package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.IngestionController;
import org.metahut.starfish.api.dto.IngestionCollectorExecuteLogResponseDTO;
import org.metahut.starfish.api.dto.IngestionCollectorLogRequestDTO;
import org.metahut.starfish.api.dto.IngestionCollectorLogResponseDTO;
import org.metahut.starfish.api.dto.IngestionCollectorCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.IngestionCollectorResponseDTO;
import org.metahut.starfish.api.dto.QueryIngestionCollectorRequestDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.enums.Status;
import org.metahut.starfish.datasource.api.DatasourceResult;
import org.metahut.starfish.server.service.IngestionService;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngestionControllerImpl implements IngestionController {

    private final IngestionService ingestionService;

    public IngestionControllerImpl(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    public ResultEntity testConnection(String type, String parameter) {
        DatasourceResult datasourceResult = ingestionService.testConnection(type, parameter);
        return datasourceResult.isStatus() ? ResultEntity.success(datasourceResult) :
                ResultEntity.of(Status.DATASOURCE_TEST_FAIL.getCode(), String.format(Status.DATASOURCE_TEST_FAIL.getMessage(), datasourceResult.getMessage()));
    }

    @Override
    public ResultEntity<IngestionCollectorResponseDTO> createCollector(IngestionCollectorCreateOrUpdateRequestDTO ingestionCollectorCreateOrUpdateRequestDTO) {
        return ResultEntity.success(ingestionService.createCollector(ingestionCollectorCreateOrUpdateRequestDTO));
    }

    @Override
    public ResultEntity<IngestionCollectorResponseDTO> updateCollector(Long collectorId, IngestionCollectorCreateOrUpdateRequestDTO ingestionCollectorCreateOrUpdateRequestDTO) {
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
