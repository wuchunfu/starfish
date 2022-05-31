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

package org.metahut.starfish.ingestion.collector.hive;

public class Constants {

    private Constants() {
    }

    public static final String COLLECTOR_TYPE = "Hive";
    public static final String TYPE_NAME_CLUSTER = "org.starfish.HiveCluster";
    public static final String TYPE_NAME_DB = "org.starfish.HiveDB";
    public static final String TYPE_NAME_TABLE = "org.starfish.HiveTable";
    public static final String TYPE_NAME_COLUMN = "org.starfish.HiveColumn";

    public static final String RELATION_PROPERTY_CLUSTER_DB = "dbs";
    public static final String RELATION_PROPERTY_DB_CLUSTER = "cluster";

    public static final String RELATION_PROPERTY_DB_TABLE = "tables";

    public static final String RELATION_PROPERTY_TABLE_DB = "db";

    public static final String RELATION_PROPERTY_TABLE_COLUMN = "columns";

    public static final String RELATION_PROPERTY_TABLE_PARTITION_KEY = "partitionKeys";

    public static final String RELATION_PROPERTY_COLUMN_TABLE = "table";

    public static final String PROPERTY_TABLE_COMMENT = "comment";


}
