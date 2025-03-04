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

package org.metahut.starfish.scheduler.dolphinscheduler.parameter;

import java.util.List;

/**
 * http parameter
 */
public class HttpParameter {
    /**
     * url
     */
    private String url;

    /**
     * httpMethod
     */
    private String httpMethod;

    /**
     *  http params
     */
    private List<HttpProperty> httpParams;

    /**
     * condition
     */
    private String condition;

    /**
     * Connect Timeout
     * Unit: ms
     */
    private int connectTimeout;

    /**
     * Socket Timeout
     * Unit: ms
     */
    private int socketTimeout;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public List<HttpProperty> getHttpParams() {
        return httpParams;
    }

    public void setHttpParams(List<HttpProperty> httpParams) {
        this.httpParams = httpParams;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}
