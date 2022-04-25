package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.datasource.pulsar.PulsarDatasource;
import org.metahut.starfish.datasource.pulsar.PulsarDatasourceManager;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.IngestionException;
import org.metahut.starfish.ingestion.collector.api.JSONUtils;
import org.metahut.starfish.ingestion.common.MetaMessageProducer;
import org.metahut.starfish.ingestion.common.ThreadUtils;
import org.metahut.starfish.message.api.IMessageProducer;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class PulsarCollector implements ICollector {

    private static final Logger logger = LoggerFactory.getLogger(PulsarCollector.class);

    private final PulsarCollectorParameter parameter;
    private final IMessageProducer producer;
    private final PulsarDatasource pulsarDatasource;
    private final PulsarAdmin admin;

    public PulsarCollector(PulsarCollectorParameter parameter) {
        this.parameter = parameter;
        producer = MetaMessageProducer.getInstance();
        pulsarDatasource = new PulsarDatasourceManager()
            .generateInstance(parameter.getDatasourceParameter());
        admin = pulsarDatasource.getMetaClient();
    }

    @Override
    public CollectorResult execute() {
        CollectorResult collectorResult = new CollectorResult();
        try {
            List<ConcurrentHashMap<String, String>> topicMetaDataList = getTopicMetaData();
            // TODO what is the key?
            producer.send("parameter.getDatasourceId()", JSONUtils.toJSONString(topicMetaDataList));

        } catch (PulsarAdminException e) {
            throw new IngestionException("get pulsar metaData is error:", e);
        }
        collectorResult.setMessage("get metaData is sucess");
        collectorResult.setState(true);
        return collectorResult;
    }

    public List<ConcurrentHashMap<String, String>> getTopicMetaData() throws PulsarAdminException {
        Map<String, List<String>> topics = new HashMap<>();
        CopyOnWriteArrayList<ConcurrentHashMap<String, String>> topicMetaList = new CopyOnWriteArrayList();
        List<String> namespaceList = new ArrayList<>();
        namespaceList = admin.tenants().getTenants().stream().map(name -> {
            try {
                return admin.namespaces().getNamespaces(name);
            } catch (PulsarAdminException e) {
                throw new IngestionException("get pulsar metaData is error:", e);
            }
        }).flatMap(Collection::stream).collect(Collectors.toList());
        namespaceList.stream().forEach(namespace -> {
            try {
                topics.put(namespace, admin.topics().getList(namespace));
            } catch (PulsarAdminException e) {
                throw new IngestionException("get topic metaData is error:", e);
            }
        });
        getTopicMetaInfo(topics, topicMetaList);
        return topicMetaList;
    }

    private void getTopicMetaInfo(Map<String, List<String>> topics,
        CopyOnWriteArrayList<ConcurrentHashMap<String, String>> topicMetaList) {
        ExecutorService threadPool = ThreadUtils.getThreadPoolExecutor();
        CountDownLatch latch = new CountDownLatch(topics.size());
        topics.entrySet().forEach(entry -> {
            threadPool.submit(
                () -> {
                    entry.getValue().stream().forEach(
                        topic -> {
                            ConcurrentHashMap map = new ConcurrentHashMap();
                            map.put("topic", topic);
                            String perType = topic.split("://")[0];
                            if ("persistent".equals(perType)) {
                                map.put("persistent", true);
                            } else {
                                map.put("persistent", false);
                            }
                            map.put("namespace", entry.getKey());
                            try {
                                map.put("clusters",
                                    admin.namespaces()
                                        .getNamespaceReplicationClusters(entry.getKey()));
                            } catch (PulsarAdminException e) {
                                throw new IngestionException(
                                    "get tenant detail information is error:",
                                    e);
                            }
                            try {
                                map.put("subscribe", admin.topics().getSubscriptions(topic));
                                map.put("schema", admin.schemas()
                                    .getAllSchemas(topic));
                            } catch (PulsarAdminException e) {
                                e.printStackTrace();
                            }
                            topicMetaList.add(map);
                            latch.countDown();
                        }
                    );
                }
            );
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        pulsarDatasource.close();
        producer.close();
    }
}
