package org.metahut.starfish.unit.row;

import java.util.Collection;

public class RowData {

    private Collection<EntityRow> entities;

    private Collection<RelationRow> relations;

    public Collection<EntityRow> getEntities() {
        return entities;
    }

    public void setEntities(Collection<EntityRow> entities) {
        this.entities = entities;
    }

    public Collection<RelationRow> getRelations() {
        return relations;
    }

    public void setRelations(Collection<RelationRow> relations) {
        this.relations = relations;
    }
}
