package org.metahut.starfish.api.dto;

/**
 *
 */
public class SourceResponseDTO {

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
