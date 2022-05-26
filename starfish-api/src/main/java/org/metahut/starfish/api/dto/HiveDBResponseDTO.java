package org.metahut.starfish.api.dto;

/**
 *
 */
public class HiveDBResponseDTO {
    private String name;
    private String description;
    private String owner;
    private String location;
    private String parameters;
    private HiveClusterResponseDTO cluster;

    public HiveClusterResponseDTO getCluster() {
        return cluster;
    }

    public void setCluster(HiveClusterResponseDTO cluster) {
        this.cluster = cluster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}
