package org.metahut.starfish.api.dto;

import java.util.List;

/**
 *
 */
public class PulsarTopicResponseDTO {

    private String name;

    private Long storageSize;

    private Long backlogSize;

    private PulsarNamespaceResponseDTO namespace;

    private List<PulsarSchemaResponseDTO> schemas;

    private List<PulsarPublisherResponseDTO> publishers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(Long storageSize) {
        this.storageSize = storageSize;
    }

    public Long getBacklogSize() {
        return backlogSize;
    }

    public void setBacklogSize(Long backlogSize) {
        this.backlogSize = backlogSize;
    }

    public PulsarNamespaceResponseDTO getNamespace() {
        return namespace;
    }

    public void setNamespace(PulsarNamespaceResponseDTO namespace) {
        this.namespace = namespace;
    }

    public List<PulsarSchemaResponseDTO> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<PulsarSchemaResponseDTO> schemas) {
        this.schemas = schemas;
    }

    public List<PulsarPublisherResponseDTO> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<PulsarPublisherResponseDTO> publishers) {
        this.publishers = publishers;
    }
}
