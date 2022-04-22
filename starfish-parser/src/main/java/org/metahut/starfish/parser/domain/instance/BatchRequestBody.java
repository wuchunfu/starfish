package org.metahut.starfish.parser.domain.instance;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class BatchRequestBody {

    private SourceBody source;

    private List<Class> types;

    /**
     * key:     classFullName
     * value:   objectJson
     */
    private Map<String,List<String>> instances;

    public SourceBody getSource() {
        return source;
    }

    public void setSource(SourceBody source) {
        this.source = source;
    }

    public List<Class> getTypes() {
        return types;
    }

    public void setTypes(List<Class> types) {
        this.types = types;
    }

    public Map<String, List<String>> getInstances() {
        return instances;
    }

    public void setInstances(Map<String, List<String>> instances) {
        this.instances = instances;
    }

    class SourceBody {

        private String name;

        private Map<String,Object> attributes;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, Object> attributes) {
            this.attributes = attributes;
        }
    }

}
