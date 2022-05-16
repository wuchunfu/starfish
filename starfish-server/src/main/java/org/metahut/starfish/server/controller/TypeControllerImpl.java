package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.TypeController;
import org.metahut.starfish.api.dto.CreateTypeRequestDTO;
import org.metahut.starfish.api.dto.CreateTypeResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.parser.domain.enums.RelType;
import org.metahut.starfish.parser.domain.instance.Attribute;
import org.metahut.starfish.parser.domain.instance.BatchTypeBody;
import org.metahut.starfish.parser.domain.instance.BodyStruct;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TypeControllerImpl implements TypeController {

    private final AbstractMetaDataService abstractMetaDataService;

    public TypeControllerImpl(AbstractMetaDataService abstractMetaDataService) {
        this.abstractMetaDataService = abstractMetaDataService;
    }

    //1.根据类型名称获取类型集合，支持上一页，下一页

    //2.创建类型
    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public ResultEntity<CreateTypeResponseDTO> createType(CreateTypeRequestDTO createTypeRequestDTO) {
        BatchTypeBody batchTypeBody = new BatchTypeBody();
        BodyStruct bodyStruct = new BodyStruct();
        bodyStruct.setName(createTypeRequestDTO.getSourceName());
        batchTypeBody.setSource(bodyStruct);
        Class classInfo = new Class();
        classInfo.setNameAttributeRel(createTypeRequestDTO.getClassRelName());
        classInfo.setPackagePath("org.starfish");
        classInfo.setName(createTypeRequestDTO.getClassName());
        List<Attribute> attributeList = createTypeRequestDTO.getClassField().stream().map
                (field -> {
                Attribute attribute = new Attribute();
                String[] fields = field.split(":");
                attribute.setName(fields[0]);
                attribute.setClassName(fields[1]);
                attribute.setArray(Boolean.valueOf(fields[2]));
                attribute.setRelType(RelType.valueOf(fields[3]));
                return attribute;
                }
                ).collect(Collectors.toList());
        classInfo.setAttributes(attributeList);
        batchTypeBody.setTypes(Arrays.asList(classInfo));
        Long sourceId = (Long) abstractMetaDataService.initSourceAndType(batchTypeBody);
        CreateTypeResponseDTO createTypeResponseDTO = new CreateTypeResponseDTO();
        createTypeResponseDTO.setSourceId(sourceId);
        return ResultEntity.success(createTypeResponseDTO);
    }

}
