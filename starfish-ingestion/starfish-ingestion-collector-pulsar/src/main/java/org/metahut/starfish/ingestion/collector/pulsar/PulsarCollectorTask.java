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

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorTask;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.pulsar.models.PulsarCluster;
import org.metahut.starfish.ingestion.collector.pulsar.models.PulsarNamespace;
import org.metahut.starfish.ingestion.collector.pulsar.models.PulsarPublisher;
import org.metahut.starfish.ingestion.collector.pulsar.models.PulsarSchema;
import org.metahut.starfish.ingestion.collector.pulsar.models.PulsarTenant;
import org.metahut.starfish.ingestion.collector.pulsar.models.PulsarTopic;
import org.metahut.starfish.ingestion.common.JSONUtils;
import org.metahut.starfish.ingestion.common.MetaClient;
import org.metahut.starfish.ingestion.common.data.EntityRow;
import org.metahut.starfish.ingestion.common.data.RowData;
import org.metahut.starfish.message.api.IMessageProducer;
import org.metahut.starfish.unit.enums.RowKind;
import org.metahut.starfish.unit.row.EntityHeader;
import org.metahut.starfish.unit.row.RelationRow;

import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.client.admin.Clusters;
import org.apache.pulsar.client.admin.Namespaces;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.admin.Schemas;
import org.apache.pulsar.client.admin.Tenants;
import org.apache.pulsar.client.admin.Topics;
import org.apache.pulsar.common.policies.data.ClusterData;
import org.apache.pulsar.common.policies.data.PublisherStats;
import org.apache.pulsar.common.policies.data.TenantInfo;
import org.apache.pulsar.common.policies.data.TopicStats;
import org.apache.pulsar.common.schema.SchemaInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static org.metahut.starfish.ingestion.collector.pulsar.Constants.COLLECTOR_TYPE;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.RELATION_PROPERTY_CLUSTER_TENANT;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.RELATION_PROPERTY_NAMESPACE_TENANT;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.RELATION_PROPERTY_NAMESPACE_TOPIC;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.RELATION_PROPERTY_PUBLISHER_TOPIC;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.RELATION_PROPERTY_SCHEMA_TOPIC;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.RELATION_PROPERTY_TENANT_CLUSTER;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.RELATION_PROPERTY_TENANT_NAMESPACE;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.RELATION_PROPERTY_TOPIC_NAMESPACE;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.RELATION_PROPERTY_TOPIC_PUBLISHER;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.RELATION_PROPERTY_TOPIC_SCHEMA;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.TYPE_NAME_CLUSTER;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.TYPE_NAME_NAMESPACE;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.TYPE_NAME_PUBLISHER;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.TYPE_NAME_SCHEMA;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.TYPE_NAME_TENANT;
import static org.metahut.starfish.ingestion.collector.pulsar.Constants.TYPE_NAME_TOPIC;
import static org.metahut.starfish.unit.EntityNameGentrator.generateName;

public class PulsarCollectorTask extends AbstractCollectorTask {

    private static Logger LOGGER = LoggerFactory.getLogger(PulsarCollectorTask.class);

    private final PulsarCollectorAdapter adapter;
    private final IMessageProducer producer;
    private final PulsarAdmin pulsarAdmin;
    private final PulsarCollectorTaskParameter parameter;

    public PulsarCollectorTask(PulsarCollectorAdapter adapter, PulsarCollectorTaskParameter parameter) {
        this.adapter = adapter;
        this.producer = MetaClient.getInstance().getMessageProducer();
        this.pulsarAdmin = this.adapter.getMetaClient();
        this.parameter = parameter;
    }

    private final Map<String, EntityHeader> clusterMap = new HashMap<>();

    @Override
    public CollectorResult execute() {

        generatePulsarClusterEntities();
        generatePulsarTenantEntities();

        CollectorResult collectorResult = new CollectorResult();
        collectorResult.setMessage(getMessage());
        collectorResult.setState(true);
        LOGGER.info("pulsar collector task execution ends ... ");
        return collectorResult;
    }

