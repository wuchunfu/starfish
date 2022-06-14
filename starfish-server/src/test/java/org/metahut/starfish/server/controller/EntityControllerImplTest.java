package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.EntityController;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.dto.SourceRequestDTO;
import org.metahut.starfish.api.dto.SourceResponseDTO;
import org.metahut.starfish.store.rdbms.dao.NodeEntityMapper;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.repository.NodeEntityPropertyRepository;
import org.metahut.starfish.store.rdbms.repository.NodeEntityRepository;
import org.metahut.starfish.unit.enums.TypeCategory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.net.URI;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 */

class EntityControllerImplTest extends WebApplicationTest {

    private static final String REST_FUNCTION_URL_PREFIX = "/entity/";

    @Autowired
    private EntityController entityController;

    @Test
    void hiveClusters() throws Exception {
        ResultActions result = mockMvc
            .perform(MockMvcRequestBuilders.get(new URI(REST_FUNCTION_URL_PREFIX + "hiveClusters"))
                .queryParam("pageNo", "1")
                .queryParam("pageSize", "10")
                .queryParam("clusterName","hive")
                .queryParam("description","")
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult clusters = result.andReturn();
        Assertions.assertEquals(200, clusters.getResponse().getStatus());
        assertNotNull(clusters);
    }

    @Test
    void hiveDbs() throws Exception {
        ResultActions result = mockMvc
            .perform(MockMvcRequestBuilders.get(new URI(REST_FUNCTION_URL_PREFIX + "hiveDbs"))
                .queryParam("pageNo", "1")
                .queryParam("pageSize", "10")
                .queryParam("owner","root")
                .queryParam("description","")
                .queryParam("location","hdfs://hacluster/user/hive/warehouse/dmm.db")
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult dbs = result.andReturn();
        Assertions.assertEquals(200, dbs.getResponse().getStatus());
        assertNotNull(dbs);
    }

    @Test
    void hiveTables() throws Exception {
        ResultActions result = mockMvc
            .perform(MockMvcRequestBuilders.get(new URI(REST_FUNCTION_URL_PREFIX + "hiveTables"))
                .queryParam("pageNo", "1")
                .queryParam("pageSize", "10")
                .queryParam("hiveDbName","default")
                .queryParam("createBeginTime",String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-05-01 00:00:00")))
                .queryParam("createEndTime",String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-06-07 00:00:00")))
                .queryParam("updateBeginTime",String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-05-01 00:00:00")))
                .queryParam("updateEndTime",String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-06-07 00:00:00")))
                .contentType(MediaType.APPLICATION_JSON));
        assertNotNull(result);
    }

    @Test
    void pulsarClusters() throws Exception {
        ResultActions result = mockMvc
            .perform(MockMvcRequestBuilders.get(new URI(REST_FUNCTION_URL_PREFIX + "pulsarClusters"))
                .queryParam("pageNo", "1")
                .queryParam("pageSize", "10")
                .queryParam("name","pulsar-cluster-qa")
                .contentType(MediaType.APPLICATION_JSON));
        assertNotNull(result);
    }

    @Test
    void pulsarTopics() throws Exception {
        ResultActions result = mockMvc
            .perform(MockMvcRequestBuilders.get(new URI(REST_FUNCTION_URL_PREFIX + "pulsarTopics"))
                .queryParam("pageNo", "1")
                .queryParam("pageSize", "10")
                .queryParam("clusterId","7682")
                .contentType(MediaType.APPLICATION_JSON));
        assertNotNull(result);
    }

    @Autowired
    private NodeEntityMapper nodeEntityMapper;

    @Autowired
    private NodeEntityRepository nodeEntityRepository;

    @Autowired
    private NodeEntityPropertyRepository nodeEntityPropertyRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    @Disabled
    public void testFindByIdAndPropertyCache() {
        transactionTemplate.execute((status) -> {
            NodeEntity byId1 = nodeEntityMapper.findById(1525L);
            byId1.getProperties();
            NodeEntity node1 = nodeEntityMapper
                .findByCategoryAndQualifiedName(TypeCategory.ENTITY.name(),
                    "hive@@dwdd@@dwd_agt_ass_asset_all@@spu_id");
            NodeEntity node2 = nodeEntityMapper
                .findByCategoryAndQualifiedName(TypeCategory.ENTITY.name(),
                    "hive@@dwdd@@dwd_agt_ass_asset_all@@spu_id");
            return Boolean.TRUE;
        });
        transactionTemplate.execute((status) -> {
            NodeEntity byId2 = nodeEntityMapper.findById(1525L);
            byId2.getProperties();
            NodeEntity node1 = nodeEntityMapper
                .findByCategoryAndQualifiedName(TypeCategory.ENTITY.name(),
                    "hive@@dwdd@@dwd_agt_ass_asset_all@@spu_id");
            NodeEntity node2 = nodeEntityMapper
                .findByCategoryAndQualifiedName(TypeCategory.ENTITY.name(),
                    "hive@@dwdd@@dwd_agt_ass_asset_all@@spu_id");
            return Boolean.TRUE;
        });
    }

    @Test
    @Disabled
    @Transactional
    void testCache() {
        for (int i = 0; i < 100; i++) {
            NodeEntity byId = nodeEntityMapper.findById(1525L);
            byId.setUpdateTime(null);
            NodeEntity update = nodeEntityMapper.update(byId);
            NodeEntity node1 = nodeEntityMapper
                .findByCategoryAndQualifiedName(TypeCategory.ENTITY.name(),
                    "hive@@dwdd@@dwd_agt_ass_asset_all@@spu_id");
        }
    }

    @Test
    void testSources() {
        SourceRequestDTO sourceRequestDTO = new SourceRequestDTO();
        sourceRequestDTO.setPageNo(1);
        sourceRequestDTO.setPageSize(1);
        sourceRequestDTO.setName("Hive");
        ResultEntity<PageResponseDTO<SourceResponseDTO>> sources = entityController.sources(sourceRequestDTO);
        Assertions.assertNotNull(sources);
    }
}
