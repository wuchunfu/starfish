package org.metahut.starfish.unit;

import java.util.Map;

/**
 *
 */
public class IdTypeNameQueryCondition extends AbstractQueryCondition<Map> {

    private Long id;

    private String typeName;

    private String qualifiedName;

    public IdTypeNameQueryCondition() {
        super.setResultType(Map.class);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }
}
