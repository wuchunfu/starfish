package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.datasource.hive.HiveDatasource;
import org.metahut.starfish.datasource.hive.HiveDatasourceManager;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.common.MetaMessageProducer;
import org.metahut.starfish.message.api.MessageProducer;

import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.Partition;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.thrift.TException;

import java.util.List;
import java.util.Objects;

public class HiveCollector implements ICollector {

    private final HiveCollectorParameter hiveParameter;
    private final MessageProducer producer;
    private final HiveDatasource hiveDatasource;
    private final IMetaStoreClient metaClient;

    public HiveCollector(HiveCollectorParameter hiveParameter) {
        this.hiveParameter = hiveParameter;
        producer = MetaMessageProducer.getInstance();
        hiveDatasource = new HiveDatasourceManager().generateInstance(this.hiveParameter.getDatasourceParameter());
        metaClient = hiveDatasource.getMetaClient();
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
        return null;
    }

    //database metadata
    public List<String> getDataBaseList() {
        try {
            return metaClient.getAllDatabases();
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Database getDataBase(String database) {
        try {
            return metaClient.getDatabase(database);
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }

    //table metaData
    public List<String> getTableList(String database) {
        try {
            return metaClient.getTables(database,"*");
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Table getTable(String database,String table) {
        try {
            return metaClient.getTable(database,table);
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }

    //partition metaData
    public List<Partition> getPartitionList(String dataBase, String tableName, List<String> partNames) {
        try {
            return metaClient.getPartitionsByNames(dataBase,tableName,partNames);
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Partition getPartition(String dataBase, String table, List<String> partNames) {
        try {
            return metaClient.getPartition(dataBase, table, partNames);
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }

}
