package org.metahut.starfish.api.dto;

import java.util.List;

/**
 *
 */
public class PulsarNamespaceResponseDTO {

    private String name;

    private List<PulsarTenantResponseDTO> allowedTenants;

    private Integer messageTTL;

    private List<PulsarTopicResponseDTO> topics;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PulsarTenantResponseDTO> getAllowedTenants() {
        return allowedTenants;
    }

    public void setAllowedTenants(List<PulsarTenantResponseDTO> allowedTenants) {
        this.allowedTenants = allowedTenants;
    }

    public Integer getMessageTTL() {
        return messageTTL;
    }

    public void setMessageTTL(Integer messageTTL) {
        this.messageTTL = messageTTL;
    }

    public List<PulsarTopicResponseDTO> getTopics() {
        return topics;
    }

    public void setTopics(List<PulsarTopicResponseDTO> topics) {
        this.topics = topics;
    }
}
