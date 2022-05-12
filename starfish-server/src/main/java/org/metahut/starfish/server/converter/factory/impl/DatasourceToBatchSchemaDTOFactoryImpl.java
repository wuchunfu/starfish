package org.metahut.starfish.server.converter.factory.impl;

import org.metahut.starfish.api.dto.BatchSchemaDTO;
import org.metahut.starfish.server.converter.factory.DatasourceToBatchSchemaDTOFactory;
import org.metahut.starfish.server.utils.Constants;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatasourceToBatchSchemaDTOFactoryImpl implements DatasourceToBatchSchemaDTOFactory {
    @Override
    public BatchSchemaDTO.ClassDTO convert(Object source) {
        if (source == null) {
            return null;
        }
        BatchSchemaDTO.ClassDTO classDTO = new BatchSchemaDTO.ClassDTO();
        try {
            Field type =  source.getClass().getDeclaredField("type");
            type.setAccessible(true);
            String typeName = (String) type.get(source);
            classDTO.setName(Constants.DATASOURCE_TYPE + typeName);
            classDTO.setPackagePath("org.starfish");
            List<BatchSchemaDTO.AttributeDTO> attributeDTOList = Arrays.stream(source.getClass().getDeclaredFields()).map(field -> {
                BatchSchemaDTO.AttributeDTO attributeDTO = new BatchSchemaDTO.AttributeDTO();
                field.setAccessible(true);
                try {
                    if (field.get(source) instanceof Collection) {
                        attributeDTO.setArray(true);
                    } else {
                        attributeDTO.setArray(false);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                attributeDTO.setName(field.getName());
                attributeDTO.setClassName(field.getType().getName());
                attributeDTO.setRelType("PRIMITIVE");
                return attributeDTO;
            }).collect(Collectors.toList());
            classDTO.setAttributes(attributeDTOList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return classDTO;
    }
}