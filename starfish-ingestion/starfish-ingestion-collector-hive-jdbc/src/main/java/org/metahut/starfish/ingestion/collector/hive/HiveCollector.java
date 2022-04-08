package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.IngestionException;

import org.mortbay.log.Log;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class HiveCollector implements ICollector {

    private final HiveParameter hiveParameter;

    //private final MessageProducer producer;

    public HiveCollector(HiveParameter hiveParameter) {
        this.hiveParameter = hiveParameter;
        //producer = MetaMessageProducer.getInstance();
    }

    public void pull() {
        HiveMetaDataSource hiveMetaDataSource = new HiveMetaDataSource();
        //get hive metaData
        DatabaseMetaData databaseMetaData = hiveMetaDataSource.getDatabaseMetaData();
        ResultSet tablesResultSet = null;
        Connection conn = hiveMetaDataSource.getConnection();
        try {
            //get hive table’s meta
            tablesResultSet = databaseMetaData
                .getTables(conn.getCatalog(), null, null, new String[]{"TABLE"});
            while (tablesResultSet.next()) {
                String tableName = tablesResultSet.getString("TABLE_NAME");
                String tableType = tablesResultSet.getString("TABLE_TYPE");
                String remarks = tablesResultSet.getString("REMARKS");
                Log.info("TABLE_NAME：" + tableName + "   TABLE_TYPE： " + tableType + "   REMARKS："
                    + remarks);
                ResultSetMetaData resultSetMetaData = tablesResultSet.getMetaData();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    Log.info(
                        tablesResultSet.getString(i) + "-==-" + resultSetMetaData.getColumnName(i));
                }
                ResultSet resultSet = databaseMetaData
                    .getColumns(conn.getCatalog(), null, tableName,
                        null);
                while (resultSet.next()) {
                    for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                        Log.info(resultSet.getMetaData().getColumnName(i + 1) + ":" + resultSet
                            .getString(i + 1) + ":" + resultSet.getString("column_name"));
                    }
                    Log.info(resultSet.getString("TABLE_NAME")
                        + "-" + resultSet.getString("column_name")
                        + "-" + resultSet.getString("TYPE_NAME")
                        + "-" + resultSet.getString("DATA_TYPE")
                        + "-" + resultSet.getString("COLUMN_SIZE")
                        + "-" + resultSet.getString("DECIMAL_DIGITS")
                        + "-" + resultSet.getString("COLUMN_DEF")
                        + "-" + resultSet.getString("REMARKS")
                        + "-" + resultSet.getString("ORDINAL_POSITION"));
                }
            }
        } catch (SQLException throwables) {
            throw new IngestionException("hive DataMetaData is wrong:", throwables);

        }

        hiveMetaDataSource.close();
    }

    @Override
    public void close() throws Exception {
        //producer.close();
    }

    //hive datasource Object
    class HiveMetaDataSource {

        private DatabaseMetaData databaseMetaData = null;
        private Connection connection = null;
        private Statement stmt = null;

        HiveMetaDataSource() {
            try {
                Class.forName(hiveParameter.getDriverName());
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
