package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.TypeRequestCreateOrUpdateDTO;
import org.metahut.starfish.api.dto.TypeResponseDTO;
import org.metahut.starfish.parser.domain.enums.RelType;
import org.metahut.starfish.parser.domain.instance.Attribute;
import org.metahut.starfish.parser.domain.instance.BatchTypeBody;
import org.metahut.starfish.parser.domain.instance.BodyStruct;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.server.service.TypeService;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeServiceImpl implements TypeService {

    private final AbstractMetaDataService abstractMetaDataService;

    public TypeServiceImpl(AbstractMetaDataService abstractMetaDataService) {
        this.abstractMetaDataService = abstractMetaDataService;
    }

    @Override
    public TypeResponseDTO createType(TypeRequestCreateOrUpdateDTO typeRequestCreateOrUpdateDTO) {

        BatchTypeBody batchTypeBody = new BatchTypeBody();
        BodyStruct bodyStruct = new BodyStruct();
        bodyStruct.setName(typeRequestCreateOrUpdateDTO.getSourceName());
        batchTypeBody.setSource(bodyStruct);
        Class classInfo = new Class();
        classInfo.setNameAttributeRel(typeRequestCreateOrUpdateDTO.getClassRelName());
        classInfo.setPackagePath("org.starfish");
        classInfo.setName(typeRequestCreateOrUpdateDTO.getClassName());
        List<Attribute> attributeList = typeRequestCreateOrUpdateDTO.getClassField().stream()
                .map(field -> {
                    Attribute attribute = new Attribute();
                    String[] fields = field.split(":");
                    attribute.setName(fields[0]);
                    attribute.setClassName(fields[1]);
                    attribute.setArray(Boolean.valueOf(fields[2]));
                    attribute.setRelType(RelType.valueOf(fields[3]));
                    return attribute;
                }).collect(Collectors.toList());
        classInfo.setAttributes(attributeList);
        batchTypeBody.setTypes(Arrays.asList(classInfo));
        Long sourceId = (Long) abstractMetaDataService.initSourceAndType(batchTypeBody);
        TypeResponseDTO typeResponseDTO = new TypeResponseDTO();
        typeResponseDTO.setSourceId(sourceId);
        return typeResponseDTO;
    }
}
