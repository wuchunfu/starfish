package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.IngestionException;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.mortbay.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PulsarCollector implements ICollector {

    private String serverUrl;

    PulsarClient client = null;
    PulsarAdmin admin = null;

    public PulsarCollector(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public CollectorResult testConnection() {
        CollectorResult collectorResult = new CollectorResult();
        try {
            client = PulsarClient.builder()
                    .serviceUrl(serverUrl)
                    .build();
        } catch (PulsarClientException e) {
            throw new IngestionException("get pulsar connection is error:", e);
        }
        if (client.isClosed()) {
            collectorResult.setState(false);
            collectorResult.setMessage("connect is fail");
        } else {
            collectorResult.setState(true);
            collectorResult.setMessage("connect is success");
        }
        return collectorResult;
    }

    @Override
    public CollectorResult execute() {
        CollectorResult collectorResult = new CollectorResult();
        try {
            admin = PulsarAdmin.builder().serviceHttpUrl(serverUrl).build();
            getClusterMetaData();
            getTenant();
            getTopicMetaData();
        } catch (PulsarClientException | PulsarAdminException e) {
            throw new IngestionException("get pulsar metaData is error:", e);
        }
        collectorResult.setMessage("get metaData is sucess");
        collectorResult.setState(true);
        return collectorResult;
    }

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
                throw new IngestionException("get cluster metaData is error:", e);
            }
        });
        Log.info(cluster.toString());
    }

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
                throw new IngestionException("get tenant metaData is error:", e);
            }
        });
        Log.info(tenants.toString());
    }

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
                                throw new IngestionException("get topic metaData is error:", e);
                            }
                        }
                );
            } catch (PulsarAdminException e) {
                throw new IngestionException("get pulsar metaData is error:", e);
            }
        });
        getTopicMetaInfo(topics, topicMetaList, "z/imsync");
        Log.info(topicMetaList.toString());
    }

    private void getTopicMetaInfo(Map<String, List<String>> topics, List<Map<String, String>> topicMetaList, String namespace) {
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
                                map.put("clusters", admin.namespaces().getNamespaceReplicationClusters(entry.getKey()));
                            } catch (PulsarAdminException e) {
                                throw new IngestionException("get tenant detail information is error:", e);
                            }
                            topicMetaList.add(map);
                        }
                );
            }
        });
    }

    @Override
    public void close() throws Exception {
        client.close();
        admin.close();
    }
}
