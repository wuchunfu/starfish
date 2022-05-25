package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.dto.CollectorAdapterCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled
class CollectorAdapterControllerImplTest {

    @Autowired
    CollectorAdapterControllerImpl collectorAdapterController;

    @Test
    void testConnection() {
    }

    @Test
    void create() {
        CollectorAdapterCreateOrUpdateRequestDTO collectorAdapterCreateOrUpdateRequestDTO = new CollectorAdapterCreateOrUpdateRequestDTO();
        collectorAdapterCreateOrUpdateRequestDTO.setDescription("this is a hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setName("hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setParameter("{\"hiveMetastoreUris\":\"thrift://172.21.100.231:9083\"}");
        collectorAdapterCreateOrUpdateRequestDTO.setType("Hive");

        ResultEntity<CollectorAdapterResponseDTO> create = collectorAdapterController.create(collectorAdapterCreateOrUpdateRequestDTO);
        CollectorAdapterResponseDTO createData = create.getData();
        Assertions.assertNotNull(createData.getId());
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void queryById() {
    }

    @Test
    void queryListPage() {
    }

    @Test
    void queryList() {
    }
}