package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.dto.CollectorAdapterCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.dto.TypeRequestBatchCreateOrUpdateDTO;
import org.metahut.starfish.api.enums.ReleaseStateEnum;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.hive.HiveCollectorAdapterParameter;
import org.metahut.starfish.scheduler.api.IScheduler;
import org.metahut.starfish.scheduler.api.parameters.TaskParameter;
import org.metahut.starfish.server.collector.CollectorPluginParameterHelper;
import org.metahut.starfish.server.service.TypeService;
import org.metahut.starfish.server.utils.Assert;
import org.metahut.starfish.server.utils.JSONUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.metahut.starfish.api.enums.Status.INIT_TYPE_MODEL_TO_LOAD_FILE_FAIL;
import static org.mockito.BDDMockito.given;

/**
 * @author chenhao 2022/5/31
 */
class CollectorTaskControllerImplMvcTest extends WebApplicationTest {

    private static final String REST_FUNCTION_URL_PREFIX = "/collectorTask/";
    private static final String REST_FUNCTION_URL_ADAPTER_PREFIX = "/collectorAdapter/";

    @MockBean
    private IScheduler scheduler;

    @Autowired
    private TypeService typeService;

    @MockBean
    private CollectorPluginParameterHelper collectorPluginParameterHelper;

    private String flowId = "1";

    private Long adapterId = 1L;

    private static boolean initStatus = false;

    @BeforeEach
    public void initTypeModel() {
        List<File> files = null;
        if (initStatus == false) {
            try {
                String path = ResourceUtils.getURL("../tools/models").getPath();
                files = Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            } catch (IOException e) {
                Assert.throwException(e, INIT_TYPE_MODEL_TO_LOAD_FILE_FAIL);
            }

            files.forEach(file -> {
                TypeRequestBatchCreateOrUpdateDTO typeRequest = JSONUtils
                    .parseObject(file, TypeRequestBatchCreateOrUpdateDTO.class);
                typeService.initTypes(typeRequest);
            });
            initStatus = true;
        }
    }

