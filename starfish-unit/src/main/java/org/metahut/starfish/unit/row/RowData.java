package org.metahut.starfish.unit.row;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class RowData<T> {

    private Collection<EntityRow<Map<String,T>>> entities;

    private Collection<RelationRow> relations;

    public Collection<EntityRow<Map<String,T>>> getEntities() {
        return entities;
    }

    public void setEntities(Collection<EntityRow<Map<String,T>>> entities) {
        this.entities = entities;
    }

    public Collection<RelationRow> getRelations() {
        return relations;
    }

    public void setRelations(Collection<RelationRow> relations) {
        this.relations = relations;
    }
}
