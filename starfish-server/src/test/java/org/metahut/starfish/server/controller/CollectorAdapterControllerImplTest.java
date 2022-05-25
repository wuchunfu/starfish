package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.dto.CollectorAdapterCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.scheduler.dolphinscheduler.JSONUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

@Disabled
class CollectorAdapterControllerImplTest  extends WebApplicationTest {

    private static final String REST_FUNCTION_URL_PREFIX = "/collectorAdapter/";

    @Test
    void testConnection() {
    }

    @Test
    void create() {
        String url = this.base + REST_FUNCTION_URL_PREFIX + "create";
        // create alerter instance
        CollectorAdapterCreateOrUpdateRequestDTO collectorAdapterCreateOrUpdateRequestDTO = new CollectorAdapterCreateOrUpdateRequestDTO();
        collectorAdapterCreateOrUpdateRequestDTO.setDescription("this is a hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setName("hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setParameter("thrift://172.21.100.231:9083");
        collectorAdapterCreateOrUpdateRequestDTO.setType("Hive");
        HttpEntity httpEntity = new HttpEntity(collectorAdapterCreateOrUpdateRequestDTO);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        ResultEntity<CollectorAdapterResponseDTO> create = JSONUtils.parseObject(responseEntity.getBody(), new TypeReference<ResultEntity<CollectorAdapterResponseDTO>>() {
        });
        Assertions.assertTrue(create.isSuccess());
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