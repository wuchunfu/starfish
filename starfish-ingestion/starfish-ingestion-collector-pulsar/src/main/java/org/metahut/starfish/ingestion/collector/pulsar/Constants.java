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

package org.metahut.starfish.ingestion.collector.pulsar;

public class Constants {

    private Constants() {
    }

    public static final String COLLECTOR_TYPE = "Pulsar";
    public static final String TYPE_NAME_CLUSTER = "org.starfish.PulsarCluster";
    public static final String TYPE_NAME_TENANT = "org.starfish.PulsarTenant";
    public static final String TYPE_NAME_NAMESPACE = "org.starfish.PulsarNamespace";
    public static final String TYPE_NAME_TOPIC = "org.starfish.PulsarTopic";
    public static final String TYPE_NAME_SCHEMA = "org.starfish.PulsarSchema";
    public static final String TYPE_NAME_PUBLISHER = "org.starfish.PulsarPublisher";

    public static final String RELATION_PROPERTY_CLUSTER_TENANT = "allowedTenants";
    public static final String RELATION_PROPERTY_TENANT_CLUSTER = "allowedClusters";
    public static final String RELATION_PROPERTY_TENANT_NAMESPACE = "namespaces";
    public static final String RELATION_PROPERTY_NAMESPACE_TENANT = "tenant";
    public static final String RELATION_PROPERTY_NAMESPACE_TOPIC = "topics";
    public static final String RELATION_PROPERTY_TOPIC_NAMESPACE = "namespace";
    public static final String RELATION_PROPERTY_TOPIC_SCHEMA = "schemas";
    public static final String RELATION_PROPERTY_SCHEMA_TOPIC = "topic";
    public static final String RELATION_PROPERTY_PUBLISHER_TOPIC = "topic";
    public static final String RELATION_PROPERTY_TOPIC_PUBLISHER = "publishers";

    public static final String PULSAR_DATA_PREFIX = "^data/.*";


}
