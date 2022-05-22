package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.TypeController;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.dto.TypeRequestBatchCreateOrUpdateDTO;
import org.metahut.starfish.api.dto.TypeResponseDTO;
import org.metahut.starfish.server.service.TypeService;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TypeControllerImpl implements TypeController {

    private final TypeService typeService;

    public TypeControllerImpl(TypeService typeService) {
        this.typeService = typeService;
    }

    @Override
    public ResultEntity<TypeResponseDTO> init(TypeRequestBatchCreateOrUpdateDTO requestDTO) {
        return ResultEntity.success(typeService.initTypes(requestDTO));
    }

}
