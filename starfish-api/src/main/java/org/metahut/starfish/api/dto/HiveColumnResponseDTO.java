package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 *
 */
@ApiModel("hive column response dto")
public class HiveColumnResponseDTO {

    @ApiModelProperty(value = "hive column id")
    private Long id;

    @ApiModelProperty(value = "hive column name")
    private String name;

    @ApiModelProperty(value = "hive column type")
    private String type;

    @ApiModelProperty(value = "hive column comment")
    private String comment;

    @ApiModelProperty(value = "hive column create time")
    private Date createTime;

    @ApiModelProperty(value = "hive column update time")
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