    private void sendMessage(String key, Object value) {
        producer.send(key, JSONUtils.toJSONString(value));
    }

    private void sendMessage(Object value) {
        sendMessage(COLLECTOR_TYPE, value);
    }

    private void deleteNonExistentMetadata() {
        // TODO Query the existing metadata in the system and call the Hive interface to determine whether it exists

    }

    private void generatePulsarClusterEntities() {
        Clusters clusters = pulsarAdmin.clusters();
        List<String> clusterNames = Collections.emptyList();
        try {
            clusterNames = clusters.getClusters();
        } catch (PulsarAdminException e) {
            isThrowException("Pulsar query all cluster names exception", e, parameter.isThrowException());
        }

        LOGGER.info("Pulsar query cluster names size:{}", clusterNames.size());
        if (CollectionUtils.isEmpty(clusterNames)) {
            return;
        }

        RowData rowData = new RowData();
        for (String clusterName : clusterNames) {
            EntityHeader entityHeader = generatePulsarClusterEntity(rowData, clusterName, clusters);
            if (Objects.isNull(entityHeader)) {
                continue;
            }
            clusterMap.put(clusterName, entityHeader);
        }
        sendMessage(rowData);
    }

    private void generatePulsarTenantEntities() {
        Tenants tenants = pulsarAdmin.tenants();
        List<String> tenantNames = Collections.emptyList();
        try {
            tenantNames = tenants.getTenants();
        } catch (PulsarAdminException e) {
            isThrowException("Pulsar query all tenant names exception", e, parameter.isThrowException());
        }

        LOGGER.info("Pulsar query tenant names size:{}", tenantNames.size());
        if (CollectionUtils.isEmpty(tenantNames)) {
            return;
        }

        for (String tenantName : tenantNames) {
            EntityHeader tenantHeader = generatePulsarTenantEntity(tenants, tenantName);
            if (Objects.isNull(tenantHeader)) {
                continue;
            }

            generatePulsarNamespaceEntities(tenantHeader, tenantName);
        }

    }

    private EntityHeader generatePulsarClusterEntity(RowData rowData, String clusterName, Clusters clusters) {
        LOGGER.info("Pulsar generate pulsar cluster entity: {}", clusterName);
        ClusterData clusterData = null;
        try {
            clusterData = clusters.getCluster(clusterName);
        } catch (PulsarAdminException e) {
            isThrowException(MessageFormat.format("Pulsar query cluster:{} info exception", clusterName), e, parameter.isThrowException());
        }

        if (Objects.isNull(clusterData)) {
            return null;
        }

        PulsarCluster pulsarCluster = new PulsarCluster();
        pulsarCluster.setName(clusterName);
        pulsarCluster.setType(COLLECTOR_TYPE);
        pulsarCluster.setListenerName(clusterData.getListenerName());
        pulsarCluster.setServiceUrl(clusterData.getServiceUrl());

        EntityHeader entityHeader = generateClusterEntityHeader(clusterName);

        // PulsarCluster entity
        rowData.getEntities().add(EntityRow.of(RowKind.UPSERT, entityHeader, pulsarCluster));

        return entityHeader;
    }

    private EntityHeader generatePulsarTenantEntity(Tenants tenants, String tenantName) {
        LOGGER.info("Pulsar generate pulsar tenant entity: {}", tenantName);
        TenantInfo tenantInfo = null;
        try {
            tenantInfo = tenants.getTenantInfo(tenantName);
        } catch (PulsarAdminException e) {
            isThrowException(MessageFormat.format("Pulsar query tenant:{} info exception", tenantName), e, parameter.isThrowException());
        }

        if (Objects.isNull(tenantInfo)) {
            return null;
        }

        PulsarTenant pulsarTenant = new PulsarTenant();
        pulsarTenant.setName(tenantName);
        EntityHeader entityHeader = generateTenantEntityHeader(tenantName);

        RowData rowData = new RowData();
        rowData.getEntities().add(EntityRow.of(RowKind.UPSERT, entityHeader, pulsarTenant));

        Set<String> allowedClusters = tenantInfo.getAllowedClusters();

        for (String clusterName : allowedClusters) {

            EntityHeader clusterHeader = clusterMap.get(clusterName);

            // PulsarTenant --> allowedClusters --> PulsarCluster
            rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, entityHeader, clusterHeader, RELATION_PROPERTY_TENANT_CLUSTER));

