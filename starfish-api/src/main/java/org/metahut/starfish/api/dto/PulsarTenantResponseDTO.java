package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 *
 */
@ApiModel(description = "pulsar tenant response dto")
public class PulsarTenantResponseDTO {

    @ApiModelProperty(value = "pulsar tenant id")
    private Long id;

    @ApiModelProperty(value = "pulsar tenant name")
    private String name;

    @ApiModelProperty(value = "pulsar tenant allowedClusters")
    private List<PulsarClusterResponseDTO> allowedClusters;

    @ApiModelProperty(value = "pulsar tenant namespaces")
    private List<PulsarNamespaceResponseDTO> namespaces;

    @ApiModelProperty(value = "pulsar tenant create time")
    private Date createTime;

    @ApiModelProperty(value = "pulsar tenant update time")
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

    public List<PulsarClusterResponseDTO> getAllowedClusters() {
        return allowedClusters;
    }

    public void setAllowedClusters(List<PulsarClusterResponseDTO> allowedClusters) {
        this.allowedClusters = allowedClusters;
    }

    public List<PulsarNamespaceResponseDTO> getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(List<PulsarNamespaceResponseDTO> namespaces) {
        this.namespaces = namespaces;
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
