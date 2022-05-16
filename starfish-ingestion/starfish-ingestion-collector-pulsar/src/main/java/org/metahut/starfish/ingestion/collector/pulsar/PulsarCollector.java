package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.api.dto.BatchMetaDataDTO;
import org.metahut.starfish.api.dto.BatchSchemaDTO;
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
import org.apache.commons.lang.StringUtils;
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
    private List<String> pulsarMetaColumn = null;
    private List<String> schemaInfo = null;
    private List<String> schemaType = null;
    private final String nonPersistent = "non-persistent";
    private static Properties properties = new Properties();
    private static final String pulsar_topic = "PulsarTopic";
    private static final String schema = "schema";
    private static final String schema_type = "schemaType";

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
        BatchMetaDataDTO topicMetaData = getMsg();
        // TODO what is the key?
        producer.send("my-topic", JSONUtils.toJSONString(topicMetaData));
        collectorResult.setMessage("get metaData is sucess");
        collectorResult.setState(true);
        return collectorResult;
    }

    @Override
    public BatchMetaDataDTO getMsg() {
        Map<String, List<String>> topics = new HashMap<>();
        BatchMetaDataDTO topicMeta = new BatchMetaDataDTO();
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
        getTopicMetaInfo(topics, topicMeta);
        return topicMeta;
    }

    @Override
    public BatchSchemaDTO getClassInfo() {
        BatchSchemaDTO schemaDTO = new BatchSchemaDTO();
        //source
        BatchSchemaDTO.SourceBodyDTO sourceBodyDTO = new BatchSchemaDTO.SourceBodyDTO();
        sourceBodyDTO.setName("Pulsar");
        schemaDTO.setSource(sourceBodyDTO);
        List<BatchSchemaDTO.ClassDTO> types = new ArrayList<>();
        BatchSchemaDTO.ClassDTO topicClassInfo = getType(pulsarMetaColumn, pulsar_topic);
        BatchSchemaDTO.ClassDTO topicSchemaInfo = getType(schemaInfo, schema);
        BatchSchemaDTO.ClassDTO topicSchemaType = getType(schemaType, schema_type);
        types.add(topicClassInfo);
        types.add(topicSchemaInfo);
        types.add(topicSchemaType);
        schemaDTO.setTypes(types);
        return schemaDTO;
    }

    private BatchSchemaDTO.ClassDTO getType(List<String> metaColumns, String className) {
        BatchSchemaDTO.ClassDTO tableClassDTO = new BatchSchemaDTO.ClassDTO();
        tableClassDTO.setName(className);
        tableClassDTO.setPackagePath("org.starfish");
        //topic
        List<BatchSchemaDTO.AttributeDTO> topicAttributeList = metaColumns.stream().map(metaColumn -> {
            BatchSchemaDTO.AttributeDTO tableAttributeDTO = new BatchSchemaDTO.AttributeDTO();
            String[] values = metaColumn.split(":");
            tableAttributeDTO.setName(values[0]);
            tableAttributeDTO.setClassName(values[1]);
            tableAttributeDTO.setRelType(values[3]);
            tableAttributeDTO.setArray(Boolean.valueOf(values[2]));
            return tableAttributeDTO;
        }).collect(Collectors.toList());
        tableClassDTO.setAttributes(topicAttributeList);
        return tableClassDTO;
    }

    private void getTopicMetaInfo(Map<String, List<String>> topics,
                                  BatchMetaDataDTO topicMeta) {
        ExecutorService threadPoolExecutor = ThreadUtils.getThreadPoolExecutor();
        topicMeta.setSourceName("Pulsar");
        CopyOnWriteArrayList<ConcurrentHashMap<String, Object>> instanceList = new CopyOnWriteArrayList();
        ConcurrentHashMap<String, String> instancesMap = new ConcurrentHashMap<>();
        Integer latchSizelatchSize = topics.entrySet().stream().map(topic -> {
            return topic.getValue().size();
        }).reduce(0, Integer::sum);
        CountDownLatch latch = new CountDownLatch(latchSizelatchSize);
        topics.entrySet().forEach(entry ->
                threadPoolExecutor.submit(() -> {
                    entry.getValue().stream().forEach(
                            topic -> {
                                //topic metaInstance info
                                ConcurrentHashMap<String, Object> topicMetaInfo = new ConcurrentHashMap<>();
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
                                try {
                                    String schemaJson = admin.schemas()
                                            .getSchemaInfo(topic).getSchemaDefinition();
                                    if (StringUtils.isNotBlank(schemaJson)) {
                                        Map<String, Object> props = JSONUtils.parseObject(admin.schemas().getSchemaInfo(topic).getSchemaDefinition(), Map.class);
                                        List<HashMap<String, String>> fieldsProp = JSONUtils.parseObject(JSONUtils.toJSONString(props.get("fields")), ArrayList.class);
                                        List<ConcurrentHashMap<String, Object>> schemaList = fieldsProp.stream().map(field -> {
                                            ConcurrentHashMap<String, Object> schemaField = new ConcurrentHashMap<>();
                                            schemaField.put("name", field.get("name"));
                                            schemaField.put("type", field.get("type"));
                                            return schemaField;
                                        }).collect(Collectors.toList());
                                        topicMetaInfo.put("schema", schemaList);
                                    }
                                } catch (PulsarAdminException e) {
                                    e.printStackTrace();
                                }
                                instanceList.add(topicMetaInfo);
                                latch.countDown();
                            }
                    );

                })
        );
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        instancesMap.put("org.starfish.PulsarTopic", JSONUtils.toJSONString(instanceList));
        topicMeta.setInstances(instancesMap);
    }

    @Override
    public void close() throws Exception {
        pulsarDatasource.close();
        producer.close();
    }
}
