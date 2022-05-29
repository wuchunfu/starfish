package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 */
@ApiModel(description = "hive cluster query dto")
public class HiveClusterQueryDTO {

    @ApiModelProperty(value = "hive cluster name")
    private String name;

    @ApiModelProperty(value = "hive cluster description")
    private String description;

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
}
