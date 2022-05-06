package org.metahut.starfish.api.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenhao 2022/4/29
 */

@ApiModel(description  = "datasource response dto")
public class DatasourceDataResponseDTO {

    //datasource id
    @ApiModelProperty(value = "datasource id",required = true)
    private Long datasourceId;
    //datasouce catagory name
    @ApiModelProperty(value = "datasource name",required = true)
    private String name;
    //datasource paramter instance
    @ApiModelProperty(value = "datasource parameter to connect",required = true)
    private String paramter;

    @ApiModelProperty(value = "datasource description", required = true)
    private String description;
    @ApiModelProperty(value = "datasource type", required = true)
    private String type;

    public Long getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(Long datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParamter() {
        return paramter;
    }

    public void setParamter(String paramter) {
        this.paramter = paramter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
