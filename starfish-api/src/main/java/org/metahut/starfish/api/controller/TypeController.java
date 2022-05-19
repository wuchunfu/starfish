package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.TypeRequestCreateOrUpdateDTO;
import org.metahut.starfish.api.dto.TypeResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "TYPE_TAG")
@RequestMapping("type")
public interface TypeController {

    @ApiOperation(value = "createType", notes = "CREATE_TYPE_NOTES")
    @PostMapping("create")
    ResultEntity<TypeResponseDTO> createType(TypeRequestCreateOrUpdateDTO typeRequestCreateOrUpdateDTO);
}
