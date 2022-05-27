package org.metahut.starfish.ingestion.common.data;

import org.metahut.starfish.unit.row.RelationRow;

import java.util.ArrayList;
import java.util.Collection;

public class RowData {

    private Collection<EntityRow> entities = new ArrayList<>();

    private Collection<RelationRow> relations = new ArrayList<>();

    public RowData() {
    }

    public RowData(Collection<EntityRow> entities, Collection<RelationRow> relations) {
        this.entities = entities;
        this.relations = relations;
    }

    public static RowData of(Collection<EntityRow> entities, Collection<RelationRow> relations) {
        return new RowData(entities, relations);
    }

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
