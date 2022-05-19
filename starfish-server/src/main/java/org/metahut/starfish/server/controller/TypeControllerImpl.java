package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.TypeController;
import org.metahut.starfish.api.dto.TypeRequestCreateOrUpdateDTO;
import org.metahut.starfish.api.dto.TypeResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.parser.domain.enums.RelType;
import org.metahut.starfish.parser.domain.instance.Attribute;
import org.metahut.starfish.parser.domain.instance.BatchTypeBody;
import org.metahut.starfish.parser.domain.instance.BodyStruct;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.server.service.TypeService;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TypeControllerImpl implements TypeController {

    private final TypeService typeService;

    public TypeControllerImpl(TypeService typeService) {
        this.typeService = typeService;
    }

    //1.根据类型名称获取类型集合，支持上一页，下一页

    //2.创建类型
    @Override
    public ResultEntity<TypeResponseDTO> createType(TypeRequestCreateOrUpdateDTO typeRequestCreateOrUpdateDTO) {
        return ResultEntity.success(typeService.createType(typeRequestCreateOrUpdateDTO));
    }

}
