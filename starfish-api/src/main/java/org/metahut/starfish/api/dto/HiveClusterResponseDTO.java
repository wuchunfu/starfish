package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 *
 */
@ApiModel(description = "hive cluster response dto")
public class HiveClusterResponseDTO {

    @ApiModelProperty(value = "hive cluster id")
    private Long id;

    @ApiModelProperty(value = "hive cluster name")
    private String name;

    @ApiModelProperty(value = "hive cluster description")
    private String description;

    @ApiModelProperty(value = "hive cluster create time")
    private Date createTime;

    @ApiModelProperty(value = "hive cluster update time")
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
