package org.metahut.starfish.unit.row;

public class EntityHeader {

    private String typeName;
    private Long id;
    private String qualifiedName;

    public EntityHeader() {
    }

    public EntityHeader(String typeName, Long id, String qualifiedName) {
        this.typeName = typeName;
        this.id = id;
        this.qualifiedName = qualifiedName;
    }

    public static EntityHeader of(String typeName, String qualifiedName) {
        return new EntityHeader(typeName, null, qualifiedName);
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }
}
