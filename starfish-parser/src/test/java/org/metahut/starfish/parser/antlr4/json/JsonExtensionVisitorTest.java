package org.metahut.starfish.parser.antlr4.json;

import org.metahut.starfish.parser.domain.enums.RelType;
import org.metahut.starfish.parser.domain.instance.Attribute;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.domain.instance.InstanceAnalysisStruct;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
class JsonExtensionVisitorTest {

    @Test
    public void testJsonPath() {
        String regex = "@|(\\.\\.(/\\.\\.)*)|(\\$(\\.(\\S)+([\\d+])?)*)";
        Assertions.assertTrue("@".matches(regex));
        Assertions.assertTrue("..".matches(regex));
        Assertions.assertTrue("../../..".matches(regex));
        Assertions.assertTrue("$.name[32]".matches(regex));
        Assertions.assertTrue("$".matches(regex));
        Assertions.assertTrue("$.parent[0].child[12]".matches(regex));
    }

    @Test
    public void analysisTest() {
        Map<Long, Class> classMap = getClassConfig();
        String json = json();
        InstanceAnalysisStruct struct = JsonExtensionVisitor.analysis(json, "HiveTable", classMap);
        Assertions.assertNotNull(struct);
    }

    private Map<Long,Class> getClassConfig() {
        Map<Long,Class> result = new HashMap<>();
        Class hiveClass = toClass("HiveTable", Arrays.asList(
                attribute(RelType.ENTITY,"HiveColumn","columns",true),
                attribute(RelType.PRIMITIVE,"String","tableName"),
                attribute(RelType.ENTITY,"HiveConstant","constants",true)

        ),"tableName");
        result.put(1L,hiveClass);
        Class columnClass = toClass("HiveColumn",Arrays.asList(
                attribute(RelType.ENTITY,"HiveTable","table"),
                attribute(RelType.PRIMITIVE,"String","columnName"),
                attribute(RelType.PRIMITIVE,"String","columnType"),
                attribute(RelType.ENTITY,"HiveConstant","constants",true)

        ),"columnName");
        result.put(2L,columnClass);
        Class constantClass = toClass("HiveConstant",Arrays.asList(
                attribute(RelType.PRIMITIVE,"String","constantName"),
                attribute(RelType.PRIMITIVE,"String","sql")

        ),"constantName");
        result.put(3L,constantClass);
        return result;
    }

    private String json() {
        List<HiveTable> tables = new ArrayList<>();
        HiveTable table = new HiveTable("user_table");
        HiveColumn column1 = new HiveColumn("user_id","int");
        column1.table = table;
        HiveColumn column2 = new HiveColumn("user_name","varchar");
        column2.table = table;
        HiveColumn column3 = new HiveColumn("password","varchar");
        column3.table = table;
        table.columns = Arrays.asList(column1,column2,column3);
        return JSON.toJSONString(Arrays.asList(table));
    }

    private Class toClass(String name,List<Attribute> attributes,String nameAttributeRel) {
        Class classInfo = new Class();
        classInfo.setName(name);
        classInfo.setAttributes(attributes);
        classInfo.setNameAttributeRel(nameAttributeRel);
        return classInfo;
    }

    private Attribute attribute(RelType relType,String className,String name) {
        return attribute(relType,className,name,false);
    }

    private Attribute attribute(RelType relType,String className,String name,boolean array) {
        Attribute attribute = new Attribute();
        attribute.setRelType(relType);
        attribute.setClassName(className);
        attribute.setName(name);
        attribute.setArray(array);
        return attribute;
    }

    class HiveTable {
        private List<HiveColumn> columns;
        private String tableName;
        private HiveConstant constants;

        public List<HiveColumn> getColumns() {
            return columns;
        }

        public void setColumns(List<HiveColumn> columns) {
            this.columns = columns;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public HiveConstant getConstants() {
            return constants;
        }

        public void setConstants(HiveConstant constants) {
            this.constants = constants;
        }

        public HiveTable(String tableName) {
            this.tableName = tableName;
        }
    }

    class HiveColumn {
        private HiveTable table;
        private String columnName;
        private String columnType;
        private List<HiveConstant> constants;

        public HiveTable getTable() {
            return table;
        }

        public void setTable(HiveTable table) {
            this.table = table;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnType() {
            return columnType;
        }

        public void setColumnType(String columnType) {
            this.columnType = columnType;
        }

        public List<HiveConstant> getConstants() {
            return constants;
        }

        public void setConstants(List<HiveConstant> constants) {
            this.constants = constants;
        }

        public HiveColumn(String columnName, String columnType) {
            this.columnName = columnName;
            this.columnType = columnType;
        }
    }

    class HiveConstant {
        private String constantName;
        private String sql;

        public String getConstantName() {
            return constantName;
        }

        public void setConstantName(String constantName) {
            this.constantName = constantName;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public HiveConstant(String constantName, String sql) {
            this.constantName = constantName;
            this.sql = sql;
        }
    }
}
