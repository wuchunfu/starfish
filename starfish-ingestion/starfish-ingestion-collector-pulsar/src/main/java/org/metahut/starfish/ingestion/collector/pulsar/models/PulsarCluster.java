package org.metahut.starfish.ingestion.collector.pulsar.models;

import java.util.Collection;

public class PulsarCluster {

    private String name;
    private String type;
    private String description;
    private String listenerName;
    private String serviceUrl;

    private Collection<PulsarTopic> topics;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<PulsarTopic> getTopics() {
        return topics;
    }

    public void setTopics(Collection<PulsarTopic> topics) {
        this.topics = topics;
    }

    public String getListenerName() {
        return listenerName;
    }

    public void setListenerName(String listenerName) {
        this.listenerName = listenerName;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}
