package org.metahut.starfish.ingestion.server.dto;

public class CollectorExecuteRequestDTO {

    private String name;

    private Long adapterId;

    private String parameter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAdapterId() {
        return adapterId;
    }

    public void setAdapterId(Long adapterId) {
        this.adapterId = adapterId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
