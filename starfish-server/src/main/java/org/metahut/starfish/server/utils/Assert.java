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

package org.metahut.starfish.server.utils;

import org.metahut.starfish.api.enums.Status;
import org.metahut.starfish.api.exception.BusinessException;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

public class Assert {

    private Assert() {

    }

    public static void notNull(@Nullable Object object, Status status, @Nullable Object... args) {
        if (object == null) {
            throw new BusinessException(status, args);
        }
    }

    public static void notTrue(@Nullable Boolean object, Status status, @Nullable Object... args) {
        if (object == null || !object) {
            throw new BusinessException(status, args);
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, Status status, @Nullable Object... args) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(status, args);
        }
    }

    public static void throwException(Throwable cause, Status status, @Nullable Object... args) {
        throw new BusinessException(cause, status, args);
    }
}
