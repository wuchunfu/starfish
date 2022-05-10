package org.metahut.starfish.api.dto;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class BatchMetaDataDTO {

    private String sourceName;
    /**
     * key:     classFullName
     * value:   objectJson
     */
    private Map<String,List<String>> instances;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Map<String, List<String>> getInstances() {
        return instances;
    }

    public void setInstances(Map<String, List<String>> instances) {
        this.instances = instances;
    }

}
