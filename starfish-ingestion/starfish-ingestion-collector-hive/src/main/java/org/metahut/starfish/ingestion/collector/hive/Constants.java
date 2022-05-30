package org.metahut.starfish.ingestion.collector.hive;

public class Constants {

    private Constants() {
    }

    public static final String COLLECTOR_TYPE = "Hive";
    public static final String TYPE_NAME_CLUSTER = "org.starfish.HiveCluster";
    public static final String TYPE_NAME_DB = "org.starfish.HiveDB";
    public static final String TYPE_NAME_TABLE = "org.starfish.HiveTable";
    public static final String TYPE_NAME_COLUMN = "org.starfish.HiveColumn";

    public static final String RELATION_PROPERTY_CLUSTER_DB = "dbs";
    public static final String RELATION_PROPERTY_DB_CLUSTER = "cluster";

    public static final String RELATION_PROPERTY_DB_TABLE = "tables";

    public static final String RELATION_PROPERTY_TABLE_DB = "db";

    public static final String RELATION_PROPERTY_TABLE_COLUMN = "columns";

    public static final String RELATION_PROPERTY_TABLE_PARTITION_KEY = "partitionKeys";

    public static final String RELATION_PROPERTY_COLUMN_TABLE = "table";

    public static final String PROPERTY_TABLE_COMMENT = "comment";


}
