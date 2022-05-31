/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
