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

    public static final String TYPE_NAME_COLLECTOR_ADAPTER = "org.starfish.CollectorAdapter";
    public static final String TYPE_NAME_COLLECTOR_TASK = "org.starfish.CollectorTask";
    public static final String TYPE_NAME_HIVE_CLUSTER = "org.starfish.HiveCluster";
    public static final String TYPE_NAME_HIVE_TABLE = "org.starfish.HiveTable";
    public static final String TYPE_NAME_HIVE_DB = "org.starfish.HiveDB";
    public static final String TYPE_NAME_PULSAR_CLUSTER = "org.starfish.PulsarCluster";

    public static final String TYPE_NAME_PULSAR_TENANT = "org.starfish.PulsarTenant";
    public static final String TYPE_NAME_PULSAR_NAMESPACE = "org.starfish.PulsarNamespace";
    public static final String TYPE_NAME_PULSAR_TOPIC = "org.starfish.PulsarTopic";
    public static final String TYPE_NAME_PULSAR_SCHEMA = "org.starfish.PulsarSchema";
    public static final String TYPE_NAME_PULSAR_PUBLISHER = "org.starfish.PulsarPublisher";

    public static final String RELATION_PROPERTY_COLLECTOR_TASK_ADAPTER = "adapter";


}
