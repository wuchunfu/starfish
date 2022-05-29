package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 *
 */
@ApiModel(description = "pulsar publisher response dto")
public class PulsarPublisherResponseDTO {

    @ApiModelProperty(value = "pulsar publisher id")
    private Long id;

    @ApiModelProperty(value = "pulsar publisher access mode")
    private String accessMode;

    @ApiModelProperty(value = "pulsar publisher message rate in")
    private Double msgRateIn;

    @ApiModelProperty(value = "pulsar publisher message throughput in")
    private Double msgThroughputIn;

    @ApiModelProperty(value = "pulsar publisher average message size")
    private Double averageMsgSize;

    @ApiModelProperty(value = "pulsar publisher chunked message rate")
    private Double chunkedMessageRate;

    @ApiModelProperty(value = "pulsar publisher producer id")
    private Long producerId;

    @ApiModelProperty(value = "pulsar publisher producer name")
    private String producerName;

    @ApiModelProperty(value = "pulsar publisher address")
    private String address;

    @ApiModelProperty(value = "pulsar publisher connected since")
    private String connectedSince;

    @ApiModelProperty(value = "pulsar publisher client version")
    private String clientVersion;

    @ApiModelProperty(value = "pulsar publisher create time")
    private Date createTime;

    @ApiModelProperty(value = "pulsar publisher update time")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
