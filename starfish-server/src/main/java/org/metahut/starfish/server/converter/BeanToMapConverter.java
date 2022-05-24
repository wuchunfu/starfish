package org.metahut.starfish.server.converter;

import org.metahut.starfish.server.utils.JSONUtils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BeanToMapConverter implements Converter<Object, Map<String,Object>> {
    @Override
    public Map<String, Object> convert(Object source) {
        return JSONUtils.toMap(source);
    }
}
