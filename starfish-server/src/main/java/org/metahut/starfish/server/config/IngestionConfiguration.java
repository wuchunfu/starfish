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
