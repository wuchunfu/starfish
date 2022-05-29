package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollectorTask;
import org.metahut.starfish.ingestion.collector.hive.models.HiveCluster;
import org.metahut.starfish.ingestion.collector.hive.models.HiveColumn;
import org.metahut.starfish.ingestion.collector.hive.models.HiveDB;
import org.metahut.starfish.ingestion.collector.hive.models.HiveTable;
import org.metahut.starfish.ingestion.common.JSONUtils;
import org.metahut.starfish.ingestion.common.MetaClient;
import org.metahut.starfish.ingestion.common.data.EntityRow;
import org.metahut.starfish.ingestion.common.data.RowData;
import org.metahut.starfish.message.api.IMessageProducer;
import org.metahut.starfish.unit.enums.RowKind;
import org.metahut.starfish.unit.row.EntityHeader;
import org.metahut.starfish.unit.row.RelationRow;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

import static org.metahut.starfish.ingestion.collector.hive.Constants.COLLECTOR_TYPE;
import static org.metahut.starfish.ingestion.collector.hive.Constants.PROPERTY_TABLE_COMMENT;
import static org.metahut.starfish.ingestion.collector.hive.Constants.RELATION_PROPERTY_CLUSTER_DB;
import static org.metahut.starfish.ingestion.collector.hive.Constants.RELATION_PROPERTY_COLUMN_TABLE;
import static org.metahut.starfish.ingestion.collector.hive.Constants.RELATION_PROPERTY_DB_CLUSTER;
import static org.metahut.starfish.ingestion.collector.hive.Constants.RELATION_PROPERTY_DB_TABLE;
import static org.metahut.starfish.ingestion.collector.hive.Constants.RELATION_PROPERTY_TABLE_COLUMN;
import static org.metahut.starfish.ingestion.collector.hive.Constants.RELATION_PROPERTY_TABLE_DB;
import static org.metahut.starfish.ingestion.collector.hive.Constants.RELATION_PROPERTY_TABLE_PARTITION_KEY;
import static org.metahut.starfish.ingestion.collector.hive.Constants.TYPE_NAME_CLUSTER;
import static org.metahut.starfish.ingestion.collector.hive.Constants.TYPE_NAME_COLUMN;
import static org.metahut.starfish.ingestion.collector.hive.Constants.TYPE_NAME_DB;
import static org.metahut.starfish.ingestion.collector.hive.Constants.TYPE_NAME_TABLE;
import static org.metahut.starfish.unit.EntityNameGentrator.generateName;

