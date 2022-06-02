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

package org.metahut.starfish.api.enums;

public enum Status {

    SUCCESS(200, "success"),
    UNKNOWN_EXCEPTION(10000, "UNKNOWN_EXCEPTION"),

    COLLECTOR_ADAPTER_TEST_CONNECTION_FAIL(20001, "COLLECTOR_ADAPTER_TEST_CONNECTION_FAIL"),
    COLLECTOR_ADAPTER_TYPE_DOES_NOT_EXIST(20002, "COLLECTOR_ADAPTER_TYPE_DOES_NOT_EXIST"),

    COLLECTOR_TASK_CREATE_SCHEDULE_FAIL(20003, "COLLECTOR_TASK_CREATE_SCHEDULE_FAIL"),
    INIT_TYPE_MODEL_TO_LOAD_FILE_FAIL(30001, "INIT_TYPE_MODEL_TO_LOAD_FILE_FAIL")
    ;

    private int code;

    private String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
