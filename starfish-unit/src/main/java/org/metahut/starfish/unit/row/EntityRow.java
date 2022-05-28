package org.metahut.starfish.unit.row;

import org.metahut.starfish.unit.enums.RowKind;

import java.util.Map;

public class EntityRow<T> {

    private RowKind rowKind;

    private EntityHeader header;
    private Map<String,T> properties;

    public EntityRow() {
    }

    public EntityRow(RowKind rowKind, EntityHeader header, Map<String,T> properties) {
        this.rowKind = rowKind;
        this.header = header;
        this.properties = properties;
    }

    public static <T> EntityRow<T> of(RowKind rowKind, EntityHeader header, Map<String,T> properties) {
        return new EntityRow<>(rowKind, header, properties);
    }

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

    public Map<String,T> getProperties() {
        return properties;
    }

    public void setProperties(Map properties) {
        this.properties = properties;
    }
}
