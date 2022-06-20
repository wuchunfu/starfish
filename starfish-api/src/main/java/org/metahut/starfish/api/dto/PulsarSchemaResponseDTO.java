package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 *
 */
@ApiModel(description = "pulsar schema response dto")
public class PulsarSchemaResponseDTO {

    @ApiModelProperty(value = "pulsar schema id")
    private Long id;

    @ApiModelProperty(value = "pulsar schema name")
    private String name;

    @ApiModelProperty(value = "pulsar schema type")
    private String type;

    @ApiModelProperty(value = "pulsar schema definition")
    private Object definition;

    @ApiModelProperty(value = "pulsar schema body")
    private Object schema;

    @ApiModelProperty(value = "pulsar schema create time")
    private Date createTime;

    @ApiModelProperty(value = "pulsar schema update time")
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

    public Object getDefinition() {
        return definition;
    }

    public void setDefinition(Object definition) {
        this.definition = definition;
    }

    public Object getSchema() {
        return schema;
    }

    public void setSchema(Object schema) {
        this.schema = schema;
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
