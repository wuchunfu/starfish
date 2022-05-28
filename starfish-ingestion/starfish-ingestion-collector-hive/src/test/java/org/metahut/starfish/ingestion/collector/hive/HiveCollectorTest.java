package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.thrift.TException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Disabled
public class HiveCollectorTest {

    Connection conn = null;
    Configuration conf = null;
    IMetaStoreClient hmsClient = null;

    final String typeUrl = "http://localhost:8801/metaData/batchType";
    final String instanceUrl = "http://localhost:8801/metaData/batchInstance";

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

    //@Test
    //public void execute() {
    //    HiveCollectorParameter hiveCollectorParameter = new HiveCollectorParameter();
    //    HiveDatasourceParameter hiveDatasourceParameter = new HiveDatasourceParameter();
    //    hiveDatasourceParameter.setDriverClassName("org.apache.hive.jdbc.HiveDriver");
    //    hiveDatasourceParameter.setJdbcUrl("thrift://172.21.100.231:9083");
    //    hiveCollectorParameter
    //            .setDatasourceParameter(JSONUtils.toJSONString(hiveDatasourceParameter));
    //    CollectorResult result = new HiveCollectorManager().generateInstance(hiveCollectorParameter)
    //            .execute();
    //    Assertions.assertNotNull(result);
    //}
    //
    //@Test
    //public void testGetMsg() {
    //    HiveCollectorParameter hiveCollectorParameter = new HiveCollectorParameter();
    //    HiveDatasourceParameter hiveDatasourceParameter = new HiveDatasourceParameter();
    //    hiveDatasourceParameter.setDriverClassName("org.apache.hive.jdbc.HiveDriver");
    //    hiveDatasourceParameter.setJdbcUrl("thrift://172.21.100.231:9083");
    //    hiveCollectorParameter
    //            .setDatasourceParameter(JSONUtils.toJSONString(hiveDatasourceParameter));
    //    BatchMetaDataDTO result = new HiveCollectorManager()
    //            .generateInstance(hiveCollectorParameter)
    //            .getMsg();
    //    doPostJson(instanceUrl, JSONUtils.toJSONString(result));
    //    Assertions.assertNotNull(result);
    //}
    //
    //@Test
    //public void testGetClassInfo() {
    //    HiveCollectorParameter hiveCollectorParameter = new HiveCollectorParameter();
    //    HiveDatasourceParameter hiveDatasourceParameter = new HiveDatasourceParameter();
    //    hiveDatasourceParameter.setDriverClassName("org.apache.hive.jdbc.HiveDriver");
    //    hiveDatasourceParameter.setJdbcUrl("thrift://172.21.100.231:9083");
    //    hiveCollectorParameter
    //            .setDatasourceParameter(JSONUtils.toJSONString(hiveDatasourceParameter));
    //    BatchSchemaDTO result = new HiveCollectorManager()
    //            .generateInstance(hiveCollectorParameter)
    //            .getClassInfo();
    //    doPostJson(typeUrl, JSONUtils.toJSONString(result));
    //    Assertions.assertNotNull(result);
    //}


    public static String doPostJson(String url, String params) {
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000)
                    .setConnectTimeout(600000).build();
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            StringEntity stringEntity = new StringEntity(params);
            stringEntity.setContentType("text/json");
            httpPost.setEntity(stringEntity);
            return postResponse(httpClientBuilder, httpPost);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String postResponse(HttpClientBuilder httpClientBuilder, HttpPost httpPost) {
        try (CloseableHttpResponse closeableHttpResponse = httpClientBuilder.build()
                .execute(httpPost)) {
            HttpEntity httpEntity = closeableHttpResponse.getEntity();
            return EntityUtils.toString(httpEntity, "UTF-8");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    @Test
    public void testHiveCollectorTask() {
        CollectorResult result = new HiveCollectorManager()
                .generateTaskInstance("{\"hiveMetastoreUris\":\"thrift://172.21.100.231:9083\"}", "{\"clusterName\":\"hive\"}")
                .execute();
        Assertions.assertEquals(true, result.getState());
    }
}
