package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.CollectorTaskController;
import org.metahut.starfish.api.dto.CollectorTaskCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CollectorTaskControllerImpl implements CollectorTaskController {

    @Override
    public ResultEntity<CollectorTaskResponseDTO> create(CollectorTaskCreateOrUpdateRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ResultEntity<CollectorTaskResponseDTO> update(Long id, CollectorTaskCreateOrUpdateRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ResultEntity deleteById(Long id) {
        return null;
    }

    @Override
    public ResultEntity<CollectorTaskResponseDTO> queryById(Long id) {
        return null;
    }

    @Override
    public ResultEntity<List<CollectorTaskResponseDTO>> queryPageList(CollectorTaskRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ResultEntity<List<CollectorTaskResponseDTO>> queryList(CollectorTaskRequestDTO requestDTO) {
        return null;
    }
}