public class HiveCollectorTask implements ICollectorTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(HiveCollectorTask.class);

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

    @Override
    public CollectorResult execute() {

        EntityHeader clusterHeader = generateHiveClusterEntity();

        generateHiveDBEntities(clusterHeader);

        CollectorResult collectorResult = new CollectorResult();

        collectorResult.setState(true);
        collectorResult.setMessage("send hive messages is success");
        return collectorResult;
    }

    private void sendMessage(String key, Object value) {
        producer.send(key, JSONUtils.toJSONString(value));
    }

    private void sendMessage(Object value) {
        sendMessage(this.parameter.getClusterName(), value);
    }

    private void deleteNonExistentMetadata() {
        // TODO Query the existing metadata in the system and call the Hive interface to determine whether it exists

    }

    private EntityHeader generateHiveClusterEntity() {
        LOGGER.info("generate hive cluster entity: {}", this.parameter.getClusterName());
        HiveCluster hiveCluster = new HiveCluster();
        hiveCluster.setName(this.parameter.getClusterName());
        hiveCluster.setType(COLLECTOR_TYPE);

        EntityHeader entityHeader = generateClusterEntityHeader();

        // HiveCluster entity
        RowData rowData = new RowData();
        rowData.getEntities().add(EntityRow.of(RowKind.UPSERT, entityHeader, hiveCluster));
        sendMessage(rowData);
        return entityHeader;
    }

    private void generateHiveDBEntities(EntityHeader clusterHeader) {
        try {
            List<String> allDatabases = metaStoreClient.getAllDatabases();
            if (CollectionUtils.isEmpty(allDatabases)) {
                return;
            }

            RowData rowData = new RowData();
            for (String dbName : allDatabases) {
                EntityHeader entityHeader = generateHiveDBEntity(clusterHeader, dbName);

                // HiveCluster -> dbs -> HiveDB
                rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, clusterHeader, entityHeader, RELATION_PROPERTY_CLUSTER_DB));
            }

            sendMessage(rowData);
        } catch (TException e) {
            // TODO exception handler
            throw new RuntimeException(e);
        }
    }

    private EntityHeader generateHiveDBEntity(EntityHeader clusterHeader, String dbName) throws TException {
        LOGGER.info("generate hive db entity: {}", dbName);
        Database database = metaStoreClient.getDatabase(dbName);

        HiveDB hiveDB = new HiveDB();
        hiveDB.setName(database.getName());
        hiveDB.setDescription(database.getDescription());
        hiveDB.setLocation(database.getLocationUri());
        hiveDB.setOwner(database.getOwnerName());
        hiveDB.setParameters(hiveDB.getParameters());

        EntityHeader entityHeader = generateDBEntityHeader(dbName);

        RowData rowData = new RowData();
        rowData.getEntities().add(EntityRow.of(RowKind.UPSERT, entityHeader, hiveDB));
        rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, entityHeader, clusterHeader, RELATION_PROPERTY_DB_CLUSTER));
        sendMessage(rowData);

        // generate HiveTable entity
        generateHiveTableEntities(entityHeader, dbName);
        return entityHeader;
    }

    private void generateHiveTableEntities(EntityHeader dbHeader, String dbName) {
        try {
            List<String> allTables = metaStoreClient.getAllTables(dbName);
            if (CollectionUtils.isEmpty(allTables)) {
                return;
            }

            RowData rowData = new RowData();
            for (String tableName : allTables) {
                EntityHeader entityHeader = generateHiveTableEntity(dbHeader, dbName, tableName);
                // HiveDB -> tables -> HiveTable
                rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, dbHeader, entityHeader, RELATION_PROPERTY_DB_TABLE));
            }

            sendMessage(rowData);

        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    private EntityHeader generateHiveTableEntity(EntityHeader dbHeader, String dbName, String tableName) {
        LOGGER.info("generate hive table entity: {}", tableName);

        try {
            Table table = metaStoreClient.getTable(dbName, tableName);

            HiveTable hiveTable = new HiveTable();
            hiveTable.setName(tableName);
            hiveTable.setComment(table.getParameters().get(PROPERTY_TABLE_COMMENT));
            hiveTable.setCreateTime(table.getCreateTime());
            hiveTable.setLastAccessTime(table.getLastAccessTime() > 0 ? table.getLastAccessTime() : table.getCreateTime());
            hiveTable.setOwner(table.getTableType());
            hiveTable.setTemporary(table.isTemporary());

            EntityHeader entityHeader = generateTableEntityHeader(dbHeader, tableName);

            RowData rowData = new RowData();
            rowData.getEntities().add(EntityRow.of(RowKind.UPSERT, entityHeader, hiveTable));

            // HiveTable --> db --> HiveDB
            rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, entityHeader, dbHeader, RELATION_PROPERTY_TABLE_DB));
            generateHiveColumnEntities(rowData, entityHeader, table);

            return entityHeader;

        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateHiveColumnEntities(RowData rowData, EntityHeader tableHeader, Table table) {
        List<FieldSchema> partitionKeys = table.getPartitionKeys();
        for (FieldSchema partitionKey : partitionKeys) {
            EntityHeader entityHeader = generateHiveColumnEntity(rowData, tableHeader, partitionKey);

            // HiveTable --> partitionKeys --> HiveColumn
            rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, tableHeader, entityHeader, RELATION_PROPERTY_TABLE_PARTITION_KEY));
        }

        // TODO how to get column
        List<FieldSchema> cols = table.getSd().getCols();

        for (FieldSchema col : cols) {
            EntityHeader entityHeader = generateHiveColumnEntity(rowData, tableHeader, col);

            // HiveTable --> columns --> HiveColumn
            rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, tableHeader, entityHeader,RELATION_PROPERTY_TABLE_COLUMN));
        }

    }

    private EntityHeader generateHiveColumnEntity(RowData rowData, EntityHeader tableHeader, FieldSchema schema) {
        LOGGER.info("generate hive column entity: {}", schema.getName());

        HiveColumn hiveColumn = new HiveColumn();
        hiveColumn.setName(schema.getName());
        hiveColumn.setType(schema.getType());
        hiveColumn.setComment(schema.getComment());

        EntityHeader entityHeader = generateColumnEntityHeader(tableHeader, schema.getName());
        rowData.getEntities().add(EntityRow.of(RowKind.UPSERT, entityHeader, hiveColumn));

        // HiveColumn --> table --> HiveTable
        rowData.getRelations().add(RelationRow.of(RowKind.UPSERT, entityHeader, tableHeader, RELATION_PROPERTY_COLUMN_TABLE));
        return entityHeader;
    }

    private EntityHeader generateClusterEntityHeader() {
        return generateEntityHeader(TYPE_NAME_CLUSTER, TYPE_NAME_CLUSTER, this.parameter.getClusterName());
    }

    private EntityHeader generateDBEntityHeader(String dbName) {
        return generateEntityHeader(TYPE_NAME_DB, this.parameter.getClusterName(), dbName);
    }

    private EntityHeader generateTableEntityHeader(EntityHeader dbHeader, String tableName) {
        return generateEntityHeader(TYPE_NAME_TABLE, dbHeader.getQualifiedName(), tableName);
    }

    private EntityHeader generateColumnEntityHeader(EntityHeader tableHeader, String columnName) {
        return generateEntityHeader(TYPE_NAME_COLUMN, tableHeader.getQualifiedName(), columnName);
    }

    private EntityHeader generateEntityHeader(String typeName, String... args) {
        return EntityHeader.of(typeName, generateName(args));
    }

}
