package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 *
 */
@ApiModel(description = "pulsar topic response dto")
public class PulsarPartitionResponseDTO {

    @ApiModelProperty(value = "pulsar topic id")
    private Long id;

    @ApiModelProperty(value = "pulsar topic name")
    private String name;

    @ApiModelProperty(value = "pulsar topic storage size")
    private Long storageSize;

    @ApiModelProperty(value = "pulsar topic back log size")
    private Long backlogSize;

    @ApiModelProperty(value = "pulsar topic")
    private PulsarTopicResponseDTO topic;

    @ApiModelProperty(value = "pulsar topic publishers")
    private List<PulsarPublisherResponseDTO> publishers;

    @ApiModelProperty(value = "pulsar topic create time")
    private Date createTime;

    @ApiModelProperty(value = "pulsar topic update time")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public PulsarTopicResponseDTO getTopic() {
        return topic;
    }

    public void setTopic(PulsarTopicResponseDTO topic) {
        this.topic = topic;
    }

    public List<PulsarPublisherResponseDTO> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<PulsarPublisherResponseDTO> publishers) {
        this.publishers = publishers;
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
