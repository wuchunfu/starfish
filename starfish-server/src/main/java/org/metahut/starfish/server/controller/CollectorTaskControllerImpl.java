package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.CollectorTaskController;
import org.metahut.starfish.api.dto.CollectorTaskCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceLogResponseDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceResponseDTO;
import org.metahut.starfish.api.dto.CollectorTaskRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.server.service.CollectorTaskService;

import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CollectorTaskControllerImpl implements CollectorTaskController {

    private final CollectorTaskService collectorTaskService;

    public CollectorTaskControllerImpl(CollectorTaskService collectorTaskService) {
        this.collectorTaskService = collectorTaskService;
    }

    @Override
    public ResultEntity<CollectorTaskResponseDTO> create(CollectorTaskCreateOrUpdateRequestDTO requestDTO) {
        return ResultEntity.success(collectorTaskService.create(requestDTO));
    }

    @Override
    public ResultEntity<CollectorTaskResponseDTO> update(Long id, CollectorTaskCreateOrUpdateRequestDTO requestDTO) {
        return ResultEntity.success(collectorTaskService.update(id, requestDTO));
    }

    @Override
    public ResultEntity deleteById(Long id) {
        collectorTaskService.deleteById(id);
        return ResultEntity.success();
    }

    @Override
    public ResultEntity<CollectorTaskResponseDTO> queryById(Long id) {
        return ResultEntity.success(collectorTaskService.queryById(id));
    }

    @Override
    public ResultEntity<PageResponseDTO<CollectorTaskResponseDTO>> queryListPage(CollectorTaskRequestDTO requestDTO) {
        return ResultEntity.success(collectorTaskService.queryListPage(requestDTO));
    }

    @Override
    public ResultEntity<Collection<CollectorTaskResponseDTO>> queryList(CollectorTaskRequestDTO requestDTO) {
        return ResultEntity.success(collectorTaskService.queryList(requestDTO));
    }

    @Override
    public ResultEntity<CollectorTaskInstanceResponseDTO> queryInstanceListPage(CollectorTaskInstanceRequestDTO requestDTO) {
        return ResultEntity.success(collectorTaskService.queryInstanceListPage(requestDTO));
    }

    @Override
    public ResultEntity<CollectorTaskInstanceLogResponseDTO> queryInstanceLog(String instanceId) {
        return ResultEntity.success(collectorTaskService.queryInstanceLog(instanceId));
    }
}
