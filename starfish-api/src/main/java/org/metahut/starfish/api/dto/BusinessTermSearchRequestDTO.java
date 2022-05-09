package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "MetaData manager Business term query request dto")
public class BusinessTermSearchRequestDTO extends PageRequestDTO {
    @ApiModelProperty(value = "MetaData manager Business term name")
    String businessTermName;

    public String getBusinessTermName() {
        return businessTermName;
    }

    public void setBusinessTermName(String businessTermName) {
        this.businessTermName = businessTermName;
    }
}
