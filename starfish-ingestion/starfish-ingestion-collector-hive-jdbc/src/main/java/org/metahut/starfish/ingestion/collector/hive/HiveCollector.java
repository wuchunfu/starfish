package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.api.controller.MetaDataController;
import org.metahut.starfish.api.dto.BatchMetaDataDTO;
import org.metahut.starfish.datasource.hive.HiveDatasource;
import org.metahut.starfish.datasource.hive.HiveDatasourceManager;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.IngestionException;
import org.metahut.starfish.ingestion.collector.api.JSONUtils;
import org.metahut.starfish.ingestion.common.MetaMessageProducer;
import org.metahut.starfish.message.api.IMessageProducer;
import org.metahut.starfish.parser.domain.enums.RelType;

import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
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
            BatchMetaDataDTO dto = new BatchMetaDataDTO();
            BatchMetaDataDTO.SourceBodyDTO sourceBodyDTO = new BatchMetaDataDTO.SourceBodyDTO();
            sourceBodyDTO.setName("Hive");
            sourceBodyDTO.setAttributes(null);
            dto.setSource(sourceBodyDTO);

            Class<? extends Table> clazz = table.getClass();
            Field[] fields = clazz.getDeclaredFields();
            List<String> instanceInfo = geHiveMetaInstance(table, fields);

            List<FieldSchema> fieldSchemaList = table.getSd().getCols();
            List<String> fieldMetaInsatnceList = fieldSchemaList.stream()
                .map(fieldSchema -> {
                    HashMap<String, Object> props = new HashMap<>();
                    props.put("columnName", fieldSchema.getName());
                    props.put("columnType", fieldSchema.getType());
                    return JSONUtils.toJSONString(props);
                }).collect(Collectors.toList());
            HashMap<String, List<String>> hiveMetaData = new HashMap<>();

            hiveMetaData.put("org.starfish.HiveTable", instanceInfo);
            hiveMetaData.put("org.starfish.HiveColumn", fieldMetaInsatnceList);
            dto.setInstances(hiveMetaData);
            List<BatchMetaDataDTO.ClassDTO> types = new ArrayList<>();

            BatchMetaDataDTO.ClassDTO hiveTable = new BatchMetaDataDTO.ClassDTO();
            hiveTable.setName("HiveTable");
            hiveTable.setPackagePath("org.starfish");
            List<BatchMetaDataDTO.AttributeDTO> attrList = geHiveMetaClassInfo(table, fields);
            hiveTable.setAttributes(attrList);
            types.add(hiveTable);

            BatchMetaDataDTO.ClassDTO hiveColumn = new BatchMetaDataDTO.ClassDTO();
            hiveColumn.setPackagePath("org.starfish");
            hiveColumn.setName("HiveColumn");
            hiveColumn.setAttributes(new ArrayList<>());
            BatchMetaDataDTO.AttributeDTO tableNameAttr = new BatchMetaDataDTO.AttributeDTO();
            tableNameAttr.setName("columnName");
            tableNameAttr.setArray(false);
            tableNameAttr.setClassName("String");
            tableNameAttr.setRelType("PRIMITIVE");
            hiveColumn.getAttributes().add(tableNameAttr);
            BatchMetaDataDTO.AttributeDTO columnsAttr = new BatchMetaDataDTO.AttributeDTO();
            columnsAttr.setRelType("PRIMITIVE");
            columnsAttr.setClassName("String");
            columnsAttr.setArray(false);
            columnsAttr.setName("columnType");
            hiveColumn.getAttributes().add(columnsAttr);
            types.add(hiveColumn);
            dto.setTypes(types);
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * get meta information about hive table class
     *
     * @param table
     * @param fields
     * @return
     */
    private List<BatchMetaDataDTO.AttributeDTO> geHiveMetaClassInfo(Object table, Field[] fields) {
        List<BatchMetaDataDTO.AttributeDTO> attrList = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            if (hiveMetaColumn.contains(field.getName())) {
                BatchMetaDataDTO.AttributeDTO props = new BatchMetaDataDTO.AttributeDTO();
                try {
                    if (field.get(table) instanceof Collection) {
                        props.setArray(true);
                    } else {
                        props.setArray(false);
                    }
                } catch (IllegalAccessException e) {
                    throw new IngestionException(e.getMessage());
                }
                props.setName(field.getName());
                props.setClassName(field.getType().getName());
                props.setRelType("PRIMITIVE");
                attrList.add(props);
            }
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
                        hiveMetaData.add(JSONUtils.toJSONString(partitionKeysInstance));
                    } else {
                        metaInstance.put(field.getName(), field.get(table));
                    }
                    hiveMetaData.add(JSONUtils.toJSONString(metaInstance));
                }
            } catch (IllegalAccessException e) {
                throw new IngestionException(e.getMessage());
            }
        }
        return hiveMetaData;
    }
}
