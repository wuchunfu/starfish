package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.CollectorAdapterController;
import org.metahut.starfish.api.dto.*;
import org.metahut.starfish.server.service.CollectorAdapterService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CollectorAdapterControllerImpl implements CollectorAdapterController {

    private final CollectorAdapterService collectorAdapterService;

    public CollectorAdapterControllerImpl(CollectorAdapterService collectorAdapterService) {
        this.collectorAdapterService = collectorAdapterService;
    }

    @Override
    public ResultEntity testConnection(String type, String parameter) {
        return null;
    }

    @Override
    public ResultEntity<CollectorAdapterResponseDTO> create(CollectorAdapterCreateOrUpdateRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ResultEntity<CollectorAdapterResponseDTO> update(Long id, CollectorAdapterCreateOrUpdateRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ResultEntity deleteById(Long id) {
        return null;
    }

    @Override
    public ResultEntity<CollectorAdapterResponseDTO> queryById(Long id) {
        return null;
    }

    @Override
    public ResultEntity<List<CollectorAdapterResponseDTO>> queryPageList(CollectorAdapterRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ResultEntity<List<CollectorAdapterResponseDTO>> queryList(CollectorAdapterRequestDTO requestDTO) {
        return null;
    }
}
