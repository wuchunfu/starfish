package org.metahut.starfish.parser.domain.instance;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class InstanceAnalysisStruct<K,T> {

    private Map<String,Instance<K,T>> instances;

    private List<Link<K,T>> links;

    public Map<String, Instance<K,T>> getInstances() {
        return instances;
    }

    public void setInstances(Map<String, Instance<K,T>> instances) {
        this.instances = instances;
    }

    public List<Link<K,T>> getLinks() {
        return links;
    }

    public void setLinks(List<Link<K,T>> links) {
        this.links = links;
    }

    public static class Instance<K,T> {
        private K id;
        private K typeId;
        private String qualifiedName;
        private Map<String,T> properties;
        private String path;

        public K getId() {
            return id;
        }

        public void setId(K id) {
            this.id = id;
        }

        public K getTypeId() {
            return typeId;
        }

        public void setTypeId(K typeId) {
            this.typeId = typeId;
        }

        public String getQualifiedName() {
            return qualifiedName;
        }

        public void setQualifiedName(String qualifiedName) {
            this.qualifiedName = qualifiedName;
        }

        public Map<String, T> getProperties() {
            return properties;
        }

        public void setProperties(Map<String, T> properties) {
            this.properties = properties;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

    }

    public static class Link<K,T> {
        private Instance<K,T> head;
        private Instance<K,T> tail;
        private String property;

        public Instance<K,T> getHead() {
            return head;
        }

        public void setHead(Instance<K,T> head) {
            this.head = head;
        }

        public Instance<K,T> getTail() {
            return tail;
        }

        public void setTail(Instance<K,T> tail) {
            this.tail = tail;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public Link() {

        }

        public Link(Instance<K,T> head, Instance<K,T> tail, String property) {
            this.head = head;
            this.tail = tail;
            this.property = property;
        }
    }
}
