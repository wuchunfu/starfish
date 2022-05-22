package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.dto.TypeRequestBatchCreateOrUpdateDTO;
import org.metahut.starfish.api.dto.TypeResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "TYPE_TAG")
@RequestMapping("type")
public interface TypeController {

    @PostMapping("init")
    @ApiOperation(value = "init", notes = "BATCH_CREATE_TYPE_NOTES")
    ResultEntity<TypeResponseDTO> init(@RequestBody TypeRequestBatchCreateOrUpdateDTO requestDTO);

}
