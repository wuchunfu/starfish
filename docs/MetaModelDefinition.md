# MetaDef

## hive

* hive_db

```
userName
dataBaseUrl
isReadOnly
databaseProductName
databaseProductVersion
driverName
driverVersion
db_name (schema)
comment (remark)
location *
owner_name *
owner_type *
paramters *
tables

```

* hive_table

```
table name
table type
table catagory
table schema(命名空间？)
remark
partitioned by *
row format serde *
stored as inputformat *
outputformat *
location *
tblproperties *
columns 
partitions *

```

* hive_column

```
TABLE_CAT String // 表类别（可为 null）
TABLE_SCHEM String // 表模式（可为 null）
TABLE_NAME String // 表名称
COLUMN_NAME String // 列名称
DATA_TYPE int // 来自 java.sql.Types 的 SQL 类型
TYPE_NAME String // 数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的
COLUMN_SIZE int // 列的大小。
BUFFER_LENGTH 未被使用。
DECIMAL_DIGITS int // 小数部分的位数。对于 DECIMAL_DIGITS 不适用的数据类型，则返回 Null。
NUM_PREC_RADIX int // 基数（通常为 10 或 2）
NULLABLE int // 是否允许使用 NULL。
columnNoNulls - 可能不允许使用 NULL 值
columnNullable - 明确允许使用 NULL 值
columnNullableUnknown - 不知道是否可使用 null
REMARKS String // 描述列的注释（可为 null）
COLUMN_DEF String // 该列的默认值，当值在单引号内时应被解释为一个字符串（可为 null）
SQL_DATA_TYPE int // 未使用
SQL_DATETIME_SUB int // 未使用
CHAR_OCTET_LENGTH int // 对于 char 类型，该长度是列中的最大字节数
ORDINAL_POSITION int // 表中的列的索引（从 1 开始）
IS_NULLABLE String // ISO 规则用于确定列是否包括 null。
SCOPE_CATLOG String // 表的类别，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null）
SCOPE_SCHEMA String // 表的模式，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null）
SCOPE_TABLE String // 表名称，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null）
SOURCE_DATA_TYPE short // 不同类型或用户生成 Ref 类型、来自 java.sql.Types 的 SQL 类型的源类型（如果 DATA_TYPE 不是 DISTINCT 或用户生成的 REF，则为 null）
IS_AUTOINCREMENT String // 指示此列是否自动增加
```

* hive index

```
TYPE //索引类型;
NON_UNIQUE// 索引值是否可以不唯一,TYPE为 tableIndexStatistic时索引值为 false;
INDEX_QUALIFIER//索引类别（可能为空）,TYPE为 tableIndexStatistic 时索引类别为 null; 
INDEX_NAME//索引的名称 ;TYPE为 tableIndexStatistic 时索引名称为 null;
ORDINAL_POSITION//在索引列顺序号;TYPE为 tableIndexStatistic 时该序列号为零;
COLUMN_NAME//列名;TYPE为 tableIndexStatistic时列名称为 null;
ASC_OR_DESC//列排序顺序:升序还是降序[A:升序; B:降序];如果排序序列不受支持,可能为 null;TYPE为 tableIndexStatistic时排序序列为 null;
CARDINALITY//基数;TYPE为 tableIndexStatistic 时,它是表中的行数;否则,它是索引中唯一值的数量。   
PAGES//TYPE为 tableIndexStatisic时,它是用于表的页数,否则它是用于当前索引的页数。
FILTER_CONDITION //过滤器条件,如果有的话(可能为 null)。
```sql
```

* hive primaryKey

```
COLUMN_NAME//列名  
KEY_SEQ//序列号(主键内值1表示第一列的主键，值2代表主键内的第二列)  
PK_NAME//主键名称   
```

decribe database db01 show create table tbl01 decribe db01.tbl01 col01

## pulsar

* pulsar_cluster

```

name 
description 
zookeeper-connect 
configuration-store s
ervice-url 
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
