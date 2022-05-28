package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.CollectorAdapterController;
import org.metahut.starfish.api.dto.CollectorAdapterCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.server.utils.JSONUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@Disabled
class CollectorAdapterControllerImplTest extends WebApplicationTest {

    private static final String REST_FUNCTION_URL_PREFIX = "/collectorAdapter/";

    @Test
    void create() {
        CollectorAdapterCreateOrUpdateRequestDTO collectorAdapterCreateOrUpdateRequestDTO = new CollectorAdapterCreateOrUpdateRequestDTO();
        collectorAdapterCreateOrUpdateRequestDTO.setDescription("this is a hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setName("hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setParameter("{\"hiveMetastoreUris\":\"thrift://172.21.100.231:9083\"}");
        collectorAdapterCreateOrUpdateRequestDTO.setType("Hive");

        createAdapters(collectorAdapterCreateOrUpdateRequestDTO);
    }

    public CollectorAdapterResponseDTO createAdapters(CollectorAdapterCreateOrUpdateRequestDTO collectorAdapterCreateOrUpdateRequestDTO) {
        String url = this.base + REST_FUNCTION_URL_PREFIX;
        HttpEntity httpEntity = new HttpEntity(collectorAdapterCreateOrUpdateRequestDTO);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
        ResultEntity<CollectorAdapterResponseDTO> create = JSONUtils.parseObject(responseEntity.getBody(), new TypeReference<ResultEntity<CollectorAdapterResponseDTO>>() {
        });
        Assertions.assertTrue(create.isSuccess());
        CollectorAdapterResponseDTO createData = create.getData();
        Assertions.assertNotNull(createData.getId());
        return createData;
    }

    @Test
    void update() {
        CollectorAdapterCreateOrUpdateRequestDTO collectorAdapterCreateOrUpdateRequestDTO = new CollectorAdapterCreateOrUpdateRequestDTO();
        collectorAdapterCreateOrUpdateRequestDTO.setDescription("this is a hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setName("hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setParameter("{\"hiveMetastoreUris\":\"thrift://172.21.100.231:9083\"}");
        collectorAdapterCreateOrUpdateRequestDTO.setType("Hive");
        CollectorAdapterResponseDTO collectorAdapterResponseDTO = createAdapters(collectorAdapterCreateOrUpdateRequestDTO);

        //collectorAdapterResponseDTO.setDescription("this is a new hive adapter");
        //collectorAdapterResponseDTO.setParameter("{\"hiveMetastoreUris\":\"thrift://172.21.100.231:9083\"}");
        //collectorAdapterResponseDTO.setName("a updated hive adapter");
        //collectorAdapterResponseDTO.setType("Hive");

        String url = this.base + REST_FUNCTION_URL_PREFIX + "/" + collectorAdapterResponseDTO.getId();
        HttpEntity httpEntity = new HttpEntity(collectorAdapterCreateOrUpdateRequestDTO);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
        ResultEntity<CollectorAdapterResponseDTO> update = JSONUtils.parseObject(responseEntity.getBody(), new TypeReference<ResultEntity<CollectorAdapterResponseDTO>>() {
        });
        Assertions.assertTrue(update.isSuccess());
    }

    @Test
    void deleteById() {
        CollectorAdapterCreateOrUpdateRequestDTO collectorAdapterCreateOrUpdateRequestDTO = new CollectorAdapterCreateOrUpdateRequestDTO();
        collectorAdapterCreateOrUpdateRequestDTO.setDescription("this is a hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setName("hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setParameter("{\"hiveMetastoreUris\":\"thrift://172.21.100.231:9083\"}");
        collectorAdapterCreateOrUpdateRequestDTO.setType("Hive");
        CollectorAdapterResponseDTO collectorAdapterResponseDTO = createAdapters(collectorAdapterCreateOrUpdateRequestDTO);
        String delUrl = REST_FUNCTION_URL_PREFIX + collectorAdapterResponseDTO.getId();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity delHttpEntity = new HttpEntity(headers);
        ResponseEntity<ResultEntity> delResponseEntity = restTemplate.exchange(delUrl, HttpMethod.DELETE, delHttpEntity, ResultEntity.class);
        Assertions.assertTrue(delResponseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(delResponseEntity.getBody().isSuccess());
    }

    @Test
    void queryById() {
        CollectorAdapterCreateOrUpdateRequestDTO collectorAdapterCreateOrUpdateRequestDTO = new CollectorAdapterCreateOrUpdateRequestDTO();
        collectorAdapterCreateOrUpdateRequestDTO.setDescription("this is a hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setName("hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setParameter("{\"hiveMetastoreUris\":\"thrift://172.21.100.231:9083\"}");
        collectorAdapterCreateOrUpdateRequestDTO.setType("Hive");
        CollectorAdapterResponseDTO collectorAdapterResponseDTO = createAdapters(collectorAdapterCreateOrUpdateRequestDTO);

        String url = this.base + REST_FUNCTION_URL_PREFIX + collectorAdapterResponseDTO.getId();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(builder.build().encode().toUri(), String.class);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        ResultEntity<CollectorAdapterResponseDTO> result = JSONUtils.parseObject(responseEntity.getBody(), new TypeReference<ResultEntity<CollectorAdapterResponseDTO>>() {
        });
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals(result.getData().getName(), collectorAdapterCreateOrUpdateRequestDTO.getName());
    }

    @Test
    void queryListPage() {
        CollectorAdapterCreateOrUpdateRequestDTO collectorAdapterCreateOrUpdateRequestDTO = new CollectorAdapterCreateOrUpdateRequestDTO();
        collectorAdapterCreateOrUpdateRequestDTO.setDescription("this is a hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setName("hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setParameter("{\"hiveMetastoreUris\":\"thrift://172.21.100.231:9083\"}");
        collectorAdapterCreateOrUpdateRequestDTO.setType("Hive");
        CollectorAdapterResponseDTO collectorAdapterResponseDTO = createAdapters(collectorAdapterCreateOrUpdateRequestDTO);

        CollectorAdapterCreateOrUpdateRequestDTO collectorAdapterCreateOrUpdateRequestDTO1 = new CollectorAdapterCreateOrUpdateRequestDTO();
        collectorAdapterCreateOrUpdateRequestDTO.setDescription("this is a pulsar adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setName("Pulsar adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setParameter("{\"serverUrl\":\"http://pulsar-idc-qa.zpidc.com:8080\"}");
        collectorAdapterCreateOrUpdateRequestDTO.setType("Pulsar");
        CollectorAdapterResponseDTO collectorAdapterResponseDTO1 = createAdapters(collectorAdapterCreateOrUpdateRequestDTO);

        String url = this.base + REST_FUNCTION_URL_PREFIX + "queryListPage";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("pageNo", 1)
                .queryParam("pageSize", 10);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(builder.build().encode().toUri(), String.class);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        ResultEntity<PageResponseDTO<CollectorAdapterCreateOrUpdateRequestDTO>> pageResult = JSONUtils.parseObject(responseEntity.getBody()
                , new TypeReference<ResultEntity<PageResponseDTO<CollectorAdapterCreateOrUpdateRequestDTO>>>() {
                });
        Assertions.assertTrue(pageResult.isSuccess());
        PageResponseDTO<CollectorAdapterCreateOrUpdateRequestDTO> data = pageResult.getData();
        Assertions.assertEquals(2, data.getTotal());
    }

    @Test
    void queryList() {
        //CollectorAdapterCreateOrUpdateRequestDTO collectorAdapterCreateOrUpdateRequestDTO = new CollectorAdapterCreateOrUpdateRequestDTO();
        //collectorAdapterCreateOrUpdateRequestDTO.setDescription("this is a hive adapter");
        //collectorAdapterCreateOrUpdateRequestDTO.setName("hive adapter");
        //collectorAdapterCreateOrUpdateRequestDTO.setParameter("{\"hiveMetastoreUris\":\"thrift://172.21.100.231:9083\"}");
        //collectorAdapterCreateOrUpdateRequestDTO.setType("Hive");
        //CollectorAdapterResponseDTO collectorAdapterResponseDTO = createAdapters(collectorAdapterCreateOrUpdateRequestDTO);
        //
        //CollectorAdapterCreateOrUpdateRequestDTO collectorAdapterCreateOrUpdateRequestDTO1 = new CollectorAdapterCreateOrUpdateRequestDTO();
        //collectorAdapterCreateOrUpdateRequestDTO1.setDescription("this is a pulsar adapter");
        //collectorAdapterCreateOrUpdateRequestDTO1.setName("Pulsar adapter");
        //collectorAdapterCreateOrUpdateRequestDTO1.setParameter("{\"serverUrl\":\"http://pulsar-idc-qa.zpidc.com:8080\"}");
        //collectorAdapterCreateOrUpdateRequestDTO1.setType("Pulsar");
        //CollectorAdapterResponseDTO collectorAdapterResponseDTO1 = createAdapters(collectorAdapterCreateOrUpdateRequestDTO1);

        String url = this.base + REST_FUNCTION_URL_PREFIX + "queryList";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("name", "hive adapter");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(builder.build().encode().toUri(), String.class);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        ResultEntity<Collection<CollectorAdapterResponseDTO>> listResult = JSONUtils.parseObject(responseEntity.getBody()
                , new TypeReference<ResultEntity<Collection<CollectorAdapterResponseDTO>>>() {
                });
        Assertions.assertTrue(listResult.isSuccess());
        Collection<CollectorAdapterResponseDTO> data = listResult.getData();
        Assertions.assertEquals(1, data.size());
        CollectorAdapterResponseDTO collectorAdapterResponseDTO2 = data.stream().findFirst().get();
        Assertions.assertEquals("hive adapter", collectorAdapterResponseDTO2.getName());
    }

    @Autowired
    private CollectorAdapterController controller;

    @Test
    public void test() {
        CollectorAdapterRequestDTO dto = new CollectorAdapterRequestDTO();
        dto.setName("hive adapter");
        dto.setDescription("this is a hive adapter");
        controller.queryList(dto);
    }
}
