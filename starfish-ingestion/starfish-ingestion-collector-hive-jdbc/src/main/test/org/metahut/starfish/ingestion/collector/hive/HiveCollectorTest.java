package org.metahut.starfish.ingestion.collector.hive;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class HiveCollectorTest {

    @Test
    @Disabled
    public void pull() {
        HiveCollector hiveCollector = (HiveCollector) new HiveCollectorManager().generateInstance(
            "{\"driverName\":\"org.apache.hive.jdbc.HiveDriver\",\"url\":\"jdbc:hive2://zp-data-hadoop-qa-001:10000\"}");
        hiveCollector.pull();
    }

    @Test
    @Disabled
    public void createDatabase() {
        HiveCollector hiveCollector = (HiveCollector) new HiveCollectorManager().generateInstance(
            "{\"driverName\":\"org.apache.hive.jdbc.HiveDriver\",\"url\":\"jdbc:hive2://zp-data-hadoop-qa-001:10000\"}");
        try {
            hiveCollector.new HiveMetaDataSource().createDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled
    public void createTable() {
        HiveCollector hiveCollector = (HiveCollector) new HiveCollectorManager().generateInstance(
            "{\"driverName\":\"org.apache.hive.jdbc.HiveDriver\",\"url\":\"jdbc:hive2://zp-data-hadoop-qa-001:10000\"}");
        try {
            hiveCollector.new HiveMetaDataSource().createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
