package org.metahut.starfish.api.dto;

import java.util.Map;

/**
 *
 */
public class EntityQueryDTO {

    private Long id;

    private String name;

    private String typeName;

    private Map<String,Object> queryCondition;

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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

