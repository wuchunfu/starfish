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

package org.metahut.starfish.api;

public class Constants {

    private Constants() {

    }

    public static final String COLLECTOR_ADAPTER_TYPE_NAME = "org.starfish.CollectorAdapter";
    public static final String COLLECTOR_TASK_TYPE_NAME = "org.starfish.CollectorTask";
    public static final String HIVE_CLUSTER_TYPE_NAME = "org.starfish.HiveCluster";
    public static final String HIVE_TABLE_TYPE_NAME = "org.starfish.HiveTable";
    public static final String HIVE_DB_TYPE_NAME = "org.starfish.HiveDB";
    public static final String PULSAR_CLUSTER_TYPE_NAME = "org.starfish.PulsarCluster";
    public static final String PULSAR_TOPIC_TYPE_NAME = "org.starfish.PulsarTopic";



    public static final String PULSAR_TENANT_TYPE_NAME = "org.starfish.PulsarTenant";
    public static final String PULSAR_NAMESPACE_TYPE_NAME = "org.starfish.PulsarNamespace";
    public static final String PULSAR_SCHEMA_TYPE_NAME = "org.starfish.PulsarSchema";
    public static final String PULSAR_PUBLISHER_TYPE_NAME = "org.starfish.PulsarPublisher";

    public static final String PROPERTY_COLLECTOR_TASK_ADAPTER_RELATION = "adapter";


}
