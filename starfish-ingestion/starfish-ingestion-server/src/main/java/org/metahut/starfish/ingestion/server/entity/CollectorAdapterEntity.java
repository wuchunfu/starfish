package org.metahut.starfish.ingestion.server.entity;

import org.metahut.starfish.ingestion.server.utils.JSONUtils;

public class CollectorAdapterEntity {

    private Long id;

    private String name;

    private String type;

    private Object parameter;

    private String description;

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

    public String getParameter() {
        return JSONUtils.toJSONString(this.parameter);
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
