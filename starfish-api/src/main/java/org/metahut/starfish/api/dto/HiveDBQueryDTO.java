package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 */
public class HiveDBQueryDTO extends PageRequestDTO {

    @ApiModelProperty(value = "hive db name")
    private String name;

    @ApiModelProperty(value = "hive db description")
    private String description;

    @ApiModelProperty(value = "hive db owner")
    private String owner;

    @ApiModelProperty(value = "hive db location")
    private String location;

    @ApiModelProperty(value = "hive db parameters")
    private String parameters;

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}
