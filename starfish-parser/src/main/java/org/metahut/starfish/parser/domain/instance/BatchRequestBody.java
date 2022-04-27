package org.metahut.starfish.parser.domain.instance;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class BatchRequestBody<T> {

    private SourceBody<T> source;

    private List<Class> types;

    /**
     * key:     classFullName
     * value:   objectJson
     */
    private Map<String,List<String>> instances;

    public SourceBody<T> getSource() {
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

    public static class SourceBody<T> {

        private String name;

        private Map<String,T> attributes;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, T> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, T> attributes) {
            this.attributes = attributes;
        }
    }

}
