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

package org.metahut.starfish.ingestion.server.dto;

public class ResultEntity<T> {

    private static final Integer SUCCESS = 200;

    private Integer code;

    private String message;

    private T data;

    public ResultEntity() {

    }

    private ResultEntity(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultEntity(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> ResultEntity<T> of (Integer code, String message) {
        return new ResultEntity<>(code, message);
    }

    public static <T> ResultEntity<T> success(T data) {
        return new ResultEntity<>(SUCCESS, data);
    }

    public static <T> ResultEntity<T> success() {
        return success(null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
