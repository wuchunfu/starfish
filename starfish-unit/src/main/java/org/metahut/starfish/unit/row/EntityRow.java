package org.metahut.starfish.unit.row;

import org.metahut.starfish.unit.enums.RowKind;

public class EntityRow<T> {

    private RowKind rowKind;

    private EntityHeader header;
    private T properties;

    public RowKind getRowKind() {
        return rowKind;
    }

    public void setRowKind(RowKind rowKind) {
        this.rowKind = rowKind;
    }

    public EntityHeader getHeader() {
        return header;
    }

    public void setHeader(EntityHeader header) {
        this.header = header;
    }

    public T getProperties() {
        return properties;
    }

    public void setProperties(T properties) {
        this.properties = properties;
    }
}
