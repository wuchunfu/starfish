package org.metahut.starfish.ingestion.common;

import org.metahut.starfish.message.api.MessageProperties;

import java.text.MessageFormat;

public class IngestionProperties {

    private RestProperties rest;
    private MessageProperties message;

    public static class RestProperties {
        private String serviceAddress;
        private String queryMetadataApi;
        private String token;

        public String getServiceAddress() {
            return serviceAddress;
        }

        public void setServiceAddress(String serviceAddress) {
            this.serviceAddress = serviceAddress;
        }

        public String getQueryMetadataApi() {
            return MessageFormat.format(queryMetadataApi, serviceAddress);
        }

        public void setQueryMetadataApi(String queryMetadataApi) {
            this.queryMetadataApi = queryMetadataApi;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public RestProperties getRest() {
        return rest;
    }

    public void setRest(RestProperties rest) {
        this.rest = rest;
    }

    public MessageProperties getMessage() {
        return message;
    }

    public void setMessage(MessageProperties message) {
        this.message = message;
    }
}
