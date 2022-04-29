package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.MetaDataController;
import org.metahut.starfish.api.dto.BatchMetaDataDTO;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@SpringBootTest
@Disabled
public class MetaDataControllerImplTest {

    @Resource
    private MetaDataController metaDataController;

    @Test
    public void batchRequest() throws Exception {
        BatchMetaDataDTO dto = new BatchMetaDataDTO();
        BatchMetaDataDTO.SourceBodyDTO sourceBodyDTO = new BatchMetaDataDTO.SourceBodyDTO();
        sourceBodyDTO.setName("Hive");
        sourceBodyDTO.setAttributes(null);
        dto.setSource(sourceBodyDTO);
        Map<String, List<String>> instances = new HashMap<>();
        instances.put("org.starfish.HiveColumn",
            Arrays.asList("{\"columnName\":\"username\",\"columnType\":\"varchar\"}"));
        instances.put("org.starfish.HiveTable", Arrays.asList("{\"tableName\":\"tb_user\"}"));
        dto.setInstances(instances);
        List<BatchMetaDataDTO.ClassDTO> types = new ArrayList<>();
        BatchMetaDataDTO.ClassDTO hiveColumn = new BatchMetaDataDTO.ClassDTO();
        hiveColumn.setPackagePath("org.starfish");
        hiveColumn.setName("HiveColumn");
        hiveColumn.setSerialVersionUID(2L);
        hiveColumn.setAttributes(new ArrayList<>());
        BatchMetaDataDTO.AttributeDTO tableNameAttr = new BatchMetaDataDTO.AttributeDTO();
        tableNameAttr.setName("columnName");
        tableNameAttr.setArray(false);
        tableNameAttr.setClassName("String");
        tableNameAttr.setRelType("PRIMITIVE");
        hiveColumn.getAttributes().add(tableNameAttr);
        BatchMetaDataDTO.AttributeDTO columnsAttr = new BatchMetaDataDTO.AttributeDTO();
        columnsAttr.setRelType("PRIMITIVE");
        columnsAttr.setClassName("enum");
        columnsAttr.setArray(false);
        columnsAttr.setName("columnType");
        hiveColumn.getAttributes().add(columnsAttr);
        types.add(hiveColumn);
        BatchMetaDataDTO.ClassDTO hiveTable = new BatchMetaDataDTO.ClassDTO();
        hiveTable.setPackagePath("org.starfish");
        hiveTable.setName("HiveTable");
        hiveTable.setSerialVersionUID(1L);
        hiveTable.setAttributes(new ArrayList<>());
        BatchMetaDataDTO.AttributeDTO tableNameAttr1 = new BatchMetaDataDTO.AttributeDTO();
        tableNameAttr1.setName("tableName");
        tableNameAttr1.setArray(false);
        tableNameAttr1.setClassName("String");
        tableNameAttr1.setRelType("PRIMITIVE");
        hiveTable.getAttributes().add(tableNameAttr1);
        BatchMetaDataDTO.AttributeDTO columnsAttr1 = new BatchMetaDataDTO.AttributeDTO();
        columnsAttr1.setRelType("ENTITY");
        columnsAttr1.setClassName("org.starfish.HiveColumn");
        columnsAttr1.setArray(true);
        columnsAttr1.setName("columns");
        hiveTable.getAttributes().add(columnsAttr1);
        types.add(hiveTable);
        dto.setTypes(types);
        metaDataController.batchRequest(dto);
    }
}
