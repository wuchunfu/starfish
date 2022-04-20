package org.metahut.starfish.ingestion.collector.api;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
public abstract class AbstractCollectorParameter {

    private String type;

    private String datasourceId;

    private String datasourceParameter;

    public abstract boolean checkParameter();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getDatasourceParameter() {
        return datasourceParameter;
    }

    public void setDatasourceParameter(String datasourceParameter) {
        this.datasourceParameter = datasourceParameter;
    }
}
