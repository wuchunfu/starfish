package org.metahut.starfish.api.dto;

import java.util.Map;

/**
 *
 */
public class BatchMetaDataDTO {

    private String sourceName;
    /**
     * key:     classFullName
     * value:   jsons or json of the object
     */
    private Map<String,String> instances;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Map<String, String> getInstances() {
        return instances;
    }

    public void setInstances(Map<String, String> instances) {
        this.instances = instances;
    }

}