            // PulsarCluster --> allowedTenants --> PulsarTenant
            rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, clusterHeader, entityHeader, RELATION_PROPERTY_CLUSTER_TENANT));
        }

        sendMessage(rowData);
        return entityHeader;
    }

    private void generatePulsarNamespaceEntities(EntityHeader tenantHeader, String tenantName) {
        Namespaces namespaces = pulsarAdmin.namespaces();

        List<String> namespaceNames = Collections.emptyList();
        try {
            namespaceNames = namespaces.getNamespaces(tenantName);
        } catch (PulsarAdminException e) {
            isThrowException(MessageFormat.format("PulsarTenant:{0}, query all namespace names exception", tenantHeader.getQualifiedName()), e, parameter.isThrowException());
        }

        LOGGER.info("PulsarTenant:{}, query HiveNamespace size:{}", tenantHeader.getQualifiedName(), namespaceNames.size());
        if (CollectionUtils.isEmpty(namespaceNames)) {
            return;
        }

        RowData rowData = new RowData();
        for (String namespaceName : namespaceNames) {

            EntityHeader namespaceHeader = generatePulsarNamespaceEntity(tenantHeader, namespaceName, namespaces);

            // PulsarTenant  --> namespaces -->  PulsarNamespace
            rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, tenantHeader, namespaceHeader, RELATION_PROPERTY_TENANT_NAMESPACE));

            // generate pulsar topic
            generatePulsarTopicEntities(namespaceHeader, namespaceName);
        }
        sendMessage(rowData);
    }

    private EntityHeader generatePulsarNamespaceEntity(EntityHeader tenantHeader, String namespaceName, Namespaces namespaces) {
        LOGGER.info("PulsarTenant:{}, generate pulsar namespace entity: {}", tenantHeader.getQualifiedName(), namespaceName);
        PulsarNamespace pulsarNamespace = new PulsarNamespace();
        pulsarNamespace.setName(namespaceName);
        try {
            pulsarNamespace.setMessageTTL(namespaces.getNamespaceMessageTTL(namespaceName));
        } catch (PulsarAdminException e) {
            isThrowException(MessageFormat.format("PulsarTenant:{0}, query namespace info:{1} exception", tenantHeader.getQualifiedName(), namespaceName), e, parameter.isThrowException());
        }
        EntityHeader entityHeader = generateNamespaceEntityHeader(tenantHeader, namespaceName);

        RowData rowData = new RowData();
        rowData.getEntities().add(EntityRow.of(RowKind.UPSERT, entityHeader, pulsarNamespace));

        // PulsarNamespace --> tenant --> PulsarTenant
        rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, entityHeader, tenantHeader, RELATION_PROPERTY_NAMESPACE_TENANT));

        sendMessage(rowData);

        return entityHeader;
    }

    private void generatePulsarTopicEntities(EntityHeader namespaceHeader, String namespaceName) {
        Topics topics = pulsarAdmin.topics();
        List<String> topicNames = Collections.emptyList();
        try {
            topicNames = topics.getList(namespaceName);
        } catch (PulsarAdminException e) {
            isThrowException(MessageFormat.format("PulsarNamespace:{0}, query all topic names exception", namespaceHeader.getQualifiedName()), e, parameter.isThrowException());
        }
        LOGGER.info("PulsarNamespace:{}, query all pulsar topic size:{}", namespaceHeader.getQualifiedName(), topicNames.size());
        if (CollectionUtils.isEmpty(topicNames)) {
            return;
        }

        RowData rowData = new RowData();
        for (String topicName : topicNames) {
            EntityHeader entityHeader = generatePulsarTopicEntity(namespaceHeader, topicName);
            if (Objects.isNull(entityHeader)) {
                continue;
            }

            // PulsarNamespace --> topics --> PulsarTopic
            rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, namespaceHeader, entityHeader, RELATION_PROPERTY_NAMESPACE_TOPIC));
        }
        sendMessage(rowData);

    }

    /**
     * <p>
     *     https://pulsar.apache.org/docs/next/admin-api-topics#get-stats
     * </p>
     * @param namespaceHeader
     * @param topicName
     * @return
     */
    private EntityHeader generatePulsarTopicEntity(EntityHeader namespaceHeader, String topicName) {
        LOGGER.info("PulsarNamespace:{}, generate pulsar topic entity: {}", namespaceHeader.getQualifiedName(), topicName);
        Topics topics = pulsarAdmin.topics();
        TopicStats stats = null;
        try {
            stats = topics.getStats(topicName);
        } catch (PulsarAdminException e) {
            isThrowException(MessageFormat.format("PulsarNamespace:{0}, query topic stats info:{1} exception", namespaceHeader.getQualifiedName(), topicName), e, parameter.isThrowException());
        }

        if (Objects.isNull(stats)) {
            return null;
        }

        stats.getBacklogSize();
        PulsarTopic pulsarTopic = new PulsarTopic();
        pulsarTopic.setName(topicName);
        pulsarTopic.setStorageSize(stats.getStorageSize());
        pulsarTopic.setBacklogSize(stats.getBacklogSize());

        EntityHeader topicHeader = generateTopicEntityHeader(namespaceHeader, topicName);
        RowData rowData = new RowData();
        rowData.getEntities().add(EntityRow.of(RowKind.UPSERT, topicHeader, pulsarTopic));

        // PulsarTopic --> namespace --> PulsarNamespace
        rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, topicHeader, namespaceHeader, RELATION_PROPERTY_TOPIC_NAMESPACE));

        // generate pulsar publisher entities
        List<? extends PublisherStats> publishers = stats.getPublishers();
        for (PublisherStats publisher : publishers) {
            EntityHeader publisherHeader = generatePulsarPublisherEntity(rowData, topicHeader, publisher);

            // PulsarTopic --> publishers --> PulsarPublisher
            rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, topicHeader, publisherHeader, RELATION_PROPERTY_TOPIC_PUBLISHER));
        }

        // generate pulsar schema entity
        EntityHeader schemaHeader = generatePulsarSchemaEntity(rowData, topicHeader, topicName);
        if (Objects.nonNull(schemaHeader)) {
            // PulsarTopic --> schemas --> PulsarSchema
            rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, topicHeader, schemaHeader, RELATION_PROPERTY_TOPIC_SCHEMA));
        }

        sendMessage(rowData);

        return topicHeader;
    }

    private EntityHeader generatePulsarPublisherEntity(RowData rowData, EntityHeader topicHeader, PublisherStats publisher) {
        String name = StringUtils.isBlank(publisher.getProducerName()) ? String.valueOf(publisher.getProducerId()) : publisher.getProducerName();
        LOGGER.info("PulsarTopic:{}, generate pulsar publisher entity: {}", topicHeader.getQualifiedName(), name);

        PulsarPublisher pulsarPublisher = new PulsarPublisher();
        pulsarPublisher.setAccessMode(publisher.getAccessMode().name());
        pulsarPublisher.setMsgRateIn(publisher.getMsgRateIn());
        pulsarPublisher.setMsgThroughputIn(publisher.getMsgThroughputIn());
        pulsarPublisher.setAverageMsgSize(publisher.getAverageMsgSize());
        pulsarPublisher.setChunkedMessageRate(publisher.getChunkedMessageRate());
        pulsarPublisher.setProducerId(publisher.getProducerId());
        pulsarPublisher.setProducerName(publisher.getProducerName());
        pulsarPublisher.setAddress(publisher.getAddress());
        pulsarPublisher.setConnectedSince(publisher.getConnectedSince());
        pulsarPublisher.setClientVersion(publisher.getClientVersion());

        EntityHeader entityHeader = generatePublisherEntityHeader(topicHeader, name);

        rowData.getEntities().add(EntityRow.of(RowKind.UPSERT, entityHeader, pulsarPublisher));

        // PulsarPublisher --> topic --> PulsarTopic
        rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, entityHeader, topicHeader, RELATION_PROPERTY_PUBLISHER_TOPIC));
        return entityHeader;
    }

    /**
     * <p>
     *     https://pulsar.apache.org/docs/next/schema-manage#get-a-schema-latest
     * </p>
     * @param rowData rowData
     * @param topicHeader topicHeader
     * @param topicName topicName
     * @return
     */
    private EntityHeader generatePulsarSchemaEntity(RowData rowData, EntityHeader topicHeader, String topicName) {
        LOGGER.info("PulsarTopic:{}, generate pulsar schema entity", topicHeader.getQualifiedName());
        Schemas schemas = pulsarAdmin.schemas();
        SchemaInfo schemaInfo = null;
        try {
            schemaInfo = schemas.getSchemaInfo(topicName);
        } catch (PulsarAdminException e) {
            isThrowException(MessageFormat.format("PulsarTopic:{0}, pulsar admin client query schema exception", topicHeader.getQualifiedName()), e, parameter.isThrowException());
        }

        if (Objects.isNull(schemaInfo)) {
            return null;
        }

        PulsarSchema pulsarSchema = new PulsarSchema();
        pulsarSchema.setName(schemaInfo.getName());
        pulsarSchema.setType(schemaInfo.getType().name());
        pulsarSchema.setDefinition(schemaInfo.getSchemaDefinition());
        pulsarSchema.setSchema(new String(schemaInfo.getSchema()));

        EntityHeader entityHeader = generateSchemaEntityHeader(topicHeader, schemaInfo.getName());
        rowData.getEntities().add(EntityRow.of(RowKind.UPSERT, entityHeader, pulsarSchema));

        // PulsarSchema --> topic --> PulsarTopic
        rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, entityHeader, topicHeader, RELATION_PROPERTY_SCHEMA_TOPIC));

        return entityHeader;
    }

    private EntityHeader generateClusterEntityHeader(String clusterName) {
        return generateEntityHeader(TYPE_NAME_CLUSTER, TYPE_NAME_CLUSTER, clusterName);
    }

    private EntityHeader generateTenantEntityHeader(String tenantName) {
        return generateEntityHeader(TYPE_NAME_TENANT, TYPE_NAME_TENANT, tenantName);
    }

    private EntityHeader generateNamespaceEntityHeader(EntityHeader tenantHeader, String namespaceName) {
        return generateEntityHeader(TYPE_NAME_NAMESPACE, namespaceName);
    }

    private EntityHeader generateTopicEntityHeader(EntityHeader namespaceHeader, String topicName) {
        return generateEntityHeader(TYPE_NAME_TOPIC, topicName);
    }

    private EntityHeader generateSchemaEntityHeader(EntityHeader topicHeader, String schemaName) {
        return generateEntityHeader(TYPE_NAME_SCHEMA, topicHeader.getQualifiedName(), schemaName);
    }

    private EntityHeader generatePublisherEntityHeader(EntityHeader topicHeader, String name) {
        return generateEntityHeader(TYPE_NAME_PUBLISHER, topicHeader.getQualifiedName(), TYPE_NAME_PUBLISHER, name);

    }

    private EntityHeader generateEntityHeader(String typeName, String... args) {
        return EntityHeader.of(typeName, generateName(args));
    }

    @Override
    public void close() throws Exception {
        if (Objects.nonNull(producer)) {
            producer.close();
        }

        if (Objects.nonNull(adapter)) {
            adapter.close();
        }
    }
}
