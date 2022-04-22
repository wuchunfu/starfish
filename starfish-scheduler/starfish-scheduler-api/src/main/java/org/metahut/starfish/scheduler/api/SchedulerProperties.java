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
