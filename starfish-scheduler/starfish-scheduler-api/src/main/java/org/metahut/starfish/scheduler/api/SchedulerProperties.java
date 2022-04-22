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

package org.metahut.starfish.scheduler.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "starfish.scheduler")
public class SchedulerProperties {

    private SchedulerType type;
    private DolphinScheduler dolphinScheduler = new DolphinScheduler();

    public static class DolphinScheduler {
        private String serviceUrl;
        private String token;
        private String projectCode;
        private HttpClient httpClient;

        public String getServiceUrl() {
            return serviceUrl;
        }

        public void setServiceUrl(String serviceUrl) {
            this.serviceUrl = serviceUrl;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getProjectCode() {
            return projectCode;
        }

        public void setProjectCode(String projectCode) {
            this.projectCode = projectCode;
        }
    }

    public static class HttpClient {

    }

    public SchedulerType getType() {
        return type;
    }

    public void setType(SchedulerType type) {
        this.type = type;
    }

    public DolphinScheduler getDolphinScheduler() {
        return dolphinScheduler;
    }

    public void setDolphinScheduler(DolphinScheduler dolphinScheduler) {
        this.dolphinScheduler = dolphinScheduler;
    }
}
