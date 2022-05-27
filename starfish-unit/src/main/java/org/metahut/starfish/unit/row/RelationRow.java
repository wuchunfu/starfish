package org.metahut.starfish.unit.row;

import org.metahut.starfish.unit.enums.RowKind;

public class RelationRow {

    private RowKind rowKind;
    private EntityHeader startNode;

    private EntityHeader endNode;

    private String property;

    public RelationRow() {
    }

    public RelationRow(RowKind rowKind, EntityHeader startNode, EntityHeader endNode, String property) {
        this.rowKind = rowKind;
        this.startNode = startNode;
        this.endNode = endNode;
        this.property = property;
    }

    public static RelationRow of(RowKind rowKind, EntityHeader startNode, EntityHeader endNode, String property) {
        return new RelationRow(rowKind, startNode, endNode, property);
    }

    public RowKind getRowKind() {
        return rowKind;
    }

    public void setRowKind(RowKind rowKind) {
        this.rowKind = rowKind;
    }

    public EntityHeader getStartNode() {
        return startNode;
    }

    public void setStartNode(EntityHeader startNode) {
        this.startNode = startNode;
    }

    public EntityHeader getEndNode() {
        return endNode;
    }

    public void setEndNode(EntityHeader endNode) {
        this.endNode = endNode;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
