package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "metaData version detail response dto")
public class MetaDataVersionResponseDTO extends PageResponseDTO {

    @ApiModelProperty(value = "metaData type")
    private String metaDataType;

    @ApiModelProperty(value = "metaData update logs")
    private String updateDetails;

    @ApiModelProperty(value = "metaData update time")
    private Date updateTime;

    @ApiModelProperty(value = " metaData version")
    private String version;

    @ApiModelProperty(value = "update metaData staff")
    private String operator;

    public String getMetaDataType() {
        return metaDataType;
    }

    public void setMetaDataType(String metaDataType) {
        this.metaDataType = metaDataType;
    }

    public String getUpdateDetails() {
        return updateDetails;
    }

    public void setUpdateDetails(String updateDetails) {
        this.updateDetails = updateDetails;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
