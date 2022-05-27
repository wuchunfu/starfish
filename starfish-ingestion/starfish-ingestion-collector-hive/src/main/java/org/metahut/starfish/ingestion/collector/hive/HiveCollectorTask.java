package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollectorTask;
import org.metahut.starfish.ingestion.collector.hive.models.HiveCluster;
import org.metahut.starfish.ingestion.collector.hive.models.HiveDB;
import org.metahut.starfish.ingestion.common.JSONUtils;
import org.metahut.starfish.ingestion.common.MetaClient;
import org.metahut.starfish.message.api.IMessageProducer;
import org.metahut.starfish.unit.enums.RowKind;
import org.metahut.starfish.unit.row.EntityHeader;
import org.metahut.starfish.unit.row.EntityRow;
import org.metahut.starfish.unit.row.RelationRow;

import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.thrift.TException;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static org.metahut.starfish.ingestion.collector.hive.Constants.COLLECTOR_TYPE;
import static org.metahut.starfish.ingestion.collector.hive.Constants.RELATION_PROPERTY_DB_CLUSTER;
import static org.metahut.starfish.ingestion.collector.hive.Constants.TYPE_NAME_CLUSTER;
import static org.metahut.starfish.ingestion.collector.hive.Constants.TYPE_NAME_DB;
import static org.metahut.starfish.ingestion.common.EntityUtils.generateName;

public class HiveCollectorTask implements ICollectorTask {

    private final HiveCollectorAdapter adapter;
    private final IMessageProducer producer;
    private final IMetaStoreClient metaStoreClient;

    private final HiveCollectorTaskParameter parameter;

    public HiveCollectorTask(HiveCollectorAdapter adapter, HiveCollectorTaskParameter parameter) {
        this.adapter = adapter;
        this.metaStoreClient = this.adapter.getMetaClient();
        this.producer = MetaClient.getInstance().getMessageProducer();
        this.parameter = parameter;
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

        collectorResult.setState(true);
        collectorResult.setMessage("send hive messages is success");
        return collectorResult;
    }

    private void sendMessage(String key, Object value) {
        producer.send(key, JSONUtils.toJSONString(value));
    }

    private void deleteNonExistentMetadata() {
        // TODO Query the existing metadata in the system and call the Hive interface to determine whether it exists

    }

    private EntityRow<HiveCluster> generateHiveClusterEntity() {
        HiveCluster hiveCluster = new HiveCluster();
        hiveCluster.setName(this.parameter.getClusterName());
        hiveCluster.setType(COLLECTOR_TYPE);

        String qualifiedName = generateName(TYPE_NAME_CLUSTER, this.parameter.getClusterName());
        EntityHeader entityHeader = EntityHeader.of(TYPE_NAME_CLUSTER, qualifiedName);
        return EntityRow.of(RowKind.UPSERT, entityHeader, hiveCluster);
    }

