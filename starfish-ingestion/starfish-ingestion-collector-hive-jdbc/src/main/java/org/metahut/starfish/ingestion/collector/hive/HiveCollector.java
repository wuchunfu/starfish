package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.IngestionException;
import org.metahut.starfish.ingestion.common.MetaMessageProducer;
import org.metahut.starfish.message.api.MessageProducer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

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
        Connection conn = hiveMetaDataSource.getConnection();
        CollectorResult collectorResult = new CollectorResult();
        try {
            if (conn.isValid(1)) {
                collectorResult.setState(true);
                collectorResult.setMessage("连接成功！");
                return collectorResult;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

        private DatabaseMetaData databaseMetaData = null;
        private Connection connection = null;
        private Statement stmt = null;

        HiveMetaDataSource() {
            try {
                java.lang.Class.forName(hiveParameter.getDriverName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public Connection getConnection() {
            try {
                connection =
                    hiveParameter.getOnSecure() ? DriverManager
                        .getConnection(hiveParameter.getUrl(), hiveParameter.getUser(),
                            hiveParameter.getPassword())
                        : DriverManager
                            .getConnection(hiveParameter.getUrl());
                stmt = connection.createStatement();
            } catch (SQLException throwables) {
                throw new IngestionException("get hive connection is error:", throwables);
            }
            return connection;
        }

        public DatabaseMetaData getDatabaseMetaData() {
            try {
                if (Objects.isNull(connection)) {
                    getConnection();
                }
                databaseMetaData = connection.getMetaData();
            } catch (SQLException throwables) {
                throw new IngestionException("get hive DataMetaData is error:", throwables);
            }
            return databaseMetaData;
        }

        public void createDatabase() throws Exception {
            getConnection();
            String sql = "create database hive_jdbc_test";
            stmt.execute(sql);
        }

        public void createTable() throws Exception {
            getConnection();
            String sql = "create table emp(\n"
                + "empno int,\n"
                + "ename string,\n"
                + "job string,\n"
                + "mgr int,\n"
                + "hiredate string,\n"
                + "sal double,\n"
                + "comm double,\n"
                + "deptno int\n"
                + ")\n"
                + "row format delimited fields terminated by '\\t'";
            stmt.execute(sql);
        }

        public boolean close() {
            Boolean closeCon = false;
            try {
                connection.close();
                closeCon = connection.isClosed() ? true : false;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return closeCon;
        }
    }
}
