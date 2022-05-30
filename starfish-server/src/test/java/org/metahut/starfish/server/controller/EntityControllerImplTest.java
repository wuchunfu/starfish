package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.EntityController;
import org.metahut.starfish.api.dto.HiveClusterResponseDTO;
import org.metahut.starfish.api.dto.HiveDBResponseDTO;
import org.metahut.starfish.api.dto.HiveTableQueryDTO;
import org.metahut.starfish.api.dto.HiveTableResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 */
@SpringBootTest
class EntityControllerImplTest {

    @Autowired
    private EntityController entityController;

    @Test
    void hiveClusters() {
        ResultEntity<Collection<HiveClusterResponseDTO>> collectionResultEntity = entityController.hiveClusters();
        assertNotNull(collectionResultEntity);
    }

    @Test
    void hiveDbs() {
        ResultEntity<Collection<HiveDBResponseDTO>> collectionResultEntity = entityController.hiveDbs();
        assertNotNull(collectionResultEntity);
    }

    @Test
    void hiveTables() throws ParseException {
        HiveTableQueryDTO queryDTO = new HiveTableQueryDTO();
        queryDTO.setPageNo(1);
        queryDTO.setPageSize(10);
        queryDTO.setHiveTableName("test");
        queryDTO.setHiveDbName("de");
        queryDTO.setCreateEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-05-31 00:00:00"));
        queryDTO.setUpdateEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-05-31 00:00:00"));
        queryDTO.setCreateBeginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-05-30 00:00:00"));
        queryDTO.setUpdateBeginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-05-30 00:00:00"));
        ResultEntity<PageResponseDTO<HiveTableResponseDTO>> result = entityController.hiveTables(queryDTO);
        assertNotNull(result);
    }
}
