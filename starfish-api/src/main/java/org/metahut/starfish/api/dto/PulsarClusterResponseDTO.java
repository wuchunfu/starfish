package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 *
 */
@ApiModel(description = "pulsar cluster response dto")
public class PulsarClusterResponseDTO {

    @ApiModelProperty(value = "pulsar cluster id")
    private Long id;

    @ApiModelProperty(value = "pulsar cluster name")
    private String name;

    @ApiModelProperty(value = "pulsar cluster type")
    private String type;

    @ApiModelProperty(value = "pulsar cluster service url")
    private String serviceUrl;

    @ApiModelProperty(value = "pulsar cluster listener name")
    private String listenerName;

    @ApiModelProperty(value = "pulsar cluster description")
    private String description;

    @ApiModelProperty(value = "pulsar cluster allowed tenants")
    private List<PulsarTenantResponseDTO> allowedTenants;

    @ApiModelProperty(value = "pulsar cluster create time")
    private Date createTime;

    @ApiModelProperty(value = "pulsar cluster update time")
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getListenerName() {
        return listenerName;
    }

    public void setListenerName(String listenerName) {
        this.listenerName = listenerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PulsarTenantResponseDTO> getAllowedTenants() {
        return allowedTenants;
    }

    public void setAllowedTenants(List<PulsarTenantResponseDTO> allowedTenants) {
        this.allowedTenants = allowedTenants;
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
