package org.metahut.starfish.ingestion.collector.api;

public abstract class AbstractCollectorParameter {

    private String datasourceId;

    private String datasourceParameter;

    public abstract boolean checkParameter();

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
