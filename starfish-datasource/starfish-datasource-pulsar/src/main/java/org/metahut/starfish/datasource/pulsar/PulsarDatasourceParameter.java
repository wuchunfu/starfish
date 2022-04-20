package org.metahut.starfish.datasource.pulsar;

import org.metahut.starfish.datasource.api.AbstractDatasourceParameter;

public class PulsarDatasourceParameter extends AbstractDatasourceParameter {

    private String serverUrl;

    @Override
    public boolean checkParameter() {
        return true;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
