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

package org.metahut.starfish.message.api;

import org.springframework.lang.Nullable;

public class ConsumerResult extends MessageResult {

    private String key;
    private String value;

    public static ConsumerResult of(String topic, String offset, @Nullable String partition) {
        return new ConsumerResult(topic, offset, partition, null, null);
    }

    public static ConsumerResult of(String topic, String offset, @Nullable String partition, @Nullable String key, String value) {
        return new ConsumerResult(topic, offset, partition, key, value);
    }

    public ConsumerResult(String topic, String offset, String partition, String key, String value) {
        super(topic, offset, partition);
        this.key = key;
        this.value = value;
    }

    public ConsumerResult() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
