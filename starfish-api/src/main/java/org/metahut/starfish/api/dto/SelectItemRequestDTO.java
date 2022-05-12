package org.metahut.starfish.api.dto;

import org.metahut.starfish.api.enums.SelectItemNameEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

@ApiModel(value = "select item request dto")
public class SelectItemRequestDTO {

    @ApiModelProperty(value = "component names")
    private Set<SelectItemNameEnum> componentNames;

    public Set<SelectItemNameEnum> getComponentNames() {
        return componentNames;
    }

    public void setComponentNames(Set<SelectItemNameEnum> componentNames) {
        this.componentNames = componentNames;
    }
}
