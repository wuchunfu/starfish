package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.datasource.hive.HiveDatasource;
import org.metahut.starfish.datasource.hive.HiveDatasourceManager;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.JSONUtils;
import org.metahut.starfish.ingestion.common.MetaMessageProducer;
import org.metahut.starfish.message.api.IMessageProducer;
import org.metahut.starfish.parser.domain.enums.RelType;

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
            e.printStackTrace();
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
        List<Map<String, Object>> hiveMessages = getTables();
        try {
            producer
                .send(this.hiveParameter.getDatasourceId(), JSONUtils.toJSONString(hiveMessages));
        } catch (Exception e) {
            collectorResult.setState(false);
            collectorResult.setMessage(e.getMessage());
            return collectorResult;
        }
        collectorResult.setState(true);
        collectorResult.setMessage("send hive messages is success");
        return collectorResult;
    }

    //table metaData
    public List<Map<String, Object>> getTables() {

        Map<String, List<Table>> dataBase = new HashMap<>();
        try {
            metaClient.getAllDatabases().stream().forEach(dbName -> {
                try {
                    List<Table> tables = new ArrayList<>();
                    metaClient.getAllTables(dbName).forEach(tbName -> {
                        try {
                            tables.add(metaClient.getTable(dbName, tbName));
                        } catch (TException e) {
                            e.printStackTrace();
                        }
                    });
                    dataBase.put(dbName, tables);
                } catch (TException e) {
                    e.printStackTrace();
                }
            });
        } catch (TException e) {
            e.printStackTrace();
        }
        return dataBase.entrySet().stream().map(Entry::getValue
        ).flatMap(Collection::stream).collect(Collectors.toList()).stream().map(table -> {
            HashMap<String, Object> hiveMetaData = new HashMap<>();
            Class<? extends Table> clazz = table.getClass();
            Field[] fields = clazz.getDeclaredFields();
            HashMap classInfo = new HashMap();
            classInfo.put("packagePath", "default");
            classInfo.put("name", "hiveTable");
            List<HashMap<String, Object>> attrList = geHiveMetaClassInfo(table, fields);
            classInfo.put("attribute", attrList);
            Map<String, Object> instanceInfo = geHiveMetaClassInstance(table, fields);
            List<FieldSchema> fieldSchemaList = table.getSd().getCols();
            HashMap columnInfo = new HashMap();
            columnInfo.put("packagePath", "default");
            columnInfo.put("name", "hiveColumn");
            List<Map<String, Object>> fieldMetaClassList = fieldSchemaList.stream()
                .map(fieldSchema -> {
                    HashMap<String, Object> props = new HashMap<>();
                    props.put("name", fieldSchema.getName());
                    props.put("className", fieldSchema.getType());
                    props.put("RelType", RelType.PRIMITIVE);
                    props.put("array", false);
                    return props;
                }).collect(Collectors.toList());
            columnInfo.put("attribute", fieldMetaClassList);
            List<Map<String, Object>> fieldMetaInsatnceList = fieldSchemaList.stream()
                .map(fieldSchema -> {
                    HashMap<String, Object> props = new HashMap<>();
                    props.put("name", fieldSchema.getName());
                    props.put("type", fieldSchema.getType());
                    props.put("comment", fieldSchema.getComment());
                    return props;
                }).collect(Collectors.toList());
            hiveMetaData.put("tableClassInfo", classInfo);
            hiveMetaData.put("tableInstanceInfo", instanceInfo);
            hiveMetaData.put("fieldMetaClassList", fieldMetaClassList);
            hiveMetaData.put("fieldMetaInsatnceList", fieldMetaInsatnceList);
            return hiveMetaData;
        }).collect(Collectors.toList());
    }

    /**
     * get meta information about hive table class
     *
     * @param table
     * @param fields
     * @return
     */
    private List<HashMap<String, Object>> geHiveMetaClassInfo(Object table, Field[] fields) {
        List<HashMap<String, Object>> attrList = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            HashMap<String, Object> props = new HashMap<>();
            try {
                props.put("name", field.get(table));
                props.put("className", field.getType());
                props.put("RelType", RelType.PRIMITIVE);
                if (field.get(table) instanceof Collection) {
                    props.put("array", true);
                } else {
                    props.put("array", false);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            attrList.add(props);
        }
        return attrList;
    }

    /**
     * get meta information about hive table instance
     *
     * @param table
     * @param fields
     * @return
     */
    private HashMap<String, Object> geHiveMetaClassInstance(Table table, Field[] fields) {
        HashMap<String, Object> metaInstance = new HashMap<>();
        for (Field field : fields) {
            try {
                if (hiveMetaColumn.contains(field.getName())) {
                    field.setAccessible(true);
                    if (partitionKeys.equals(field.getName())) {
                        metaInstance
                            .put(partitionStatus,
                                table.getPartitionKeys().size() > 0 ? true : false);
                        metaInstance.put(partitionKeys,
                            Objects.nonNull(table.getPartitionKeys()) ? table.getPartitionKeys()
                                : new ArrayList<>());
                    }
                    metaInstance.put(field.getName(), field.get(table));
                    metaInstance.put("className", field.getType());
                    if (field.get(table) instanceof Collection) {
                        metaInstance.put("array", true);
                    } else {
                        metaInstance.put("array", false);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return metaInstance;
    }
}
