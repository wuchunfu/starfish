package org.metahut.starfish.parser.domain.instance;

import java.util.Map;

/**
 *
 */
public class BatchInstanceBody {

    private String sourceName;

    /**
     * key:     classFullName
     * value:   objectJsons
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
