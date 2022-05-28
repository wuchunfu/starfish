package org.metahut.starfish.ingestion.collector.pulsar.models;

public class PulsarNamespace {

    private String name;
    private Integer messageTTL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMessageTTL() {
        return messageTTL;
    }

    public void setMessageTTL(Integer messageTTL) {
        this.messageTTL = messageTTL;
    }
}
