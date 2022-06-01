package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.EntityController;
import org.metahut.starfish.api.dto.HiveClusterQueryDTO;
import org.metahut.starfish.api.dto.HiveClusterResponseDTO;
import org.metahut.starfish.api.dto.HiveDBQueryDTO;
import org.metahut.starfish.api.dto.HiveDBResponseDTO;
import org.metahut.starfish.api.dto.HiveTableQueryDTO;
import org.metahut.starfish.api.dto.HiveTableResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.PulsarClusterQueryDTO;
import org.metahut.starfish.api.dto.PulsarClusterResponseDTO;
import org.metahut.starfish.api.dto.PulsarTopicQueryDTO;
import org.metahut.starfish.api.dto.PulsarTopicResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        HiveClusterQueryDTO hiveClusterQueryDTO = new HiveClusterQueryDTO();
        hiveClusterQueryDTO.setPageNo(1);
        hiveClusterQueryDTO.setPageSize(10);
        ResultEntity<PageResponseDTO<HiveClusterResponseDTO>> collectionResultEntity = entityController.hiveClusters(hiveClusterQueryDTO);
        assertNotNull(collectionResultEntity);
    }

    @Test
    void hiveDbs() {
        HiveDBQueryDTO hiveDBQueryDTO = new HiveDBQueryDTO();
        hiveDBQueryDTO.setPageNo(1);
        hiveDBQueryDTO.setPageSize(10);
        ResultEntity<PageResponseDTO<HiveDBResponseDTO>> collectionResultEntity = entityController.hiveDbs(hiveDBQueryDTO);
        assertNotNull(collectionResultEntity);
    }

    @Test
    void hiveTables() throws ParseException {
        HiveTableQueryDTO queryDTO = new HiveTableQueryDTO();
        queryDTO.setPageNo(1);
        queryDTO.setPageSize(10);
        queryDTO.setHiveTableName("test");
        queryDTO.setHiveDbName("de");
        queryDTO.setHiveClusterId(1411L);
        queryDTO.setCreateEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-05-31 00:00:00"));
        queryDTO.setUpdateEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-05-31 00:00:00"));
        queryDTO.setCreateBeginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-05-30 00:00:00"));
        queryDTO.setUpdateBeginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-05-30 00:00:00"));
        ResultEntity<PageResponseDTO<HiveTableResponseDTO>> result = entityController.hiveTables(queryDTO);
        assertNotNull(result);
    }

    @Test
    void pulsarClusters() {
        PulsarClusterQueryDTO pulsarClusterQueryDTO = new PulsarClusterQueryDTO();
        pulsarClusterQueryDTO.setPageNo(1);
        pulsarClusterQueryDTO.setPageSize(10);
        ResultEntity<PageResponseDTO<PulsarClusterResponseDTO>> pulsarClusters = entityController
            .pulsarClusters(pulsarClusterQueryDTO);
        Assertions.assertEquals(true, pulsarClusters.isSuccess());
        assertNotNull(pulsarClusters);
    }

    @Test
    void pulsarTopics() {
        PulsarTopicQueryDTO pulsarTopicQueryDTO = new PulsarTopicQueryDTO();
        // pulsarTopicQueryDTO.setClusterName("pulsar-cluster-qa");
        // pulsarTopicQueryDTO.setTopicName();
        pulsarTopicQueryDTO.setPageNo(1);
        pulsarTopicQueryDTO.setPageSize(10);
        // pulsarTopicQueryDTO.setPublisherName();
        // pulsarTopicQueryDTO.setPublisherTeam();
        // pulsarTopicQueryDTO.getCreateBeginTime();
        // pulsarTopicQueryDTO.setCreateEndTime();
        // pulsarTopicQueryDTO.setUpdateBeginTime();
        // pulsarTopicQueryDTO.setUpdateEndTime();
        ResultEntity<PageResponseDTO<PulsarTopicResponseDTO>> pulsarTopics = entityController
            .pulsarTopics(pulsarTopicQueryDTO);
        Assertions.assertEquals(true, pulsarTopics.isSuccess());
        assertNotNull(pulsarTopics);
    }
}
