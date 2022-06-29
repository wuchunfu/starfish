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

package org.metahut.starfish.ingestion.collector.api;

public class CollectorResult {

    private boolean state;

    private String message;

    public CollectorResult() {
    }

    public CollectorResult(boolean state) {
        this.state = state;
    }

    private CollectorResult(boolean state, String message) {
        this.state = state;
        this.message = message;
    }

    public static CollectorResult success() {
        return new CollectorResult(true);
    }

    public static CollectorResult error(String message) {
        return new CollectorResult(false, message);
    }

    public static CollectorResult of(boolean state, String message) {
        return new CollectorResult(state, message);
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
