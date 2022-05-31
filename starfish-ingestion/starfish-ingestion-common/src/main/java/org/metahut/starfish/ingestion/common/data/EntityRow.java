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

package org.metahut.starfish.ingestion.common.data;

import org.metahut.starfish.unit.enums.RowKind;
import org.metahut.starfish.unit.row.EntityHeader;

public class EntityRow<T> {

    private RowKind rowKind;

    private EntityHeader header;
    private T properties;

    public EntityRow() {
    }

    public EntityRow(RowKind rowKind, EntityHeader header, T properties) {
        this.rowKind = rowKind;
        this.header = header;
        this.properties = properties;
    }

    public static <T> EntityRow<T> of(RowKind rowKind, EntityHeader header, T properties) {
        return new EntityRow<>(rowKind, header, properties);
    }

    public RowKind getRowKind() {
        return rowKind;
    }

    public void setRowKind(RowKind rowKind) {
        this.rowKind = rowKind;
    }

    public EntityHeader getHeader() {
        return header;
    }

    public void setHeader(EntityHeader header) {
        this.header = header;
    }

    public T getProperties() {
        return properties;
    }

    public void setProperties(T properties) {
        this.properties = properties;
    }
}
