package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 *
 */
@ApiModel(description = "pulsar namespace response dto")
public class PulsarNamespaceResponseDTO {

    @ApiModelProperty(value = "pulsar namespace id")
    private Long id;

    @ApiModelProperty(value = "pulsar namespace name")
    private String name;

    @ApiModelProperty(value = "pulsar namespace allowed tenants")
    private List<PulsarTenantResponseDTO> allowedTenants;

    @ApiModelProperty(value = "pulsar namespace message ttl")
    private Integer messageTTL;

    @ApiModelProperty(value = "pulsar namespace id")
    private List<PulsarTopicResponseDTO> topics;

    @ApiModelProperty(value = "pulsar namespace create time")
    private Date createTime;

    @ApiModelProperty(value = "pulsar namespace update time")
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

    public List<PulsarTenantResponseDTO> getAllowedTenants() {
        return allowedTenants;
    }

    public void setAllowedTenants(List<PulsarTenantResponseDTO> allowedTenants) {
        this.allowedTenants = allowedTenants;
    }

    public Integer getMessageTTL() {
        return messageTTL;
    }

    public void setMessageTTL(Integer messageTTL) {
        this.messageTTL = messageTTL;
    }

    public List<PulsarTopicResponseDTO> getTopics() {
        return topics;
    }

    public void setTopics(List<PulsarTopicResponseDTO> topics) {
        this.topics = topics;
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
