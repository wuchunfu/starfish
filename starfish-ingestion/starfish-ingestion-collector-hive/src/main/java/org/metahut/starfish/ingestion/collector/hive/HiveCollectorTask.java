package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollectorTask;
import org.metahut.starfish.ingestion.common.MetaClient;
import org.metahut.starfish.message.api.IMessageProducer;

import org.apache.hadoop.hive.metastore.IMetaStoreClient;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class HiveCollectorTask implements ICollectorTask {

    private final HiveCollectorAdapter adapter;
    private final IMessageProducer producer;
    private final IMetaStoreClient metaStoreClient;

    public HiveCollectorTask(HiveCollectorAdapter adapter, HiveCollectorTaskParameter parameter) {
        this.adapter = adapter;
        metaStoreClient = this.adapter.getMetaClient();
        producer = MetaClient.getInstance().getMessageProducer();
    }

    @Override
    public void close() throws Exception {
        if (Objects.nonNull(producer)) {
            producer.close();
        }

        if (Objects.nonNull(adapter)) {
            adapter.close();
        }
    }

    private List<String> hiveMetaColumn = null;
    private final String partitionKeys = "partitionKeys";
    private final String partitionStatus = "partitionStatus";
    private static Properties properties = new Properties();

    @Override
    public CollectorResult execute() {
        CollectorResult collectorResult = new CollectorResult();

        ////transform the message
        //BatchMetaDataDTO hiveMessages = getMsg();
        //try {
        //    producer.send("metaData", JSONUtils.toJSONString(hiveMessages));
        //} catch (Exception e) {
        //    throw new CollectorException(e.getMessage());
        //}
        collectorResult.setState(true);
        collectorResult.setMessage("send hive messages is success");
        return collectorResult;
    }

    private void deleteNonExistentMetadata() {
        // 查询全部，删除事件
    }

    private void generateHiveClusterEntity() {

    }

    private void generateHiveDBEntities() {

    }

    private void generateHiveTableEntities() {

    }

    //table metaData
    //@Override
    //public BatchMetaDataDTO getMsg() {
    //    Map<String, String> hiveMetaData = new HashMap<>();
    //    List<HashMap<String, Object>> instanceInfoList = new ArrayList<>();
    //    List<List<HashMap<String, Object>>> fieldMetaInsatnceList = new ArrayList<>();
    //    BatchMetaDataDTO dto = new BatchMetaDataDTO();
    //    dto.setSourceName("Hive");
    //    dataBaseTableMap.entrySet().stream().map(Entry::getValue
    //    ).flatMap(Collection::stream).collect(Collectors.toList()).stream().forEach(table -> {
    //        Class<? extends Table> clazz = table.getClass();
    //        Field[] fields = clazz.getDeclaredFields();
    //        List<HashMap<String, Object>> fieldMetaInstanceList = new ArrayList<>();
    //        HashMap<String, Object> instanceInfo = geHiveMetaInstance(table, fields);
    //        instanceInfo.put("column", fieldMetaInstanceList);
    //        instanceInfoList.add(instanceInfo);
    //    });
    //    hiveMetaData = new HashMap<>();
    //    hiveMetaData.put("org.starfish.HiveTable", JSONUtils.toJSONString(instanceInfoList));
    //    dto.setInstances(hiveMetaData);
    //    return dto;
    //}
    //
    //private Map<String, List<Table>> getTableMap() {
    //    Map<String, List<Table>> dataBase = new HashMap<>();
    //    try {
    //        metaStoreClient.getAllDatabases().stream().forEach(dbName -> {
    //            try {
    //                List<Table> tables = new ArrayList<>();
    //                metaStoreClient.getAllTables(dbName).forEach(tbName -> {
    //                    try {
    //                        tables.add(metaStoreClient.getTable(dbName, tbName));
    //                    } catch (TException e) {
    //                        throw new CollectorException(e.getMessage());
    //                    }
    //                });
    //                dataBase.put(dbName, tables);
    //            } catch (TException e) {
    //                throw new CollectorException(e.getMessage());
    //            }
    //        });
    //    } catch (TException e) {
    //        throw new CollectorException(e.getMessage());
    //    }
    //    return dataBase;
    //}
    //
    //@Override
    //public BatchSchemaDTO getClassInfo() {
    //    BatchSchemaDTO schemaDTO = new BatchSchemaDTO();
    //    BatchSchemaDTO.SourceBodyDTO sourceBodyDTO = new BatchSchemaDTO.SourceBodyDTO();
    //    sourceBodyDTO.setName("Hive");
    //    schemaDTO.setSource(sourceBodyDTO);
    //    List<BatchSchemaDTO.ClassDTO> types = new ArrayList<>();
    //    Table table = dataBaseTableMap.entrySet().stream().map(Entry::getValue
    //    ).flatMap(Collection::stream).collect(Collectors.toList()).stream().findFirst().get();
    //    BatchSchemaDTO.ClassDTO tableClassDTO = new BatchSchemaDTO.ClassDTO();
    //    tableClassDTO.setName("HiveTable");
    //    tableClassDTO.setPackagePath("org.starfish");
    //    List<BatchSchemaDTO.AttributeDTO> attributeDTOList = getAttributeDTOS(table);
    //    BatchSchemaDTO.AttributeDTO columnAttributeDTO = new BatchSchemaDTO.AttributeDTO();
    //    columnAttributeDTO.setClassName("org.starfish.HiveColumn");
    //    columnAttributeDTO.setName("HiveColumn");
    //    columnAttributeDTO.setRelType("ENTITY");
    //    columnAttributeDTO.setArray(false);
    //    attributeDTOList.add(columnAttributeDTO);
    //    tableClassDTO.setAttributes(attributeDTOList);
    //    tableClassDTO.setNameAttributeRel("tableName");
    //    types.add(tableClassDTO);
    //    BatchSchemaDTO.ClassDTO columnClassDTO = new BatchSchemaDTO.ClassDTO();
    //    columnClassDTO.setAttributes(new ArrayList<>());
    //    columnClassDTO.setName("HiveColumn");
    //    columnClassDTO.setPackagePath("org.starfish");
    //    BatchSchemaDTO.AttributeDTO columnNameAttr = new BatchSchemaDTO.AttributeDTO();
    //    columnNameAttr.setName("columnName");
    //    columnNameAttr.setArray(false);
    //    columnNameAttr.setClassName("String");
    //    columnNameAttr.setRelType("PRIMITIVE");
    //    columnClassDTO.getAttributes().add(columnNameAttr);
    //    BatchSchemaDTO.AttributeDTO columnTypeAttr = new BatchSchemaDTO.AttributeDTO();
    //    columnTypeAttr.setRelType("PRIMITIVE");
    //    columnTypeAttr.setClassName("String");
    //    columnTypeAttr.setArray(false);
    //    columnTypeAttr.setName("columnType");
    //    columnClassDTO.getAttributes().add(columnTypeAttr);
    //    BatchSchemaDTO.AttributeDTO tableAttributeDTO = new BatchSchemaDTO.AttributeDTO();
    //    tableAttributeDTO.setClassName("org.starfish.HiveTable");
    //    tableAttributeDTO.setName("HiveTable");
    //    tableAttributeDTO.setRelType("ENTITY");
    //    tableAttributeDTO.setArray(false);
    //    columnClassDTO.getAttributes().add(tableAttributeDTO);
    //    columnClassDTO.setNameAttributeRel("columnName");
    //    types.add(columnClassDTO);
    //
    //    schemaDTO.setTypes(types);
    //
    //    return schemaDTO;
    //}
    //
    //private List<BatchSchemaDTO.AttributeDTO> getAttributeDTOS(Table table) {
    //    List<BatchSchemaDTO.AttributeDTO> attributeDTOList = Arrays.stream(table.getClass().getDeclaredFields()).map(field -> {
    //        if (hiveMetaColumn.contains(field.getName())) {
    //            BatchSchemaDTO.AttributeDTO attributeDTO = new BatchSchemaDTO.AttributeDTO();
    //            field.setAccessible(true);
    //            try {
    //                if (field.get(table) instanceof Collection) {
    //                    attributeDTO.setArray(true);
    //                } else {
    //                    attributeDTO.setArray(false);
    //                }
    //            } catch (IllegalAccessException e) {
    //                e.printStackTrace();
    //            }
    //            attributeDTO.setName(field.getName());
    //            attributeDTO.setClassName(field.getType().getName());
    //            attributeDTO.setRelType("PRIMITIVE");
    //            return attributeDTO;
    //        }
    //        return null;
    //    }).filter(Objects::nonNull).collect(Collectors.toList());
    //    return attributeDTOList;
    //}
    //
    ///**
    // * get meta information about hive table instance
    // *
    // * @param table
    // * @param fields
    // * @return
    // */
    //private HashMap<String, Object> geHiveMetaInstance(Table table, Field[] fields) {
    //    HashMap<String, Object> metaInstance = new HashMap<>();
    //    for (Field field : fields) {
    //        try {
    //            if (hiveMetaColumn.contains(field.getName())) {
    //                field.setAccessible(true);
    //                if (partitionKeys.equals(field.getName())) {
    //                    HashMap<String, Object> partitionStatusInstance = new HashMap<>();
    //                    partitionStatusInstance
    //                            .put(partitionStatus,
    //                                    table.getPartitionKeys().size() > 0 ? true : false);
    //                    HashMap<String, Object> partitionKeysInstance = new HashMap<>();
    //                    partitionKeysInstance.put(partitionKeys,
    //                            Objects.nonNull(table.getPartitionKeys()) ? table.getPartitionKeys()
    //                                    : new ArrayList<>());
    //                } else {
    //                    metaInstance.put(field.getName(), field.get(table));
    //                }
    //            }
    //        } catch (IllegalAccessException e) {
    //            throw new CollectorException(e.getMessage());
    //        }
    //    }
    //    return metaInstance;
    //}
}
