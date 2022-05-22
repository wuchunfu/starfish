package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.CollectorAdapterController;
import org.metahut.starfish.api.dto.CollectorAdapterCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.server.service.CollectorAdapterService;
import org.metahut.starfish.server.utils.Assert;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.metahut.starfish.api.enums.Status.COLLECTOR_ADAPTER_TEST_CONNECTION_FAIL;

@RestController
public class CollectorAdapterControllerImpl implements CollectorAdapterController {

    private final CollectorAdapterService collectorAdapterService;

    public CollectorAdapterControllerImpl(CollectorAdapterService collectorAdapterService) {
        this.collectorAdapterService = collectorAdapterService;
    }

    @Override
    public ResultEntity testConnection(String type, String parameter) {
        CollectorResult collectorResult = collectorAdapterService.testConnection(type, parameter);
        Assert.notTrue(collectorResult.getState(), COLLECTOR_ADAPTER_TEST_CONNECTION_FAIL, collectorResult.getMessage());
        return ResultEntity.success();
    }

    @Override
    public ResultEntity<CollectorAdapterResponseDTO> create(CollectorAdapterCreateOrUpdateRequestDTO requestDTO) {
        return ResultEntity.success(collectorAdapterService.create(requestDTO));
    }

    @Override
    public ResultEntity<CollectorAdapterResponseDTO> update(Long id, CollectorAdapterCreateOrUpdateRequestDTO requestDTO) {
        return ResultEntity.success(collectorAdapterService.update(id, requestDTO));
    }

    @Override
    public ResultEntity deleteById(Long id) {
        collectorAdapterService.deleteById(id);
        return ResultEntity.success();
    }

    @Override
    public ResultEntity<CollectorAdapterResponseDTO> queryById(Long id) {
        return ResultEntity.success(collectorAdapterService.queryById(id));
    }

    @Override
    public ResultEntity<PageResponseDTO<CollectorAdapterResponseDTO>> queryListPage(CollectorAdapterRequestDTO requestDTO) {
        return ResultEntity.success(collectorAdapterService.queryListPage(requestDTO));
    }

    @Override
    public ResultEntity<List<CollectorAdapterResponseDTO>> queryList(CollectorAdapterRequestDTO requestDTO) {
        return ResultEntity.success(collectorAdapterService.queryList(requestDTO));
    }
}
