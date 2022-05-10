package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.api.dto.BatchMetaDataDTO;
import org.metahut.starfish.datasource.pulsar.PulsarDatasource;
import org.metahut.starfish.datasource.pulsar.PulsarDatasourceManager;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.IngestionException;
import org.metahut.starfish.ingestion.collector.api.JSONUtils;
import org.metahut.starfish.ingestion.common.MetaMessageProducer;
import org.metahut.starfish.ingestion.common.ThreadUtils;
import org.metahut.starfish.message.api.IMessageProducer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
    private List<String> pulsarMetaColumn = null;
    private List<String> schemaInfo = null;
    private List<String> schemaType = null;
    private final String nonPersistent = "non-persistent";
    private static Properties properties = new Properties();
    private final int pulsarSize = 500;

    public PulsarCollector(PulsarCollectorParameter parameter) {
        this.parameter = parameter;
        producer = MetaMessageProducer.getInstance();
        pulsarDatasource = new PulsarDatasourceManager()
            .generateInstance(parameter.getDatasourceParameter());
        admin = pulsarDatasource.getMetaClient();
        ClassPathResource classPathResource = new ClassPathResource("\\pulsarMetaData.properties");
        try {
            InputStream fileInputStream = classPathResource.getInputStream();
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new IngestionException(e.getMessage());
        }
        pulsarMetaColumn = Arrays.stream(properties.get("TOPIC").toString().split(","))
            .map(String::trim).collect(
                Collectors.toList());
        schemaInfo = Arrays.stream(properties.get("SCHEMAINFO").toString().split(","))
            .map(String::trim).collect(
                Collectors.toList());
        schemaType = Arrays.stream(properties.get("SHEMATYPE").toString().split(","))
            .map(String::trim).collect(
                Collectors.toList());
    }

    @Override
    public CollectorResult execute() {
        CollectorResult collectorResult = new CollectorResult();
        CopyOnWriteArrayList<BatchMetaDataDTO> topicMetaDataList = getMsg();
        // TODO what is the key?
        List<List<BatchMetaDataDTO>> subLists = ListUtils
            .partition(topicMetaDataList, pulsarSize);
        subLists.stream().forEach(
            subList -> {
                producer.send("my-topic", JSONUtils.toJSONString(subList));
            }
        );
        collectorResult.setMessage("get metaData is sucess");
        collectorResult.setState(true);
        return collectorResult;
    }

    @Override
    public CopyOnWriteArrayList<BatchMetaDataDTO> getMsg() {
        Map<String, List<String>> topics = new HashMap<>();
        CopyOnWriteArrayList<BatchMetaDataDTO> topicMetaList = new CopyOnWriteArrayList();
        List<String> namespaceList = new ArrayList<>();
        try {
            namespaceList = admin.tenants().getTenants().stream().map(name -> {
                try {
                    return admin.namespaces().getNamespaces(name);
                } catch (PulsarAdminException e) {
                    throw new IngestionException("get pulsar metaData is error:", e);
                }
            }).flatMap(Collection::stream).collect(Collectors.toList());
        } catch (PulsarAdminException e) {
            throw new IngestionException("get pulsar metaData is error:", e);
        }
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
        CopyOnWriteArrayList<BatchMetaDataDTO> topicMetaList) {
        ExecutorService threadPool = ThreadUtils.getThreadPoolExecutor();
        Integer latchSizelatchSize = topics.entrySet().stream().map(topic -> {
            return topic.getValue().size();
        }).reduce(0, Integer::sum);
        CountDownLatch latch = new CountDownLatch(latchSizelatchSize);
        topics.entrySet().forEach(entry -> {
                threadPool.submit(
                    () -> {
                        entry.getValue().stream().forEach(
                            topic -> {
                                BatchMetaDataDTO dto = new BatchMetaDataDTO();
                                dto.setSourceName("Pulsar");
                                //topic metaInstance info
                                Map<String, Object> topicMetaInfo = new HashMap<>();
                                topicMetaInfo.put("topic", topic);
                                String perType = topic.split("://")[0];
                                if (nonPersistent.equals(perType)) {
                                    topicMetaInfo.put("persistent", false);
                                } else {
                                    topicMetaInfo.put("persistent", true);
                                }
                                topicMetaInfo.put("namespace", entry.getKey());
                                try {
                                    if (CollectionUtils.isNotEmpty(admin.namespaces()
                                        .getNamespaceReplicationClusters(entry.getKey()))) {
                                        topicMetaInfo.put("clusters", admin.namespaces()
                                            .getNamespaceReplicationClusters(entry.getKey()));
                                    }
                                } catch (PulsarAdminException e) {
                                    throw new IngestionException(
                                        "get tenant detail information is error:",
                                        e);
                                }
                                try {
                                    if (CollectionUtils
                                        .isNotEmpty(admin.topics().getSubscriptions(topic))) {
                                        topicMetaInfo.put(
                                            "subscribe", admin.topics().getSubscriptions(topic));
                                    }
                                } catch (PulsarAdminException e) {
                                    throw new IngestionException(
                                        e.getMessage());
                                }

                                HashMap<String, List<String>> instances = new HashMap<String, List<String>>();
                                HashMap<String, Object> schema = new HashMap<>();
                                HashMap<String, Object> schemaType = new HashMap<>();
                                try {
                                    schema.put("schema", admin.schemas()
                                        .getSchemaInfo(topic).getSchemaDefinition());
                                    instances.put("org.starfish.Schema",
                                        Arrays.asList(
                                            JSONUtils.toJSONString(schema)));
                                    schemaType.put("schemaType", admin.schemas()
                                        .getSchemaInfo(topic).getType().name());
                                    instances.put("org.starfish.SchemaType",
                                        Arrays.asList(
                                            JSONUtils.toJSONString(schemaType)));
                                    instances.put("org.starfish.Topic",
                                        Arrays.asList(JSONUtils.toJSONString(topicMetaInfo)));
                                } catch (PulsarAdminException e) {
                                    e.printStackTrace();
                                }
                                dto.setInstances(instances);
                                topicMetaList.add(dto);
                                latch.countDown();
                            }
                        );
                    }
                );
            }
        );
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
