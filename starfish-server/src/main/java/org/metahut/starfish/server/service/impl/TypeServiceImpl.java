/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.TypeRequestBatchCreateOrUpdateDTO;
import org.metahut.starfish.api.dto.TypeResponseDTO;
import org.metahut.starfish.parser.domain.enums.RelType;
import org.metahut.starfish.parser.domain.instance.Attribute;
import org.metahut.starfish.parser.domain.instance.BatchTypeBody;
import org.metahut.starfish.parser.domain.instance.BodyStruct;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.server.service.TypeService;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeServiceImpl implements TypeService {

    private final AbstractMetaDataService metaDataService;

    public TypeServiceImpl(AbstractMetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }

    private BatchTypeBody generateBatchTypeBody(TypeRequestBatchCreateOrUpdateDTO requestDTO) {
        BatchTypeBody batchTypeBody = new BatchTypeBody();
        BodyStruct bodyStruct = new BodyStruct();
        bodyStruct.setAttributes(requestDTO.getSource().getAttributes());
        bodyStruct.setName(requestDTO.getSource().getName());
        List<Class> classInfos = requestDTO.getTypes().stream().map(type -> {
            Class classInfo = new Class();
            classInfo.setSerialVersionUID(type.getSerialVersionUID());
            classInfo.setName(type.getName());
            classInfo.setPackagePath(type.getPackagePath());
            classInfo.setNameAttributeRel(type.getNameAttributeRel());
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
        batchTypeBody.setSource(bodyStruct);
        batchTypeBody.setTypes(classInfos);
        return batchTypeBody;
    }

    @Override
    public TypeResponseDTO initTypes(TypeRequestBatchCreateOrUpdateDTO requestDTO) {
        Long sourceId = (Long) metaDataService.initSourceAndType(generateBatchTypeBody(requestDTO));
        TypeResponseDTO typeResponseDTO = new TypeResponseDTO();
        typeResponseDTO.setSourceId(sourceId);
        return typeResponseDTO;
    }
}
