package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;

import org.apache.commons.lang3.StringUtils;

public class PulsarCollectorAdapterParameter extends AbstractCollectorParameter {

    private String serverUrl;
    @Override
    public boolean checkParameter() {
        return StringUtils.isNotBlank(serverUrl);
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
