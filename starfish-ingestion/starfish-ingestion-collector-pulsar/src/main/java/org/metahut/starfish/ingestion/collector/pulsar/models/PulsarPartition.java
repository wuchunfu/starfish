package org.metahut.starfish.ingestion.collector.pulsar.models;

public class PulsarPartition {

    private String name;
    private Long storageSize;
    private Long backlogSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(Long storageSize) {
        this.storageSize = storageSize;
    }

    public Long getBacklogSize() {
        return backlogSize;
    }

    public void setBacklogSize(Long backlogSize) {
        this.backlogSize = backlogSize;
    }
}
