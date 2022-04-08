package org.metahut.starfish.ingestion.server.dto;

public class CollectorExecuteDto {

    private String type;
    private String parameter;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
