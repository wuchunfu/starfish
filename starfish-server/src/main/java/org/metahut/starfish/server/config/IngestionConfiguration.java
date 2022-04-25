package org.metahut.starfish.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;

@Configuration
@ConfigurationProperties(prefix = "starfish.ingestion")
public class IngestionConfiguration {

    private String serviceAddress;
    private String collectorExecuteRest;

    // TODO Missing handling of multiple addresses
    public String getServiceAddress() {
        return serviceAddress;
    }

    public String getCollectorExecuteRest() {
        return MessageFormat.format(collectorExecuteRest, getServiceAddress());
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public void setCollectorExecuteRest(String collectorExecuteRest) {
        this.collectorExecuteRest = collectorExecuteRest;
    }
}
