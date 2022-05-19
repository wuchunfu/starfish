package org.metahut.starfish.ingestion.collector.pulsar;

import org.apache.commons.lang3.StringUtils;
import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;

public class PulsarCollectorAdapterParameter extends AbstractCollectorParameter {

    private String serverUrl;
    @Override
    public boolean checkParameter() {
        return StringUtils.isBlank(serverUrl);
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
