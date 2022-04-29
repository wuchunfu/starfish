# MetaDef

## hive
* hive_db

```
String databaseName 数据库名称
String description 数据库描述
String locationUrl 数据库HDFS路径
String ownerName  数据库所有者用户名
String catalogName 目录名称
PrincipalType ownerType 数据库所有者角色(
    USER(1),
    ROLE(2),
    GROUP(3))
List<String> tables  数据库下的所有表名称
Map parameters:{ 存储数据库的相关参数 
String param_key 例如：createBy
String param_value 例如：创建者名称
PrincipalPrivilegeSet privileges 数据库权限 
（
PrincipalPrivilegeSet：
     Map<String, List<PrivilegeGrantInfo>> userPrivileges;//用户权限
     Map<String, List<PrivilegeGrantInfo>> groupPrivileges;//组权限
    Map<String, List<PrivilegeGrantInfo>> rolePrivileges;//角色权限）
}
```

* hive_table

```
String tableName 数据库表名称
String tableType 数据库表类型（四种类型：MANAGED_TABLE,EXTERNAL_TABLE,INDEX_TABLE,VIRTUAL_VIEW）
String dbName 数据库名称
String owner 所有者
timestamp creatime 创建时间
timestamp lastAccessTime 上次访问时间
String retention 保留字段
StorageDescriptor sd( 序列化配置信息（包括Hive文件存储信息元数据，）
StorageDescriptor :   Hive文件存储信息元数据
     FieldSchema[] cols: 表字段相关元数据信息
       (FieldSchema:
            String name, 字段名
            String type , 字段类型
            String comment 字段注释
            ) 
     String location HDFS存储路径
     String inputFormat 文件输入格式
     String outputFormat 文件输出格式
     Boolean compressed 是否压缩
     int numBuckets 分桶数量
     Object parameters:{ 存储Hive存储的属性信息，在创建表时候使用
       String param_key
       String param_value 
    })
     SerdeInfo serdeInfo: 序列化使用的类信息
      (
      String name, 序列化类别名
      String serializationLib, 序列化类
      Object parameters:{ 存储序列化的一些属性、格式信息,比如：行、列分隔符
       String param_key 例如:field.delim(字段分隔符)
       String param_value 例如：, ;
    })
    List<String> bucketCols;
    List<Order> sortCols;
      map<String,String> parameters:{ 存储序列化的一些属性、格式信息,比如：行、列分隔符
       String param_key 例如:field.delim(字段分隔符)
       String param_value 例如：, ;
    })
    SkewedInfo skewedInfo;
    （
    List<String> skewedColNames;
    List<List<String>> skewedColValues;
    Map<List<String>, String> skewedColValueLocationMaps;）
    boolean storedAsSubDirectories; 
    ）
     PartitionKeys partitionKeys:{
         String pkeyComment  分区字段说明
         String pkeyName 分区字段名
         String pkeyType 分区字段类型
         int integerIndex 分区字段顺序
}     
    map<String,String> parameters:{//分区
       String param_key 例如:分区属性名
       String param_value 例如：分区属性值, ;
    })
   String viewOriginalText, 视图的原始HQL语句
   String viewExpandedText, 视图的详细HQL语句
   Boolean rewriteEnabled, 是否可重写
   String ownerType 所有者类型
   boolean temporary 是否临时表
   CreationMetadata creationMetadata
   {
    private String catName; 目录名称
    private String dbName; 数据库名称
    private String tblName; 表名
    private Set<String> tablesUsed; 
    private String validTxnList;
    private long materializationTime;
   PrincipalPrivilegeSet privileges 数据表权限 
（
PrincipalPrivilegeSet：
     Map<String, List<PrivilegeGrantInfo>> userPrivileges;//用户权限
     Map<String, List<PrivilegeGrantInfo>> groupPrivileges;//组权限
    Map<String, List<PrivilegeGrantInfo>> rolePrivileges;//角色权限）
}
   }
     
)
```

* hive_partition

```
    List<String> values; 分区字段
    String dbName; 数据库
    String tableName; 数据表
    int createTime; 分区创建时间
    int lastAccessTime; 分区最近修改时间
    StorageDescriptor sd; 序列化配置信息
    （
     List<FieldSchema> cols;
      (FieldSchema:  字段描述信息
            String name, 字段名
            String type , 字段类型
            String comment 字段注释
            ）
     String location HDFS存储路径
     String inputFormat 文件输入格式
     String outputFormat 文件输出格式
     Boolean compressed 是否压缩
     int numBuckets 分桶数量
     map<String,String> parameters:{ 存储Hive存储的属性信息，在创建表时候使用
       String param_key
       String param_value 
    })
     SerdeInfo serdeInfo: 序列化使用的类信息
      (
      String name, 序列化类别名
      String serializationLib, 序列化类
      map<String,String> parameters:{ 存储序列化的一些属性、格式信息,比如：行、列分隔符
       String param_key 例如:field.delim(字段分隔符)
       String param_value 例如：, ;
    })
    List<String> bucketCols;
    List<Order> sortCols;
      map<String,String> parameters:{ 存储序列化的一些属性、格式信息,比如：行、列分隔符
       String param_key 例如:field.delim(字段分隔符)
       String param_value 例如：, ;
    })
    SkewedInfo skewedInfo;
    （
    List<String> skewedColNames;
    List<List<String>> skewedColValues;
    Map<List<String>, String> skewedColValueLocationMaps;）
    boolean storedAsSubDirectories; 
    ）
    map<String,String> parameters:{ 存储分区属性
       String param_key 例如：numFiles、numRows、rawDataSize、totalSize、transient_lastDdlTime
       String param_value 例如：15、502195
    })
    PrincipalPrivilegeSet privileges 数据表分区权限 
（
PrincipalPrivilegeSet：
     Map<String, List<PrivilegeGrantInfo>> userPrivileges;//用户权限
     Map<String, List<PrivilegeGrantInfo>> groupPrivileges;//组权限
    Map<String, List<PrivilegeGrantInfo>> rolePrivileges;//角色权限）
}



decribe database db01 show create table tbl01 decribe db01.tbl01 col01

## pulsar

* pulsar_cluster

```

name 
description 
zookeeper-connect 
configuration-store s
service-url 
brokerService-url 
service-url-tls
brokerService-url-tls 
token

```

* pulsar_topic

```
name
description
cluster
persistentType 
namespace
```
