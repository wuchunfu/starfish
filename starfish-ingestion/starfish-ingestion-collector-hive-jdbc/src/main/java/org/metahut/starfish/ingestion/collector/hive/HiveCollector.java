package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.api.dto.BatchMetaDataDTO;
import org.metahut.starfish.datasource.hive.HiveDatasource;
import org.metahut.starfish.datasource.hive.HiveDatasourceManager;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.IngestionException;
import org.metahut.starfish.ingestion.collector.api.JSONUtils;
import org.metahut.starfish.ingestion.common.MetaMessageProducer;
import org.metahut.starfish.message.api.IMessageProducer;

import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.thrift.TException;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

public class HiveCollector implements ICollector {

    private final HiveCollectorParameter hiveParameter;
    private final IMessageProducer producer;
    private final HiveDatasource hiveDatasource;
    private final IMetaStoreClient metaClient;
    private List<String> hiveMetaColumn = null;
    private final String partitionKeys = "partitionKeys";
    private final String partitionStatus = "partitionStatus";
    private static Properties properties = new Properties();

    public HiveCollector(HiveCollectorParameter hiveParameter) {
        this.hiveParameter = hiveParameter;
        producer = MetaMessageProducer.getInstance();
        hiveDatasource = new HiveDatasourceManager()
            .generateInstance(this.hiveParameter.getDatasourceParameter());
        metaClient = hiveDatasource.getMetaClient();
        ClassPathResource classPathResource = new ClassPathResource("\\hiveMetaData.properties");
        try {
            InputStream fileInputStream = classPathResource.getInputStream();
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new IngestionException(e.getMessage());
        }
        hiveMetaColumn = Arrays.stream(properties.get("METADATA").toString().split(","))
            .map(String::trim).collect(
                Collectors.toList());
    }

    @Override
    public void close() throws Exception {
        if (Objects.nonNull(producer)) {
            producer.close();
        }

        if (Objects.nonNull(hiveDatasource)) {
            hiveDatasource.close();
        }
    }

    @Override
    public CollectorResult execute() {
        CollectorResult collectorResult = new CollectorResult();
        //transform the message
        List<BatchMetaDataDTO> hiveMessages = getMsg();
        try {
            // TODO what is the key?
            producer
                .send("testHive", JSONUtils.toJSONString(hiveMessages));
        } catch (Exception e) {
            throw new IngestionException(e.getMessage());
        }
        collectorResult.setState(true);
        collectorResult.setMessage("send hive messages is success");
        return collectorResult;
    }

    //table metaData
    @Override
    public List<BatchMetaDataDTO> getMsg() {

        Map<String, List<Table>> dataBase = new HashMap<>();
        try {
            metaClient.getAllDatabases().stream().forEach(dbName -> {
                try {
                    List<Table> tables = new ArrayList<>();
                    metaClient.getAllTables(dbName).forEach(tbName -> {
                        try {
                            tables.add(metaClient.getTable(dbName, tbName));
                        } catch (TException e) {
                            throw new IngestionException(e.getMessage());
                        }
                    });
                    dataBase.put(dbName, tables);
                } catch (TException e) {
                    throw new IngestionException(e.getMessage());
                }
            });
        } catch (TException e) {
            throw new IngestionException(e.getMessage());
        }
        return dataBase.entrySet().stream().map(Entry::getValue
        ).flatMap(Collection::stream).collect(Collectors.toList()).stream().map(table -> {
            Map<String, List<String>> hiveMetaData = new HashMap<>();
            List<String> instanceInfoList = new ArrayList<>();
            List<String> fieldMetaInsatnceList = new ArrayList<>();
            BatchMetaDataDTO dto = new BatchMetaDataDTO();
            Class<? extends Table> clazz = table.getClass();
            Field[] fields = clazz.getDeclaredFields();
            List<String> instanceInfo = geHiveMetaInstance(table, fields);
            List<FieldSchema> fieldSchemaList = table.getSd().getCols();
            fieldMetaInsatnceList = fieldSchemaList.stream()
                .map(fieldSchema -> {
                    HashMap<String, Object> props = new HashMap<>();
                    props.put("columnName", fieldSchema.getName());
                    props.put("columnType", fieldSchema.getType());
                    return JSONUtils.toJSONString(props);
                }).collect(Collectors.toList());
            hiveMetaData = new HashMap<>();
            hiveMetaData.put("org.starfish.HiveTable", instanceInfo);
            hiveMetaData.put("org.starfish.HiveColumn", fieldMetaInsatnceList);
            dto.setInstances(hiveMetaData);
            dto.setSourceName("Hive");
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * get meta information about hive table instance
     *
     * @param table
     * @param fields
     * @return
     */
    private List<String> geHiveMetaInstance(Table table, Field[] fields) {
        List<String> hiveMetaData = new ArrayList<>();
        for (Field field : fields) {
            try {
                if (hiveMetaColumn.contains(field.getName())) {
                    HashMap<String, Object> metaInstance = new HashMap<>();
                    field.setAccessible(true);
                    if (partitionKeys.equals(field.getName())) {
                        HashMap<String, Object> partitionStatusInstance = new HashMap<>();
                        partitionStatusInstance
                            .put(partitionStatus,
                                table.getPartitionKeys().size() > 0 ? true : false);
                        hiveMetaData.add(JSONUtils.toJSONString(partitionStatusInstance));
                        HashMap<String, Object> partitionKeysInstance = new HashMap<>();
                        partitionKeysInstance.put(partitionKeys,
                            Objects.nonNull(table.getPartitionKeys()) ? table.getPartitionKeys()
                                : new ArrayList<>());
                    } else {
                        metaInstance.put(field.getName(), field.get(table));
                        hiveMetaData.add(JSONUtils.toJSONString(metaInstance));
                    }
                }
            } catch (IllegalAccessException e) {
                throw new IngestionException(e.getMessage());
            }
        }
        return hiveMetaData;
    }
}
