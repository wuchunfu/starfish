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

import org.metahut.starfish.unit.row.RelationRow;

import java.util.ArrayList;
import java.util.Collection;

public class RowData {

    private Collection<EntityRow> entities = new ArrayList<>();

    private Collection<RelationRow> relations = new ArrayList<>();

    public RowData() {
    }

    public RowData(Collection<EntityRow> entities, Collection<RelationRow> relations) {
        this.entities = entities;
        this.relations = relations;
    }

    public static RowData of(Collection<EntityRow> entities, Collection<RelationRow> relations) {
        return new RowData(entities, relations);
    }

    public Collection<EntityRow> getEntities() {
        return entities;
    }

    public void setEntities(Collection<EntityRow> entities) {
        this.entities = entities;
    }

    public Collection<RelationRow> getRelations() {
        return relations;
    }

    public void setRelations(Collection<RelationRow> relations) {
        this.relations = relations;
    }
}
