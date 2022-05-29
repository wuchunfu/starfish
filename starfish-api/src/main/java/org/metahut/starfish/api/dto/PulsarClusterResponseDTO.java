package org.metahut.starfish.api.dto;

import java.util.List;

/**
 *
 */
public class PulsarClusterResponseDTO {

    private String name;

    private String serviceUrl;

    private String listenerName;

    private String description;

    private List<PulsarTenantResponseDTO> allowedTenants;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getListenerName() {
        return listenerName;
    }

    public void setListenerName(String listenerName) {
        this.listenerName = listenerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PulsarTenantResponseDTO> getAllowedTenants() {
        return allowedTenants;
    }

    public void setAllowedTenants(List<PulsarTenantResponseDTO> allowedTenants) {
        this.allowedTenants = allowedTenants;
    }
}
