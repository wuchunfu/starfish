package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 */
@ApiModel(description = "pulsar cluster query dto")
public class PulsarClusterQueryDTO {

    @ApiModelProperty(value = "pulsar cluster name")
    private String name;

    @ApiModelProperty(value = "pulsar cluster service url")
    private String serviceUrl;

    @ApiModelProperty(value = "pulsar cluster service url tls")
    private String serviceUrlTls;

    @ApiModelProperty(value = "pulsar cluster broker service url")
    private String brokerServiceUrl;

    @ApiModelProperty(value = "pulsar cluster broker service url tls")
    private String brokerServiceUrlTls;

    @ApiModelProperty(value = "pulsar cluster proxy service url")
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
