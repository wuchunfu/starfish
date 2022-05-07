package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.MetaDataController;
import org.metahut.starfish.api.dto.BatchMetaDataDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.parser.domain.enums.RelType;
import org.metahut.starfish.parser.domain.instance.Attribute;
import org.metahut.starfish.parser.domain.instance.BatchRequestBody;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@RestController
public class MetaDataControllerImpl implements MetaDataController {

    @Autowired
    private AbstractMetaDataService abstractMetaDataService;

    @Override
    public ResultEntity batchRequest(BatchMetaDataDTO metaDataDTO) throws Exception {
        BatchRequestBody batchRequestBody = new BatchRequestBody();
        batchRequestBody.setInstances(metaDataDTO.getInstances());
        BatchRequestBody.SourceBody sourceBody = new BatchRequestBody.SourceBody();
        sourceBody.setAttributes(metaDataDTO.getSource().getAttributes());
        sourceBody.setName(metaDataDTO.getSource().getName());
        List<Class> classInfos = metaDataDTO.getTypes().stream().map(type -> {
            Class classInfo = new Class();
            classInfo.setSerialVersionUID(type.getSerialVersionUID());
            classInfo.setName(type.getName());
            classInfo.setPackagePath(type.getPackagePath());
            if (type.getAttributes() != null) {
                classInfo.setAttributes(type.getAttributes().stream().map(
                        attributeDTO -> {
                            Attribute attribute = new Attribute();
                            attribute.setName(attributeDTO.getName());
                            attribute.setArray(attributeDTO.isArray());
                            attribute.setClassName(attributeDTO.getClassName());
                            if (attributeDTO.getRelType() != null) {
                                attribute.setRelType(RelType.valueOf(attributeDTO.getRelType()));
                            }
                            attribute.setDefaultValue(attributeDTO.getDefaultValue());
                            return attribute;
                        }
                ).collect(Collectors.toList()));
            }
            return classInfo;
        }).collect(Collectors.toList());
        batchRequestBody.setSource(sourceBody);
        batchRequestBody.setTypes(classInfos);
        abstractMetaDataService.batchCreate(batchRequestBody);
        return null;
    }

}
