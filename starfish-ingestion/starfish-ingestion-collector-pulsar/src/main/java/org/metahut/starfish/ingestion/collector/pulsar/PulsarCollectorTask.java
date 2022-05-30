package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollectorTask;
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

public class PulsarCollectorTask implements ICollectorTask {

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
        generatePulsarTenantAndNamespaceEntities();

        CollectorResult collectorResult = new CollectorResult();
        collectorResult.setMessage("get metaData is success");
        collectorResult.setState(true);
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
        try {
            List<String> clusterNames = clusters.getClusters();

            if (CollectionUtils.isEmpty(clusterNames)) {
                return;
            }

            RowData rowData = new RowData();
            for (String clusterName : clusterNames) {
                ClusterData cluster = clusters.getCluster(clusterName);
                EntityHeader entityHeader = generatePulsarClusterEntity(rowData, clusterName, cluster);
                clusterMap.put(clusterName, entityHeader);
            }
            sendMessage(rowData);

        } catch (PulsarAdminException e) {
            throw new RuntimeException(e);
        }

    }

    private void generatePulsarTenantAndNamespaceEntities() {
        try {
            Tenants tenants = pulsarAdmin.tenants();

            List<String> tenantNames = tenants.getTenants();
            if (CollectionUtils.isEmpty(tenantNames)) {
                return;
            }
            Namespaces namespaces = pulsarAdmin.namespaces();

            for (String tenantName : tenantNames) {
                TenantInfo tenantInfo = tenants.getTenantInfo(tenantName);
                EntityHeader tenantHeader = generatePulsarTenantEntity(tenantName, tenantInfo);

                List<String> namespaceNames = namespaces.getNamespaces(tenantName);
                if (CollectionUtils.isEmpty(namespaceNames)) {
                    continue;
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
        } catch (PulsarAdminException e) {
            throw new RuntimeException(e);
        }
    }

    private EntityHeader generatePulsarClusterEntity(RowData rowData, String clusterName, ClusterData clusterData) {
        LOGGER.info("generate pulsar cluster entity: {}", clusterName);
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

    private EntityHeader generatePulsarTenantEntity(String tenantName, TenantInfo tenantInfo) {
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

    private EntityHeader generatePulsarNamespaceEntity(EntityHeader tenantHeader, String namespaceName, Namespaces namespaces) {
        PulsarNamespace pulsarNamespace = new PulsarNamespace();
        pulsarNamespace.setName(namespaceName);
        try {
            pulsarNamespace.setMessageTTL(namespaces.getNamespaceMessageTTL(namespaceName));

            EntityHeader entityHeader = generateNamespaceEntityHeader(tenantHeader, namespaceName);

            RowData rowData = new RowData();
            rowData.getEntities().add(EntityRow.of(RowKind.UPSERT, entityHeader, pulsarNamespace));

            // PulsarNamespace --> tenant --> PulsarTenant
            rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, entityHeader, tenantHeader, RELATION_PROPERTY_NAMESPACE_TENANT));

            sendMessage(rowData);

            return entityHeader;
        } catch (PulsarAdminException e) {
            throw new RuntimeException(e);
        }

    }

    private void generatePulsarTopicEntities(EntityHeader namespaceHeader, String namespaceName) {
        Topics topics = pulsarAdmin.topics();
        try {
            List<String> topicNames = topics.getList(namespaceName);
            if (CollectionUtils.isEmpty(topicNames)) {
                return;
            }

            RowData rowData = new RowData();
            for (String topicName : topicNames) {
                EntityHeader entityHeader = generatePulsarTopicEntity(namespaceHeader, topicName);

                // PulsarNamespace --> topics --> PulsarTopic
                rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, namespaceHeader, entityHeader, RELATION_PROPERTY_NAMESPACE_TOPIC));
            }
            sendMessage(rowData);

        } catch (PulsarAdminException e) {
            throw new RuntimeException(e);
        }

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
        Topics topics = pulsarAdmin.topics();
        RowData rowData = new RowData();
        try {
            TopicStats stats = topics.getStats(topicName);
            stats.getBacklogSize();
            PulsarTopic pulsarTopic = new PulsarTopic();
            pulsarTopic.setName(topicName);
            pulsarTopic.setStorageSize(stats.getStorageSize());
            pulsarTopic.setBacklogSize(stats.getBacklogSize());

            EntityHeader topicHeader = generateTopicEntityHeader(namespaceHeader, topicName);
            rowData.getEntities().add(EntityRow.of(RowKind.UPSERT, topicHeader, pulsarTopic));

            // PulsarTopic --> namespace --> PulsarNamespace
            rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, topicHeader, namespaceHeader, RELATION_PROPERTY_TOPIC_NAMESPACE));

            List<? extends PublisherStats> publishers = stats.getPublishers();
            for (PublisherStats publisher : publishers) {
                EntityHeader publisherHeader = generatePulsarPublisherEntity(rowData, topicHeader, publisher);

                // PulsarTopic --> publishers --> PulsarPublisher
                rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, topicHeader, publisherHeader, RELATION_PROPERTY_TOPIC_PUBLISHER));
            }

            EntityHeader schemaHeader = generatePulsarSchemaEntity(rowData, topicHeader, topicName);

            // PulsarTopic --> schemas --> PulsarSchema
            rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, topicHeader, schemaHeader, RELATION_PROPERTY_TOPIC_SCHEMA));

            sendMessage(rowData);

            return topicHeader;
        } catch (PulsarAdminException e) {
            throw new RuntimeException(e);
        }

    }

    private EntityHeader generatePulsarPublisherEntity(RowData rowData, EntityHeader topicHeader, PublisherStats publisher) {

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
        String name = StringUtils.isBlank(pulsarPublisher.getProducerName()) ? String.valueOf(pulsarPublisher.getProducerId()) : pulsarPublisher.getProducerName();
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
        Schemas schemas = pulsarAdmin.schemas();
        try {
            SchemaInfo schemaInfo = schemas.getSchemaInfo(topicName);
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
        } catch (PulsarAdminException e) {
            throw new RuntimeException(e);
        }

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
