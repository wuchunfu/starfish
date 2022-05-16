package org.metahut.starfish.server.utils;

import org.metahut.starfish.api.dto.BatchMetaDataDTO;
import org.metahut.starfish.api.dto.BatchSchemaDTO;
import org.metahut.starfish.api.enums.Status;
import org.metahut.starfish.api.exception.DatasourceException;
import org.metahut.starfish.datasource.common.JSONUtils;

import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * convert dto to entry so that to operate with service API
 */
public class ConvertUtils {

    //convert to entry type
    public static <T> BatchSchemaDTO getBatchSchemaDTO(T converImpl, List<Object> sources) {
        BatchSchemaDTO batchSchemaDTO = new BatchSchemaDTO();
        Class<?> covertInstance = converImpl.getClass();
        try {
            Method convertMethod = covertInstance.getDeclaredMethod("convert", Object.class);
            List<BatchSchemaDTO.ClassDTO> classDTOList = sources.stream().map(source -> {
                try {
                    return (BatchSchemaDTO.ClassDTO) convertMethod.invoke(converImpl, source);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
            batchSchemaDTO.setTypes(classDTOList);
            BatchSchemaDTO.SourceBodyDTO sourceBodyDTO = new BatchSchemaDTO.SourceBodyDTO();
            sourceBodyDTO.setName(Constants.DATASOURCE_TYPE);
            batchSchemaDTO.setSource(sourceBodyDTO);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return batchSchemaDTO;
    }

    //convert to entry instance
    public static <T> BatchMetaDataDTO getBatchMetaDataDTO(T converImpl, List<Object> sources) {
        BatchMetaDataDTO batchMetaDataDTO = new BatchMetaDataDTO();
        Class<?> covertInstance = converImpl.getClass();
        HashMap<String, String> metaDataInstance = new HashMap<>();
        try {
            Method convertMethod = covertInstance.getDeclaredMethod("convert", Object.class);
            sources.stream().forEach(source -> {
                String key = null;
                try {
                    Field field = source.getClass().getDeclaredField("type");
                    field.setAccessible(true);
                    key = "org.starfish." + Constants.DATASOURCE_TYPE + field.get(source);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                try {
                    metaDataInstance.put(key, JSONUtils.toJSONString(convertMethod.invoke(converImpl, source)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        batchMetaDataDTO.setInstances(metaDataInstance);
        batchMetaDataDTO.setSourceName(Constants.DATASOURCE_TYPE);
        return batchMetaDataDTO;
    }

    //convert to entry type and instance
    public static <T, R> Map<String, Object> getBatchAll(T converSchemaImpl, R converMetaDataImpl, List<Object> sources) {
        BatchSchemaDTO batchSchemaDTO = getBatchSchemaDTO(converSchemaImpl, sources);
        BatchMetaDataDTO batchMetaDataDTO = getBatchMetaDataDTO(converMetaDataImpl, sources);
        Map<String, Object> batchSchemaAndMetaDataDTO = new HashMap<>();
        if (Objects.nonNull(batchSchemaDTO)) {
            batchSchemaAndMetaDataDTO.put("type", batchSchemaDTO);
        }
        if (Objects.nonNull(batchMetaDataDTO)) {
            batchSchemaAndMetaDataDTO.put("instance", batchMetaDataDTO);
        }
        return batchSchemaAndMetaDataDTO;
    }

    //convert to entry instance
    public static <R> HashMap<String, R> getMetaDataProps(Object source, @Nullable Map<String, R> rel) {

        HashMap<String, R> metaDataInstance = new HashMap<>();
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                metaDataInstance.put(field.getName(), (R) field.get(source));
            } catch (IllegalAccessException e) {
                throw new DatasourceException(Status.DATASOURCE_PARAMETER_CONVERT_ERROR, e);
            }
        }
        if (Objects.nonNull(rel) && rel.isEmpty()) {
            metaDataInstance.putAll(rel);
        }
        return metaDataInstance;
    }
}
