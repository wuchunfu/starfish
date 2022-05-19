package org.metahut.starfish.api.dto;

import java.util.Map;

public class NodePropertiesRequestDTO {

    private Map<String, Object> properties;

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
