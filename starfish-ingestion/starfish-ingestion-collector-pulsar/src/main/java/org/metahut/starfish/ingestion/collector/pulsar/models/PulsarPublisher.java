package org.metahut.starfish.ingestion.collector.pulsar.models;

public class PulsarPublisher {

    private String accessMode;

    private Double msgRateIn;

    private Double msgThroughputIn;

    private Double averageMsgSize;

    private Double chunkedMessageRate;

    private Long producerId;

    private String producerName;

    private String address;

    private String connectedSince;

    private String clientVersion;

    public String getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(String accessMode) {
        this.accessMode = accessMode;
    }

    public Double getMsgRateIn() {
        return msgRateIn;
    }

    public void setMsgRateIn(Double msgRateIn) {
        this.msgRateIn = msgRateIn;
    }

    public Double getMsgThroughputIn() {
        return msgThroughputIn;
    }

    public void setMsgThroughputIn(Double msgThroughputIn) {
        this.msgThroughputIn = msgThroughputIn;
    }

    public Double getAverageMsgSize() {
        return averageMsgSize;
    }

    public void setAverageMsgSize(Double averageMsgSize) {
        this.averageMsgSize = averageMsgSize;
    }

    public Double getChunkedMessageRate() {
        return chunkedMessageRate;
    }

    public void setChunkedMessageRate(Double chunkedMessageRate) {
        this.chunkedMessageRate = chunkedMessageRate;
    }

    public Long getProducerId() {
        return producerId;
    }

    public void setProducerId(Long producerId) {
        this.producerId = producerId;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConnectedSince() {
        return connectedSince;
    }

    public void setConnectedSince(String connectedSince) {
        this.connectedSince = connectedSince;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }
}
