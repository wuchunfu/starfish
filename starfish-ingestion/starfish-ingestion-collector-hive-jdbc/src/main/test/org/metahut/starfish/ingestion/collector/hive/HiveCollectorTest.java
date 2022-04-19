package org.metahut.starfish.ingestion.collector.hive;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.RetryingMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.api.Partition;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.pulsar.shade.com.google.common.collect.Lists;
import org.apache.thrift.TException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HiveCollectorTest {

    Connection conn = null;
    Configuration conf = null;
    IMetaStoreClient hmsClient = null;

    @BeforeEach
    public void init() {
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            // 创建连接
            conn = DriverManager
                .getConnection("jdbc:hive2://172.21.100.219:10000");
            //创建元数据客户端
            conf = new Configuration();
            conf.set("hive.metastore.uris", "thrift://172.21.100.231:9083");
            hmsClient = RetryingMetaStoreClient.getProxy(conf, false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (MetaException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHive3Client() {
        try {
            Assertions.assertEquals(conn.isClosed(), false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testHive3MetaClient() {
        List<String> dataBases = null;
        try {
            dataBases = hmsClient.getAllDatabases();
        } catch (TException e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(dataBases);
    }

    @Test
    public void getDataBase() {
        List<Database> dataBases = null;
        try {
            dataBases = hmsClient.getAllDatabases().stream().map(database -> {
                try {
                    return hmsClient.getDatabase(database);
                } catch (TException e) {
                    e.printStackTrace();
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (TException e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(dataBases);
    }

    @Test
    public void getTables() {
        List<Table> tables = null;
        try {
            tables = hmsClient.getTables("default", "*").stream().map(table -> {
                try {
                    return hmsClient.getTable("default", table);
                } catch (TException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
        } catch (TException e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(tables);
    }

    @Test
    public void getpartitions() {

    }

    @Test
    public void createPartitions() {
        Partition partition = new Partition(Lists.newArrayList("part1"), "dmm", "test_obs", 1650354547, 1650354547, null, null);
        try {
            hmsClient.add_partition(partition);
        } catch (TException e) {
            e.printStackTrace();
        }
    }

}
