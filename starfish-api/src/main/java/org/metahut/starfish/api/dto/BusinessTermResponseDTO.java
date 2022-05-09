package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class BusinessTermResponseDTO extends PageResponseDTO {
    @ApiModelProperty(value = "MetaData manager Business term name")
    private String name;

    @ApiModelProperty(value = "MetaData manager Business term describe")
    private String describe;

    @ApiModelProperty(value = "MetaData manager Business term creator erp account")
    private String creator;

    @ApiModelProperty(value = "MetaData manager Business term creator erp account")
    private Date creatorTime;

    @ApiModelProperty(value = "MetaData manager Business term creator erp account")
    private Date updateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