    private void generateHiveDBEntities() {
        try {
            List<String> allDatabases = metaStoreClient.getAllDatabases();

        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateHiveDBEntity(String dbName) throws TException {
        Database database = metaStoreClient.getDatabase(dbName);

        HiveDB hiveDB = new HiveDB();
        hiveDB.setName(database.getName());
        hiveDB.setDescription(database.getDescription());
        hiveDB.setLocation(database.getLocationUri());
        hiveDB.setOwner(database.getOwnerName());
        hiveDB.setParameters(hiveDB.getParameters());

        String qualifiedName = generateName(TYPE_NAME_CLUSTER, this.parameter.getClusterName());
        EntityHeader entityHeader = EntityHeader.of(TYPE_NAME_DB, qualifiedName);
        EntityRow<HiveDB> of = EntityRow.of(RowKind.UPSERT, entityHeader, hiveDB);

        RelationRow.of(RowKind.UPSERT, entityHeader,null, RELATION_PROPERTY_DB_CLUSTER);
    }

    private void generateHiveTableEntities() {

    }

    private EntityHeader generateClusterEntityHeader() {
        return generateEntityHeader(TYPE_NAME_CLUSTER, TYPE_NAME_CLUSTER, this.parameter.getClusterName());
    }

    private EntityHeader generateDBEntityHeader(String dbName) {
        return generateEntityHeader(TYPE_NAME_CLUSTER, TYPE_NAME_CLUSTER, this.parameter.getClusterName());
    }

    private EntityHeader generateEntityHeader(String typeName, String... args) {
        return EntityHeader.of(typeName, generateName(args));
    }

//    table metaData
//    @Override
//    public BatchMetaDataDTO getMsg() {
//        Map<String, String> hiveMetaData = new HashMap<>();
//        List<HashMap<String, Object>> instanceInfoList = new ArrayList<>();
//        List<List<HashMap<String, Object>>> fieldMetaInsatnceList = new ArrayList<>();
//        BatchMetaDataDTO dto = new BatchMetaDataDTO();
//        dto.setSourceName("Hive");
//        dataBaseTableMap.entrySet().stream().map(Entry::getValue
//        ).flatMap(Collection::stream).collect(Collectors.toList()).stream().forEach(table -> {
//            Class<? extends Table> clazz = table.getClass();
//            Field[] fields = clazz.getDeclaredFields();
//            List<HashMap<String, Object>> fieldMetaInstanceList = new ArrayList<>();
//            HashMap<String, Object> instanceInfo = geHiveMetaInstance(table, fields);
//            instanceInfo.put("column", fieldMetaInstanceList);
//            instanceInfoList.add(instanceInfo);
//        });
//        hiveMetaData = new HashMap<>();
//        hiveMetaData.put("org.starfish.HiveTable", JSONUtils.toJSONString(instanceInfoList));
//        dto.setInstances(hiveMetaData);
//        return dto;
//    }
//
//    private Map<String, List<Table>> getTableMap() {
//        Map<String, List<Table>> dataBase = new HashMap<>();
//        try {
//            metaStoreClient.getAllDatabases().stream().forEach(dbName -> {
//                try {
//                    List<Table> tables = new ArrayList<>();
//                    metaStoreClient.getAllTables(dbName).forEach(tbName -> {
//                        try {
//                            tables.add(metaStoreClient.getTable(dbName, tbName));
//                        } catch (TException e) {
//                            throw new CollectorException(e.getMessage());
//                        }
//                    });
//                    dataBase.put(dbName, tables);
//                } catch (TException e) {
//                    throw new CollectorException(e.getMessage());
//                }
//            });
//        } catch (TException e) {
//            throw new CollectorException(e.getMessage());
//        }
//        return dataBase;
//    }
//
//    @Override
//    public BatchSchemaDTO getClassInfo() {
//        BatchSchemaDTO schemaDTO = new BatchSchemaDTO();
//        BatchSchemaDTO.SourceBodyDTO sourceBodyDTO = new BatchSchemaDTO.SourceBodyDTO();
//        sourceBodyDTO.setName("Hive");
//        schemaDTO.setSource(sourceBodyDTO);
//        List<BatchSchemaDTO.ClassDTO> types = new ArrayList<>();
//        Table table = dataBaseTableMap.entrySet().stream().map(Entry::getValue
//        ).flatMap(Collection::stream).collect(Collectors.toList()).stream().findFirst().get();
//        BatchSchemaDTO.ClassDTO tableClassDTO = new BatchSchemaDTO.ClassDTO();
//        tableClassDTO.setName("HiveTable");
//        tableClassDTO.setPackagePath("org.starfish");
//        List<BatchSchemaDTO.AttributeDTO> attributeDTOList = getAttributeDTOS(table);
//        BatchSchemaDTO.AttributeDTO columnAttributeDTO = new BatchSchemaDTO.AttributeDTO();
//        columnAttributeDTO.setClassName("org.starfish.HiveColumn");
//        columnAttributeDTO.setName("HiveColumn");
//        columnAttributeDTO.setRelType("ENTITY");
//        columnAttributeDTO.setArray(false);
//        attributeDTOList.add(columnAttributeDTO);
//        tableClassDTO.setAttributes(attributeDTOList);
//        tableClassDTO.setNameAttributeRel("tableName");
//        types.add(tableClassDTO);
//        BatchSchemaDTO.ClassDTO columnClassDTO = new BatchSchemaDTO.ClassDTO();
//        columnClassDTO.setAttributes(new ArrayList<>());
//        columnClassDTO.setName("HiveColumn");
//        columnClassDTO.setPackagePath("org.starfish");
//        BatchSchemaDTO.AttributeDTO columnNameAttr = new BatchSchemaDTO.AttributeDTO();
//        columnNameAttr.setName("columnName");
//        columnNameAttr.setArray(false);
//        columnNameAttr.setClassName("String");
//        columnNameAttr.setRelType("PRIMITIVE");
//        columnClassDTO.getAttributes().add(columnNameAttr);
//        BatchSchemaDTO.AttributeDTO columnTypeAttr = new BatchSchemaDTO.AttributeDTO();
//        columnTypeAttr.setRelType("PRIMITIVE");
//        columnTypeAttr.setClassName("String");
//        columnTypeAttr.setArray(false);
//        columnTypeAttr.setName("columnType");
//        columnClassDTO.getAttributes().add(columnTypeAttr);
//        BatchSchemaDTO.AttributeDTO tableAttributeDTO = new BatchSchemaDTO.AttributeDTO();
//        tableAttributeDTO.setClassName("org.starfish.HiveTable");
//        tableAttributeDTO.setName("HiveTable");
//        tableAttributeDTO.setRelType("ENTITY");
//        tableAttributeDTO.setArray(false);
//        columnClassDTO.getAttributes().add(tableAttributeDTO);
//        columnClassDTO.setNameAttributeRel("columnName");
//        types.add(columnClassDTO);
//
//        schemaDTO.setTypes(types);
//
//        return schemaDTO;
//    }
//
//    private List<BatchSchemaDTO.AttributeDTO> getAttributeDTOS(Table table) {
//        List<BatchSchemaDTO.AttributeDTO> attributeDTOList = Arrays.stream(table.getClass().getDeclaredFields()).map(field -> {
//            if (hiveMetaColumn.contains(field.getName())) {
//                BatchSchemaDTO.AttributeDTO attributeDTO = new BatchSchemaDTO.AttributeDTO();
//                field.setAccessible(true);
//                try {
//                    if (field.get(table) instanceof Collection) {
//                        attributeDTO.setArray(true);
//                    } else {
//                        attributeDTO.setArray(false);
//                    }
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                attributeDTO.setName(field.getName());
//                attributeDTO.setClassName(field.getType().getName());
//                attributeDTO.setRelType("PRIMITIVE");
//                return attributeDTO;
//            }
//            return null;
//        }).filter(Objects::nonNull).collect(Collectors.toList());
//        return attributeDTOList;
//    }
//
//    /**
//     * get meta information about hive table instance
//     *
//     * @param table
//     * @param fields
//     * @return
//     */
//    private HashMap<String, Object> geHiveMetaInstance(Table table, Field[] fields) {
//        HashMap<String, Object> metaInstance = new HashMap<>();
//        for (Field field : fields) {
//            try {
//                if (hiveMetaColumn.contains(field.getName())) {
//                    field.setAccessible(true);
//                    if (partitionKeys.equals(field.getName())) {
//                        HashMap<String, Object> partitionStatusInstance = new HashMap<>();
//                        partitionStatusInstance
//                                .put(partitionStatus,
//                                        table.getPartitionKeys().size() > 0 ? true : false);
//                        HashMap<String, Object> partitionKeysInstance = new HashMap<>();
//                        partitionKeysInstance.put(partitionKeys,
//                                Objects.nonNull(table.getPartitionKeys()) ? table.getPartitionKeys()
//                                        : new ArrayList<>());
//                    } else {
//                        metaInstance.put(field.getName(), field.get(table));
//                    }
//                }
//            } catch (IllegalAccessException e) {
//                throw new CollectorException(e.getMessage());
//            }
//        }
//        return metaInstance;
//    }
}
