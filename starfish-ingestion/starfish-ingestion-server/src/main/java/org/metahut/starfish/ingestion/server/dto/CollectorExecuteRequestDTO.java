package org.metahut.starfish.ingestion.server.dto;

public class CollectorExecuteRequestDTO {

    private Long id;

    private String qualifiedName;

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
