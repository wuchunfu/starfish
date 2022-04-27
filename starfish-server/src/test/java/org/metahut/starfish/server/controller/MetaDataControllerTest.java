package org.metahut.starfish.server.controller;

import org.metahut.starfish.parser.domain.enums.RelType;
import org.metahut.starfish.parser.domain.instance.Attribute;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 *
 */
@Transactional
@SpringBootTest
public class MetaDataControllerTest {

    @Autowired
    private AbstractMetaDataService<Long,Object> metaDataService;

    private Map<String,Object> getSourceProperties() {
        Map<String,Object> result = new HashMap<>();
        result.put("version","v3.0");
        return result;
    }

    /**
     * public class HiveTable {
     *     private List<org.starfish.HiveColumn> columns
     *     private String tableName;
     * }
     * @return
     */
    private Class hiveTableClassInfo() {
        Class classInfo = new Class();
        classInfo.setPackagePath("org.starfish");
        classInfo.setName("HiveTable");
        classInfo.setSerialVersionUID(1L);
        classInfo.setAttributes(new ArrayList<>());
        Attribute tableNameAttr = new Attribute();
        tableNameAttr.setName("tableName");
        tableNameAttr.setArray(false);
        tableNameAttr.setClassName("String");
        tableNameAttr.setRelType(RelType.PRIMITIVE);
        classInfo.getAttributes().add(tableNameAttr);
        Attribute columnsAttr = new Attribute();
        columnsAttr.setRelType(RelType.ENTITY);
        columnsAttr.setClassName("org.starfish.HiveColumn");
        columnsAttr.setArray(true);
        columnsAttr.setName("columns");
        classInfo.getAttributes().add(columnsAttr);
        return classInfo;
    }

    /**
     * public class org.starfish.HiveColumn {
     *     private String columnName;
     *     private Enum columnType;
     * }
     * @return
     */
    private Class hiveColumnClassInfo() {
        Class classInfo = new Class();
        classInfo.setPackagePath("org.starfish");
        classInfo.setName("HiveColumn");
        classInfo.setSerialVersionUID(2L);
        classInfo.setAttributes(new ArrayList<>());
        Attribute tableNameAttr = new Attribute();
        tableNameAttr.setName("columnName");
        tableNameAttr.setArray(false);
        tableNameAttr.setClassName("String");
        tableNameAttr.setRelType(RelType.PRIMITIVE);
        classInfo.getAttributes().add(tableNameAttr);
        Attribute columnsAttr = new Attribute();
        columnsAttr.setRelType(RelType.PRIMITIVE);
        columnsAttr.setClassName("enum");
        columnsAttr.setArray(false);
        columnsAttr.setName("columnType");
        classInfo.getAttributes().add(columnsAttr);
        return classInfo;
    }

    private Map<String,Object> hiveTableInstance() {
        Map<String,Object> result = new HashMap<>();
        result.put("tableName","tb_user");
        return result;
    }

    private Map<String,Object> hiveColumnInstance() {
        Map<String,Object> result = new HashMap<>();
        result.put("columnName","username");
        result.put("columnType","varchar");
        return result;
    }

    @Test
    public void processTest() throws AbstractMetaParserException {
        Long sourceId = metaDataService.createSource("Hive", getSourceProperties());
        Long hiveTableType = metaDataService.createType(sourceId, hiveTableClassInfo(), null);
        Long hiveColumnType = metaDataService.createType(sourceId,hiveColumnClassInfo(),null);
        Long hiveTableId = metaDataService.createEntity(hiveTableType, "tb_user", hiveTableInstance());
        Long hiveColumnId = metaDataService.createEntity(hiveColumnType, "column1", hiveColumnInstance());
        metaDataService.link(hiveTableId,hiveColumnId,"columns");
        metaDataService.updateEntity(hiveColumnId,"username",hiveColumnInstance());
        metaDataService.deleteSource(sourceId);
        //metaDataService.deleteEntity(Arrays.asList(hiveTableId));
        //metaDataService.deleteType(Arrays.asList(hiveTableType));
    }

}
