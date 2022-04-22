package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.datasource.hive.HiveDatasourceParameter;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.JSONUtils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.RetryingMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.thrift.TException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Disabled
public class HiveCollectorTest {

    Connection conn = null;
    Configuration conf = null;
    IMetaStoreClient hmsClient = null;

    @BeforeEach
    public void init() {
        try {
            Class.forName("XXX");
            // 创建连接
            conn = DriverManager
                .getConnection("XXX");
            //创建元数据客户端
            conf = new Configuration();
            conf.set("hive.metastore.uris", "XXX");
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
    public void execute() {
        HiveCollectorParameter hiveCollectorParameter = new HiveCollectorParameter();
        HiveDatasourceParameter hiveDatasourceParameter = new HiveDatasourceParameter();
        hiveCollectorParameter.setDatasourceId("XXX");
        hiveDatasourceParameter.setDriverClassName("XXXX");
        hiveDatasourceParameter.setJdbcUrl("XXXX");
        hiveCollectorParameter
            .setDatasourceParameter(JSONUtils.toJSONString(hiveDatasourceParameter));
        CollectorResult result = new HiveCollectorManager().generateInstance(hiveCollectorParameter)
            .execute();
        Assertions.assertNotNull(result);
    }

}
