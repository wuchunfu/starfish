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

package org.metahut.starfish.api.exception;

import org.metahut.starfish.api.enums.Status;

import org.springframework.lang.Nullable;

public class BusinessException extends RuntimeException {

    private Integer code;

    private Object[] args;

    public BusinessException(Status status, @Nullable Object... args) {
        super(status.getMessage());
        this.code = status.getCode();
        this.args = args;
    }

    public BusinessException(Throwable cause, Status status) {
        super(status.getMessage(), cause);
        this.code = status.getCode();
    }

    public BusinessException(Throwable cause, Status status, @Nullable Object... args) {
        super(status.getMessage(), cause);
        this.code = status.getCode();
        this.args = args;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