    @Test
    void create() throws Exception {

        CollectorTaskCreateOrUpdateRequestDTO collectorTaskCreateOrUpdateRequestDTO = new CollectorTaskCreateOrUpdateRequestDTO();
        collectorTaskCreateOrUpdateRequestDTO.setDescription("this is a new description");
        collectorTaskCreateOrUpdateRequestDTO.setName("updated collect task name");
        collectorTaskCreateOrUpdateRequestDTO.setCron("0 1 * * * ? *");
        collectorTaskCreateOrUpdateRequestDTO.setState(ReleaseStateEnum.OFFLINE);
        collectorTaskCreateOrUpdateRequestDTO.setAdapterId(adapterId);
        given(scheduler.createSingleHttpTask(Mockito.any(TaskParameter.class))).willReturn(flowId);
        HiveCollectorAdapterParameter hiveCollectorAdapterParameter = new HiveCollectorAdapterParameter();
        hiveCollectorAdapterParameter.setMetastoreUris("thrift://172.21.100.231:9083");
        given(collectorPluginParameterHelper
            .testAdapterConnection(Mockito.any(String.class), Mockito.any(String.class)))
            .willReturn(new CollectorResult(true));
        String request = JSONUtils.toJSONString(collectorTaskCreateOrUpdateRequestDTO);
        ResultActions result = mockMvc
            .perform(MockMvcRequestBuilders.post(new URI(REST_FUNCTION_URL_PREFIX)).content(request)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult create = result.andReturn();
        Assertions.assertEquals(200, create.getResponse().getStatus());
    }

    @Test
    void update() throws Exception {
        String url = REST_FUNCTION_URL_PREFIX;

        CollectorTaskCreateOrUpdateRequestDTO collectorTaskCreateOrUpdateRequestDTO = new CollectorTaskCreateOrUpdateRequestDTO();
        collectorTaskCreateOrUpdateRequestDTO.setDescription("this is a old description");
        collectorTaskCreateOrUpdateRequestDTO.setName("old collect task name");
        collectorTaskCreateOrUpdateRequestDTO.setCron("0 0 * * * ? *");
        collectorTaskCreateOrUpdateRequestDTO.setState(ReleaseStateEnum.OFFLINE);
        collectorTaskCreateOrUpdateRequestDTO.setAdapterId(adapterId);
        given(scheduler.createSingleHttpTask(Mockito.any(TaskParameter.class))).willReturn(flowId);
        HiveCollectorAdapterParameter hiveCollectorAdapterParameter = new HiveCollectorAdapterParameter();
        hiveCollectorAdapterParameter.setMetastoreUris("thrift://172.21.100.231:9083");

        String request = JSONUtils.toJSONString(collectorTaskCreateOrUpdateRequestDTO);
        ResultActions result = mockMvc
            .perform(MockMvcRequestBuilders.post(new URI(url)).content(request)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult create = result.andReturn();
        Assertions.assertEquals(200, create.getResponse().getStatus());

        CollectorTaskCreateOrUpdateRequestDTO collectorTaskCreateOrUpdateNewRequestDTO = new CollectorTaskCreateOrUpdateRequestDTO();
        collectorTaskCreateOrUpdateNewRequestDTO.setDescription("this is a new description");
        collectorTaskCreateOrUpdateNewRequestDTO.setName("new collect task name");
        collectorTaskCreateOrUpdateNewRequestDTO.setCron("0 1 * * * ? *");
        collectorTaskCreateOrUpdateNewRequestDTO.setState(ReleaseStateEnum.ONLINE);
        collectorTaskCreateOrUpdateNewRequestDTO.setAdapterId(adapterId);
        collectorTaskCreateOrUpdateNewRequestDTO.setSchedulerFlowCode(flowId);
        given(scheduler.createSingleHttpTask(Mockito.any(TaskParameter.class))).willReturn(flowId);

        ResultEntity<CollectorTaskResponseDTO> createObj = JSONUtils
            .parseObject(create.getResponse().getContentAsString(),
                new TypeReference<ResultEntity<CollectorTaskResponseDTO>>() {
                });
        Assertions.assertNotNull(createObj.getData().getId());
        ResultActions upResult = mockMvc
            .perform(MockMvcRequestBuilders
                .put(REST_FUNCTION_URL_PREFIX + "{id}", createObj.getData().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONUtils.toJSONString(collectorTaskCreateOrUpdateNewRequestDTO)));
        MvcResult update = upResult.andReturn();
        Assertions.assertEquals(200, update.getResponse().getStatus());

    }

    @Test
    void deleteById() throws Exception {
        CollectorTaskCreateOrUpdateRequestDTO collectorTaskCreateOrUpdateRequestDTO = new CollectorTaskCreateOrUpdateRequestDTO();
        collectorTaskCreateOrUpdateRequestDTO.setDescription("this is a deleted description");
        collectorTaskCreateOrUpdateRequestDTO.setName("delete collect task name");
        collectorTaskCreateOrUpdateRequestDTO.setCron("0 0 * * * ? *");
        collectorTaskCreateOrUpdateRequestDTO.setState(ReleaseStateEnum.OFFLINE);
        collectorTaskCreateOrUpdateRequestDTO.setAdapterId(adapterId);
        given(scheduler.createSingleHttpTask(Mockito.any(TaskParameter.class))).willReturn(flowId);
        HiveCollectorAdapterParameter hiveCollectorAdapterParameter = new HiveCollectorAdapterParameter();
        hiveCollectorAdapterParameter.setMetastoreUris("thrift://172.21.100.231:9083");

        String request = JSONUtils.toJSONString(collectorTaskCreateOrUpdateRequestDTO);
        ResultActions result = mockMvc
            .perform(MockMvcRequestBuilders.post(new URI(REST_FUNCTION_URL_PREFIX)).content(request)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult create = result.andReturn();
        Assertions.assertEquals(200, create.getResponse().getStatus());
        ResultEntity<CollectorTaskResponseDTO> createObj = JSONUtils
            .parseObject(create.getResponse().getContentAsString(),
                new TypeReference<ResultEntity<CollectorTaskResponseDTO>>() {
                });

        ResultActions delResult = mockMvc
            .perform(MockMvcRequestBuilders
                .delete(REST_FUNCTION_URL_PREFIX + "{id}", createObj.getData().getId())
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult delete = delResult.andReturn();
        Assertions.assertEquals(200, delete.getResponse().getStatus());
    }

    @Test
    void queryById() throws Exception {
        CollectorTaskCreateOrUpdateRequestDTO collectorTaskCreateOrUpdateRequestDTO = new CollectorTaskCreateOrUpdateRequestDTO();
        collectorTaskCreateOrUpdateRequestDTO.setDescription("this is a query description");
        collectorTaskCreateOrUpdateRequestDTO.setName("queryById");
        collectorTaskCreateOrUpdateRequestDTO.setCron("0 0 * * * ? *");
        collectorTaskCreateOrUpdateRequestDTO.setState(ReleaseStateEnum.OFFLINE);
        collectorTaskCreateOrUpdateRequestDTO.setAdapterId(adapterId);
        given(scheduler.createSingleHttpTask(Mockito.any(TaskParameter.class))).willReturn(flowId);
        HiveCollectorAdapterParameter hiveCollectorAdapterParameter = new HiveCollectorAdapterParameter();
        hiveCollectorAdapterParameter.setMetastoreUris("thrift://172.21.100.231:9083");
        String request = JSONUtils.toJSONString(collectorTaskCreateOrUpdateRequestDTO);
        ResultActions result = mockMvc
            .perform(MockMvcRequestBuilders.post(new URI(REST_FUNCTION_URL_PREFIX)).content(request)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult create = result.andReturn();
        Assertions.assertEquals(200, create.getResponse().getStatus());
        ResultEntity<CollectorTaskResponseDTO> createObj = JSONUtils
            .parseObject(create.getResponse().getContentAsString(),
                new TypeReference<ResultEntity<CollectorTaskResponseDTO>>() {
                });
        ResultActions delResult = mockMvc
            .perform(MockMvcRequestBuilders
                .get(REST_FUNCTION_URL_PREFIX + "{id}", createObj.getData().getId())
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult delete = delResult.andReturn();
        Assertions.assertEquals(200, delete.getResponse().getStatus());

    }

    @Test
    void queryListPage() throws Exception {
        CollectorAdapterCreateOrUpdateRequestDTO collectorAdapterCreateOrUpdateRequestDTO = new CollectorAdapterCreateOrUpdateRequestDTO();
        collectorAdapterCreateOrUpdateRequestDTO.setDescription("this is new a hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setName("hive adapter test01");
        collectorAdapterCreateOrUpdateRequestDTO
            .setParameter("{\"hiveMetastoreUris\":\"thrift://172.21.100.231:9083\"}");
        collectorAdapterCreateOrUpdateRequestDTO.setType("Hive");
        HiveCollectorAdapterParameter hiveCollectorAdapterParameter = new HiveCollectorAdapterParameter();
        hiveCollectorAdapterParameter.setMetastoreUris("thrift://172.21.100.231:9083");
        given(collectorPluginParameterHelper
            .testAdapterConnection(Mockito.any(String.class), Mockito.any(String.class)))
            .willReturn(new CollectorResult(true));

        String adapterRequest = JSONUtils.toJSONString(collectorAdapterCreateOrUpdateRequestDTO);
        ResultActions adapterResult = mockMvc
            .perform(MockMvcRequestBuilders.post(new URI(REST_FUNCTION_URL_ADAPTER_PREFIX))
                .content(adapterRequest)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult adapterCreate = adapterResult.andReturn();
        Assertions.assertEquals(200, adapterCreate.getResponse().getStatus());
        ResultEntity<CollectorTaskResponseDTO> createObj = JSONUtils
            .parseObject(adapterCreate.getResponse().getContentAsString(),
                new TypeReference<ResultEntity<CollectorTaskResponseDTO>>() {
                });

        CollectorTaskCreateOrUpdateRequestDTO collectorTaskCreateOrUpdateRequestDTO = new CollectorTaskCreateOrUpdateRequestDTO();
        collectorTaskCreateOrUpdateRequestDTO.setDescription("this is a query description");
        collectorTaskCreateOrUpdateRequestDTO.setName("query collect task name page");
        collectorTaskCreateOrUpdateRequestDTO.setCron("0 0 * * * ? *");
        collectorTaskCreateOrUpdateRequestDTO.setState(ReleaseStateEnum.OFFLINE);
        collectorTaskCreateOrUpdateRequestDTO.setAdapterId(createObj.getData().getId());
        given(scheduler.createSingleHttpTask(Mockito.any(TaskParameter.class))).willReturn(flowId);

        String request = JSONUtils.toJSONString(collectorTaskCreateOrUpdateRequestDTO);
        ResultActions result = mockMvc
            .perform(MockMvcRequestBuilders.post(new URI(REST_FUNCTION_URL_PREFIX)).content(request)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult create = result.andReturn();
        Assertions.assertEquals(200, create.getResponse().getStatus());

        CollectorTaskCreateOrUpdateRequestDTO collectorTaskCreateOrUpdateRequestSecondDTO = new CollectorTaskCreateOrUpdateRequestDTO();
        collectorTaskCreateOrUpdateRequestSecondDTO
            .setDescription("this is a second query description ");
        collectorTaskCreateOrUpdateRequestSecondDTO.setName("query second collect task name page ");
        collectorTaskCreateOrUpdateRequestSecondDTO.setCron("0 0 * * * ? *");
        collectorTaskCreateOrUpdateRequestSecondDTO.setState(ReleaseStateEnum.OFFLINE);
        collectorTaskCreateOrUpdateRequestSecondDTO.setAdapterId(adapterId);
        given(scheduler.createSingleHttpTask(Mockito.any(TaskParameter.class))).willReturn(flowId);

        String requestSecond = JSONUtils.toJSONString(collectorTaskCreateOrUpdateRequestSecondDTO);
        ResultActions resultSecond = mockMvc
            .perform(MockMvcRequestBuilders.post(new URI(REST_FUNCTION_URL_PREFIX))
                .content(requestSecond)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult createSecond = resultSecond.andReturn();
        Assertions.assertEquals(200, createSecond.getResponse().getStatus());

        ResultActions pageResult = mockMvc
            .perform(MockMvcRequestBuilders
                .get(REST_FUNCTION_URL_PREFIX + "queryListPage")
                .queryParam("type", "Hive")
                .queryParam("adapterName", "hive adapter test01")
                .queryParam("pageNo", "1")
                .queryParam("pageSize", "10")
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult pageList = pageResult.andReturn();
        ResultEntity<PageResponseDTO<CollectorTaskResponseDTO>> page = JSONUtils
            .parseObject(pageList.getResponse().getContentAsString(),
                new TypeReference<ResultEntity<PageResponseDTO<CollectorTaskResponseDTO>>>() {
                });
        Assertions.assertEquals(200, page.getCode());
        Assertions.assertEquals(1, page.getData().getTotal());
    }

    @Test
    void queryList() throws Exception {
        CollectorAdapterCreateOrUpdateRequestDTO collectorAdapterCreateOrUpdateRequestDTO = new CollectorAdapterCreateOrUpdateRequestDTO();
        collectorAdapterCreateOrUpdateRequestDTO.setDescription("this is new a hive adapter");
        collectorAdapterCreateOrUpdateRequestDTO.setName("hive adapter test02");
        collectorAdapterCreateOrUpdateRequestDTO
            .setParameter("{\"hiveMetastoreUris\":\"thrift://172.21.100.231:9083\"}");
        collectorAdapterCreateOrUpdateRequestDTO.setType("Hive");
        HiveCollectorAdapterParameter hiveCollectorAdapterParameter = new HiveCollectorAdapterParameter();
        hiveCollectorAdapterParameter.setMetastoreUris("thrift://172.21.100.231:9083");
        given(collectorPluginParameterHelper
            .testAdapterConnection(Mockito.any(String.class), Mockito.any(String.class)))
            .willReturn(new CollectorResult(true));
        String adapterRequest = JSONUtils.toJSONString(collectorAdapterCreateOrUpdateRequestDTO);
        ResultActions adapterResult = mockMvc
            .perform(MockMvcRequestBuilders.post(new URI(REST_FUNCTION_URL_ADAPTER_PREFIX))
                .content(adapterRequest)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult adapterCreate = adapterResult.andReturn();
        Assertions.assertEquals(200, adapterCreate.getResponse().getStatus());
        ResultEntity<CollectorTaskResponseDTO> createObj = JSONUtils
            .parseObject(adapterCreate.getResponse().getContentAsString(),
                new TypeReference<ResultEntity<CollectorTaskResponseDTO>>() {
                });

        CollectorTaskCreateOrUpdateRequestDTO collectorTaskCreateOrUpdateRequestDTO = new CollectorTaskCreateOrUpdateRequestDTO();
        collectorTaskCreateOrUpdateRequestDTO.setDescription("this is a query description");
        collectorTaskCreateOrUpdateRequestDTO.setName("query collect task name");
        collectorTaskCreateOrUpdateRequestDTO.setCron("0 0 * * * ? *");
        collectorTaskCreateOrUpdateRequestDTO.setState(ReleaseStateEnum.OFFLINE);
        collectorTaskCreateOrUpdateRequestDTO.setAdapterId(createObj.getData().getId());
        given(scheduler.createSingleHttpTask(Mockito.any(TaskParameter.class))).willReturn(flowId);

        String request = JSONUtils.toJSONString(collectorTaskCreateOrUpdateRequestDTO);
        ResultActions result = mockMvc
            .perform(MockMvcRequestBuilders.post(new URI(REST_FUNCTION_URL_PREFIX)).content(request)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult create = result.andReturn();
        Assertions.assertEquals(200, create.getResponse().getStatus());

        CollectorTaskCreateOrUpdateRequestDTO collectorTaskCreateOrUpdateRequestSecondDTO = new CollectorTaskCreateOrUpdateRequestDTO();
        collectorTaskCreateOrUpdateRequestSecondDTO
            .setDescription("this is a second query description ");
        collectorTaskCreateOrUpdateRequestSecondDTO.setName("query second collect task name ");
        collectorTaskCreateOrUpdateRequestSecondDTO.setCron("0 0 * * * ? *");
        collectorTaskCreateOrUpdateRequestSecondDTO.setState(ReleaseStateEnum.OFFLINE);
        collectorTaskCreateOrUpdateRequestSecondDTO.setAdapterId(adapterId);
        given(scheduler.createSingleHttpTask(Mockito.any(TaskParameter.class))).willReturn(flowId);

        String requestSecond = JSONUtils.toJSONString(collectorTaskCreateOrUpdateRequestSecondDTO);
        ResultActions resultSecond = mockMvc
            .perform(MockMvcRequestBuilders.post(new URI(REST_FUNCTION_URL_PREFIX))
                .content(requestSecond)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult createSecond = resultSecond.andReturn();
        Assertions.assertEquals(200, createSecond.getResponse().getStatus());

        ResultActions pageResult = mockMvc
            .perform(MockMvcRequestBuilders
                .get(REST_FUNCTION_URL_PREFIX + "queryList")
                .queryParam("type", "Hive")
                .queryParam("adapterName", "hive adapter test02")
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult pageList = pageResult.andReturn();
        ResultEntity<Collection<CollectorTaskResponseDTO>> list = JSONUtils
            .parseObject(pageList.getResponse().getContentAsString(),
                new TypeReference<ResultEntity<Collection<CollectorTaskResponseDTO>>>() {
                });
        Assertions.assertEquals(200, list.getCode());
        Assertions.assertEquals(1, list.getData().size());
    }

}