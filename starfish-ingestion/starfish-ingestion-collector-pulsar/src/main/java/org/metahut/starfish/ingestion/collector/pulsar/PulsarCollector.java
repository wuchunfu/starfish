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
                                BatchMetaDataDTO.SourceBodyDTO sourceBodyDTO = new BatchMetaDataDTO.SourceBodyDTO();
                                sourceBodyDTO.setName("Pulsar");
                                sourceBodyDTO.setAttributes(null);
                                dto.setSource(sourceBodyDTO);

                                List<BatchMetaDataDTO.ClassDTO> types = new ArrayList<>();

                                //topic metaClass info
                                BatchMetaDataDTO.ClassDTO topicInfo = new BatchMetaDataDTO.ClassDTO();
                                topicInfo.setName("Topic");
                                topicInfo.setPackagePath("org.starfish");
                                List<BatchMetaDataDTO.AttributeDTO> attributes = null;
                                attributes = pulsarMetaColumn.stream().map(
                                    pulsarMetaColumnItem -> {
                                        BatchMetaDataDTO.AttributeDTO attribute = new BatchMetaDataDTO.AttributeDTO();
                                        String[] values = pulsarMetaColumnItem.split(":");
                                        attribute.setName(values[0]);
                                        attribute.setClassName(values[1]);
                                        attribute.setArray(Boolean.valueOf(values[2]));
                                        attribute.setRelType(values[3]);
                                        return attribute;
                                    }
                                ).collect(Collectors.toList());
                                topicInfo.setAttributes(attributes);

                                //schecma metaClass info
                                BatchMetaDataDTO.ClassDTO pulsarSchema = new BatchMetaDataDTO.ClassDTO();
                                pulsarSchema.setName("Schema");
                                pulsarSchema.setPackagePath("org.starfish");
                                List<BatchMetaDataDTO.AttributeDTO> schemaAttributes = null;
                                schemaAttributes = schemaInfo.stream().map(
                                    schemaInfoItem -> {
                                        BatchMetaDataDTO.AttributeDTO attribute = new BatchMetaDataDTO.AttributeDTO();
                                        String[] values = schemaInfoItem.split(":");
                                        attribute.setName(values[0]);
                                        attribute.setClassName(values[1]);
                                        attribute.setArray(Boolean.valueOf(values[2]));
                                        attribute.setRelType(values[3]);
                                        return attribute;
                                    }
                                ).collect(Collectors.toList());
                                pulsarSchema.setAttributes(schemaAttributes);

                                //schemaType metaClass info
                                BatchMetaDataDTO.ClassDTO pulsarSchemaType = new BatchMetaDataDTO.ClassDTO();
                                pulsarSchemaType.setName("SchemaType");
                                pulsarSchemaType.setPackagePath("org.starfish");
                                List<BatchMetaDataDTO.AttributeDTO> schemaTypeAttributes = null;
                                schemaTypeAttributes = schemaType.stream().map(
                                    schemaTypeItem -> {
                                        BatchMetaDataDTO.AttributeDTO attribute = new BatchMetaDataDTO.AttributeDTO();
                                        String[] values = schemaTypeItem.split(":");
                                        attribute.setName(values[0]);
                                        attribute.setClassName(values[1]);
                                        attribute.setArray(Boolean.valueOf(values[2]));
                                        attribute.setRelType(values[3]);
                                        return attribute;
                                    }
                                ).collect(Collectors.toList());
                                pulsarSchemaType.setAttributes(schemaTypeAttributes);

                                types.add(topicInfo);
                                types.add(pulsarSchema);
                                types.add(pulsarSchemaType);
                                //topic metaInstance info
                                List<String> topicMetaInfo = new ArrayList<>();
                                topicMetaInfo.add("{\"topic\":\"" + topic + "\"}");
                                String perType = topic.split("://")[0];
                                if (nonPersistent.equals(perType)) {
                                    topicMetaInfo.add(
                                        "{\"persistent\":\"" + false + "\"}");
                                } else {
                                    topicMetaInfo.add(
                                        "{\"persistent\":\"" + true + "\"}");
                                }
                                topicMetaInfo.add(
                                    "{\"namespace\":\"" + entry.getKey() + "\"}");
                                try {
                                    if (CollectionUtils.isNotEmpty(admin.namespaces()
                                        .getNamespaceReplicationClusters(entry.getKey()))) {
                                        topicMetaInfo.add(
                                            "{\"clusters\":\"" + admin.namespaces()
                                                .getNamespaceReplicationClusters(entry.getKey())
                                                + "\"}");
                                    }
                                } catch (PulsarAdminException e) {
                                    throw new IngestionException(
                                        "get tenant detail information is error:",
                                        e);
                                }
                                try {
                                    if (CollectionUtils
                                        .isNotEmpty(admin.topics().getSubscriptions(topic))) {
                                        topicMetaInfo.add(
                                            "{\"subscribe\":\"" + admin.topics().getSubscriptions(topic)
                                                + "\"}");
                                    }
                                } catch (PulsarAdminException e) {
                                    throw new IngestionException(
                                        e.getMessage());
                                }

                                HashMap<String, List<String>> instances = new HashMap<String, List<String>>();
                                try {
                                    HashMap schemaMap = new HashMap<String, Object>();
                                    schemaMap.put("schema", admin.schemas()
                                        .getSchemaInfo(topic).getSchemaDefinition());
                                    instances.put("org.starfish.Schema",
                                        Arrays.asList(
                                            JSONUtils.toJSONString(schemaMap))
                                    );
                                    instances.put("org.starfish.SchemaType",
                                        Arrays.asList(
                                            "{\"schemaType\":\"" + admin.schemas()
                                                .getSchemaInfo(topic).getType().name() + "\"}"
                                        ));
                                    instances.put("org.starfish.Topic",
                                        topicMetaInfo);
                                } catch (PulsarAdminException e) {
                                    e.printStackTrace();
                                }
                                dto.setInstances(instances);
                                dto.setTypes(types);
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
