package org.metahut.starfish.unit;

import java.util.Map;

/**
 *
 */
public class TypeNameQueryCondition extends AbstractQueryCondition<Map> {
    private String typeName;

    public TypeNameQueryCondition() {
        super.setResultType(Map.class);
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
