package org.metahut.starfish.api.dto;

/**
 *
 */
public class PulsarClusterResponseDTO {

    private String name;

    private String serviceUrl;

    private String serviceUrlTls;

    private String brokerServiceUrl;

    private String brokerServiceUrlTls;

    private String proxyServiceUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getServiceUrlTls() {
        return serviceUrlTls;
    }

    public void setServiceUrlTls(String serviceUrlTls) {
        this.serviceUrlTls = serviceUrlTls;
    }

    public String getBrokerServiceUrl() {
        return brokerServiceUrl;
    }

    public void setBrokerServiceUrl(String brokerServiceUrl) {
        this.brokerServiceUrl = brokerServiceUrl;
    }

    public String getBrokerServiceUrlTls() {
        return brokerServiceUrlTls;
    }

    public void setBrokerServiceUrlTls(String brokerServiceUrlTls) {
        this.brokerServiceUrlTls = brokerServiceUrlTls;
    }

    public String getProxyServiceUrl() {
        return proxyServiceUrl;
    }

    public void setProxyServiceUrl(String proxyServiceUrl) {
        this.proxyServiceUrl = proxyServiceUrl;
    }
}
