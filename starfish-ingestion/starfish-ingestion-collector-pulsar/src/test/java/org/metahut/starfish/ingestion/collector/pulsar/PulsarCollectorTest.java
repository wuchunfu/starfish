package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.datasource.pulsar.PulsarDatasourceParameter;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.JSONUtils;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.common.policies.data.TopicStats;
import org.apache.pulsar.common.schema.SchemaInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Disabled
public class PulsarCollectorTest {

    PulsarClient client = null;
    PulsarAdmin admin = null;
    String url = "XXX";

    @BeforeEach
    @Disabled
    public void init() {
        try {
            client = PulsarClient.builder()
                .serviceUrl(url)
                .build();
            admin = PulsarAdmin.builder().serviceHttpUrl(url).build();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled
    public void getClusterMetaData() throws PulsarAdminException {
        List<Map> cluster = new ArrayList<>();
        admin.clusters().getClusters().stream().forEach(clusterName -> {
            try {
                Map<String, Object> map = new HashMap<>();
                map.put("cluster", clusterName);
                map.put("clusterInfo", admin.clusters().getCluster(clusterName));
                map.put("brokerInfo", admin.brokers().getActiveBrokers(clusterName));
                cluster.add(map);
            } catch (PulsarAdminException e) {
                e.printStackTrace();
            }
        });
        Assertions.assertNotNull(cluster);
    }

    @Test
    @Disabled
    public void getTenant() throws PulsarAdminException {
        List<Map> tenants = new ArrayList<>();
        admin.tenants().getTenants().stream().forEach(name -> {
            try {
                Map<String, Object> map = new HashMap<>();
                map.put("tenantName", name);
                map.put("tenentInfo", admin.tenants().getTenantInfo(name));
                map.put("namespace", admin.namespaces().getNamespaces(name));
                tenants.add(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Assertions.assertNotNull(tenants);
    }

    @Test
    @Disabled
    public void getTopicMetaData() throws PulsarAdminException {
        Map<String, List<String>> topics = new HashMap<>();
        List<Map<String, String>> topicMetaList = new ArrayList<>();
        admin.tenants().getTenants().stream().forEach(name -> {
            try {
                admin.namespaces().getNamespaces(name).stream().forEach(
                    namespace -> {
                        try {
                            topics.put(namespace, admin.topics().getList(namespace));
                        } catch (PulsarAdminException e) {
                            e.printStackTrace();
                        }
                    }
                );
            } catch (PulsarAdminException e) {
                e.printStackTrace();
            }
        });
        getTopicMetaInfo(topics, topicMetaList, "z/imsync");
        Assertions.assertNotNull(topicMetaList);
    }

    @Disabled
    private void getTopicMetaInfo(Map<String, List<String>> topics,
        List<Map<String, String>> topicMetaList, String namespace) {
        topics.entrySet().forEach(entry -> {
            if (namespace.equals(entry.getKey())) {
                entry.getValue().stream().forEach(
                    topic -> {
                        HashMap map = new HashMap();
                        map.put("topic", topic);
                        String perType = topic.split("://")[0];
                        if ("persistent".equals(perType)) {
                            map.put("persistent", "true");
                        } else {
                            map.put("persistent", "false");
                        }
                        map.put("namespace", entry.getKey());
                        try {
                            map.put("clusters",
                                admin.namespaces().getNamespaceReplicationClusters(entry.getKey()));
                        } catch (PulsarAdminException e) {
                            e.printStackTrace();
                        }
                        topicMetaList.add(map);
                    }
                );
            }
        });
    }

    @Test
    @Disabled
    public void testGetSchema() {
        List<SchemaInfo> schemaInfoList = null;
        try {
            schemaInfoList = admin.schemas()
                .getAllSchemas("persistent://data/datamax/std.feature.test1");
        } catch (PulsarAdminException e) {
            e.printStackTrace();
        }
        String schema = new String(schemaInfoList.get(0).getSchema());
        Assertions.assertNotNull(schemaInfoList);
    }

    @Test
    @Disabled
    public void testGetSubscribe() {
        List<String> subscriptions = null;
        try {
            subscriptions = admin.topics()
                .getSubscriptions("my-topic");
        } catch (PulsarAdminException e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(subscriptions);
    }

    @Test
    @Disabled
    public void testGetSubscribeInfo() {
        TopicStats topicStats = null;
        try {
            topicStats = admin.topics().getStats("my-topic");
        } catch (PulsarAdminException e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(topicStats);
    }

    @Test
    @Disabled
    public void execute() {
        PulsarDatasourceParameter pulsarDatasourceParameter = new PulsarDatasourceParameter();
        pulsarDatasourceParameter.setServerUrl("http://pulsar-idc-qa.zpidc.com:8080");
        PulsarCollectorParameter parameter = new PulsarCollectorParameter();
        // parameter.setDatasourceId("my-topic");
        parameter.setDatasourceParameter(JSONUtils.toJSONString(pulsarDatasourceParameter));
        CollectorResult collectorResult = new PulsarCollectorManager().generateInstance(parameter).execute();
        Assertions.assertNotNull(collectorResult);
    }

}
