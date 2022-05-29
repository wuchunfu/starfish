package org.metahut.starfish.api.dto;

import java.util.List;

/**
 *
 */
public class PulsarTenantResponseDTO {

    private String name;

    private List<PulsarClusterResponseDTO> allowedClusters;

    private List<PulsarNamespaceResponseDTO> namespaces;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PulsarClusterResponseDTO> getAllowedClusters() {
        return allowedClusters;
    }

    public void setAllowedClusters(List<PulsarClusterResponseDTO> allowedClusters) {
        this.allowedClusters = allowedClusters;
    }

    public List<PulsarNamespaceResponseDTO> getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(List<PulsarNamespaceResponseDTO> namespaces) {
        this.namespaces = namespaces;
    }
}
