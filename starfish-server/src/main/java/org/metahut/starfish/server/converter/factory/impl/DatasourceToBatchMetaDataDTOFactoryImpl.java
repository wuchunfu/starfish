package org.metahut.starfish.server.converter.factory.impl;

import org.metahut.starfish.api.enums.Status;
import org.metahut.starfish.api.exception.DatasourceException;
import org.metahut.starfish.datasource.common.JSONUtils;
import org.metahut.starfish.server.converter.factory.DatasourceToBatchMetaDataDTOFactory;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class DatasourceToBatchMetaDataDTOFactoryImpl implements DatasourceToBatchMetaDataDTOFactory {

    @Override
    public List<String> convert(Object source) {
        if (source == null) {
            return null;
        }
        List<String> metaDataList = new ArrayList<>();
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            HashMap<String, Object> metaInstanceMap = new HashMap<>();
            try {
                field.setAccessible(true);
                metaInstanceMap.put(field.getName(), field.get(source));
            } catch (IllegalAccessException e) {
                throw new DatasourceException(Status.DATASOURCE_PARAMETER_CONVERT_ERROR, e);
            }
            metaDataList.add(JSONUtils.toJSONString(metaInstanceMap));
        }

        return metaDataList;
    }
}
