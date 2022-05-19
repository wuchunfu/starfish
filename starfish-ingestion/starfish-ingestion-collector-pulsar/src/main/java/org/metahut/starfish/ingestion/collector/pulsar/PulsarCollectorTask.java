package org.metahut.starfish.ingestion.collector.pulsar;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollectorTask;
import org.metahut.starfish.ingestion.common.MetaMessageProducer;
import org.metahut.starfish.message.api.IMessageProducer;

import org.apache.pulsar.client.admin.PulsarAdmin;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class PulsarCollectorTask implements ICollectorTask {

    private final PulsarCollectorAdapter adapter;
    private final IMessageProducer producer;

    private final PulsarAdmin pulsarAdmin;

    public PulsarCollectorTask(PulsarCollectorAdapter adapter, PulsarCollectorTaskParameter parameter) {
        this.adapter = adapter;
        this.producer = MetaMessageProducer.getInstance();
        this.pulsarAdmin = this.adapter.getMetaClient();
    }

    private List<String> pulsarMetaColumn = null;
    private List<String> schemaInfo = null;
    private List<String> schemaType = null;
    private final String nonPersistent = "non-persistent";
    private static Properties properties = new Properties();
    private final int pulsarSize = 500;

    @Override
    public CollectorResult execute() {
        CollectorResult collectorResult = new CollectorResult();
//        BatchMetaDataDTO topicMetaData = getMsg();
//        // TODO what is the key?
//        producer.send("my-topic", JSONUtils.toJSONString(topicMetaData));
        collectorResult.setMessage("get metaData is sucess");
        collectorResult.setState(true);
        return collectorResult;
    }

//    @Override
//    public BatchMetaDataDTO getMsg() {
//        Map<String, List<String>> topics = new HashMap<>();
//        BatchMetaDataDTO topicMeta = new BatchMetaDataDTO();
//        List<String> namespaceList = new ArrayList<>();
//        try {
//            namespaceList = admin.tenants().getTenants().stream().map(name -> {
//                try {
//                    return admin.namespaces().getNamespaces(name);
//                } catch (PulsarAdminException e) {
//                    throw new CollectorException("get pulsar metaData is error:", e);
//                }
//            }).flatMap(Collection::stream).collect(Collectors.toList());
//        } catch (PulsarAdminException e) {
//            throw new CollectorException("get pulsar metaData is error:", e);
//        }
//        namespaceList.stream().forEach(namespace -> {
//            try {
//                topics.put(namespace, admin.topics().getList(namespace));
//            } catch (PulsarAdminException e) {
//                throw new CollectorException("get topic metaData is error:", e);
//            }
//        });
//        getTopicMetaInfo(topics, topicMeta);
//        return topicMeta;
//    }
//
//    @Override
//    public BatchSchemaDTO getClassInfo() {
//        BatchSchemaDTO schemaDTO = new BatchSchemaDTO();
//        //source
//        BatchSchemaDTO.SourceBodyDTO sourceBodyDTO = new BatchSchemaDTO.SourceBodyDTO();
//        sourceBodyDTO.setName("Pulsar");
//        schemaDTO.setSource(sourceBodyDTO);
//        List<BatchSchemaDTO.ClassDTO> types = new ArrayList<>();
//        BatchSchemaDTO.ClassDTO topicClassInfo = getType(pulsarMetaColumn, pulsar_topic);
//        BatchSchemaDTO.ClassDTO topicSchemaInfo = getType(schemaInfo, schema);
//        BatchSchemaDTO.ClassDTO topicSchemaType = getType(schemaType, schema_type);
//        types.add(topicClassInfo);
//        types.add(topicSchemaInfo);
//        types.add(topicSchemaType);
//        schemaDTO.setTypes(types);
//        return schemaDTO;
//    }
//
//    private BatchSchemaDTO.ClassDTO getType(List<String> metaColumns, String className) {
//        BatchSchemaDTO.ClassDTO tableClassDTO = new BatchSchemaDTO.ClassDTO();
//        tableClassDTO.setName(className);
//        tableClassDTO.setPackagePath("org.starfish");
//        //topic
//        List<BatchSchemaDTO.AttributeDTO> topicAttributeList = metaColumns.stream().map(metaColumn -> {
//            BatchSchemaDTO.AttributeDTO tableAttributeDTO = new BatchSchemaDTO.AttributeDTO();
//            String[] values = metaColumn.split(":");
//            tableAttributeDTO.setName(values[0]);
//            tableAttributeDTO.setClassName(values[1]);
//            tableAttributeDTO.setRelType(values[3]);
//            tableAttributeDTO.setArray(Boolean.valueOf(values[2]));
//            return tableAttributeDTO;
//        }).collect(Collectors.toList());
//        tableClassDTO.setAttributes(topicAttributeList);
//        return tableClassDTO;
//    }
//
//    private void getTopicMetaInfo(Map<String, List<String>> topics,
//                                  BatchMetaDataDTO topicMeta) {
//        ExecutorService threadPoolExecutor = ThreadUtils.getThreadPoolExecutor();
//        topicMeta.setSourceName("Pulsar");
//        CopyOnWriteArrayList<ConcurrentHashMap<String, Object>> instanceList = new CopyOnWriteArrayList();
//        ConcurrentHashMap<String, String> instancesMap = new ConcurrentHashMap<>();
//        Integer latchSizelatchSize = topics.entrySet().stream().map(topic -> {
//            return topic.getValue().size();
//        }).reduce(0, Integer::sum);
//        CountDownLatch latch = new CountDownLatch(latchSizelatchSize);
//        topics.entrySet().forEach(entry ->
//                threadPoolExecutor.submit(() -> {
//                    entry.getValue().stream().forEach(
//                            topic -> {
//                                //topic metaInstance info
//                                ConcurrentHashMap<String, Object> topicMetaInfo = new ConcurrentHashMap<>();
//                                topicMetaInfo.put("topic", topic);
//                                String perType = topic.split("://")[0];
//                                if (nonPersistent.equals(perType)) {
//                                    topicMetaInfo.put("persistent", false);
//                                } else {
//                                    topicMetaInfo.put("persistent", true);
//                                }
//                                topicMetaInfo.put("namespace", entry.getKey());
//                                try {
//                                    if (CollectionUtils.isNotEmpty(admin.namespaces()
//                                            .getNamespaceReplicationClusters(entry.getKey()))) {
//                                        topicMetaInfo.put("clusters", admin.namespaces()
//                                                .getNamespaceReplicationClusters(entry.getKey()));
//                                    }
//                                } catch (PulsarAdminException e) {
//                                    throw new CollectorException(
//                                            "get tenant detail information is error:",
//                                            e);
//                                }
//                                try {
//                                    if (CollectionUtils
//                                            .isNotEmpty(admin.topics().getSubscriptions(topic))) {
//                                        topicMetaInfo.put(
//                                                "subscribe", admin.topics().getSubscriptions(topic));
//                                    }
//                                } catch (PulsarAdminException e) {
//                                    throw new CollectorException(
//                                            e.getMessage());
//                                }
//                                try {
//                                    String schemaJson = admin.schemas()
//                                            .getSchemaInfo(topic).getSchemaDefinition();
//                                    if (StringUtils.isNotBlank(schemaJson)) {
//                                        Map<String, Object> props = JSONUtils.parseObject(admin.schemas().getSchemaInfo(topic).getSchemaDefinition(), Map.class);
//                                        List<HashMap<String, String>> fieldsProp = JSONUtils.parseObject(JSONUtils.toJSONString(props.get("fields")), ArrayList.class);
//                                        List<ConcurrentHashMap<String, Object>> schemaList = fieldsProp.stream().map(field -> {
//                                            ConcurrentHashMap<String, Object> schemaField = new ConcurrentHashMap<>();
//                                            schemaField.put("name", field.get("name"));
//                                            schemaField.put("type", field.get("type"));
//                                            return schemaField;
//                                        }).collect(Collectors.toList());
//                                        topicMetaInfo.put("schema", schemaList);
//                                    }
//                                } catch (PulsarAdminException e) {
//                                    e.printStackTrace();
//                                }
//                                instanceList.add(topicMetaInfo);
//                                latch.countDown();
//                            }
//                    );
//
//                })
//        );
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        instancesMap.put("org.starfish.PulsarTopic", JSONUtils.toJSONString(instanceList));
//        topicMeta.setInstances(instancesMap);
//    }

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
