package org.metahut.starfish.parser.domain.instance;

import java.util.List;
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
