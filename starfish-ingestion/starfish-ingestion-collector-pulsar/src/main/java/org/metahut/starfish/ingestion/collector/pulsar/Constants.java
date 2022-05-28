package org.metahut.starfish.ingestion.collector.pulsar;

public class Constants {

    private Constants() {
    }

    public static final String COLLECTOR_TYPE = "Pulsar";
    public static final String TYPE_NAME_CLUSTER = "PulsarCluster";
    public static final String TYPE_NAME_TENANT = "PulsarTenant";
    public static final String TYPE_NAME_NAMESPACE = "PulsarNamespace";
    public static final String TYPE_NAME_TOPIC = "PulsarTopic";
    public static final String TYPE_NAME_SCHEMA = "PulsarSchema";
    public static final String TYPE_NAME_PUBLISHER = "PulsarPublisher";

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


}
