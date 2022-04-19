package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.common.MetaMessageProducer;
import org.metahut.starfish.message.api.MessageProducer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.RetryingMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.api.Partition;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.thrift.TException;

import java.util.List;

public class HiveCollector implements ICollector {

    private final HiveParameter hiveParameter;

    private final MessageProducer producer;

    public HiveCollector(HiveParameter hiveParameter) {
        this.hiveParameter = hiveParameter;
        producer = MetaMessageProducer.getInstance();
    }

    public void pull() {

    }

    @Override
    public void close() throws Exception {
        producer.close();
    }

    @Override
    public CollectorResult testConnection() {
        HiveMetaDataSource hiveMetaDataSource = new HiveMetaDataSource();
        boolean connectStatus = hiveMetaDataSource.getConnection();
        CollectorResult collectorResult = new CollectorResult();
        if (connectStatus) {
            collectorResult.setState(true);
            collectorResult.setMessage("连接成功！");
            return collectorResult;
        }
        collectorResult.setState(false);
        collectorResult.setMessage("连接失败！");
        return collectorResult;
    }

    @Override
    public CollectorResult execute() {
        return null;
    }

    //hive datasource Object
    class HiveMetaDataSource {

        private boolean connectedStatus = false;

        Configuration conf = null;

        IMetaStoreClient hmsClient = null;

        HiveMetaDataSource() {
            try {
                conf = new Configuration();
                conf.set("hive.metastore.uris", hiveParameter.getUrl());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public boolean getConnection() {
            init();
            return connectedStatus;
        }

        public IMetaStoreClient init() {
            try {
                hmsClient = RetryingMetaStoreClient.getProxy(conf, false);
                connectedStatus = true;
            } catch (MetaException e) {
                e.printStackTrace();
            }
            return hmsClient;
        }

        //database metadata
        public List<String> getDataBaseList() {
            try {
                return hmsClient.getAllDatabases();
            } catch (TException e) {
                e.printStackTrace();
            }
            return null;
        }

        public Database getDataBase(String database) {
            try {
                return hmsClient.getDatabase(database);
            } catch (TException e) {
                e.printStackTrace();
            }
            return null;
        }

        //table metaData
        public List<String> getTableList(String database) {
            try {
                return hmsClient.getTables(database,"*");
            } catch (TException e) {
                e.printStackTrace();
            }
            return null;
        }

        public Table getTable(String database,String table) {
            try {
                return  hmsClient.getTable(database,table);
            } catch (TException e) {
                e.printStackTrace();
            }
            return null;
        }

        //partition metaData
        public List<Partition> getPartitionList(String dataBase, String tableName, List<String> partNames) {
            try {
                return  hmsClient.getPartitionsByNames(dataBase,tableName,partNames);
            } catch (TException e) {
                e.printStackTrace();
            }
            return null;
        }

        public Partition getPartition(String dataBase, String table, List<String> partNames) {
            try {
                return  hmsClient.getPartition(dataBase, table, partNames);
            } catch (TException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
