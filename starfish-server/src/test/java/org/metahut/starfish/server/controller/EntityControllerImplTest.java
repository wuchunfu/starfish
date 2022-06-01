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
import org.metahut.starfish.store.rdbms.dao.NodeEntityMapper;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.repository.NodeEntityPropertyRepository;
import org.metahut.starfish.store.rdbms.repository.NodeEntityRepository;
import org.metahut.starfish.unit.enums.TypeCategory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

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

    @Autowired
    private NodeEntityMapper nodeEntityMapper;

    @Autowired
    private NodeEntityRepository nodeEntityRepository;

    @Autowired
    private NodeEntityPropertyRepository  nodeEntityPropertyRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    @Disabled
    public void testFindByIdAndPropertyCache() {
        transactionTemplate.execute((status) -> {
            NodeEntity byId1 = nodeEntityMapper.findById(1525L);
            byId1.getProperties();
            NodeEntity node1 = nodeEntityMapper.findByCategoryAndQualifiedName(TypeCategory.ENTITY.name(), "hive@@dwdd@@dwd_agt_ass_asset_all@@spu_id");
            NodeEntity node2 = nodeEntityMapper.findByCategoryAndQualifiedName(TypeCategory.ENTITY.name(), "hive@@dwdd@@dwd_agt_ass_asset_all@@spu_id");
            return Boolean.TRUE;
        });
        transactionTemplate.execute((status) -> {
            NodeEntity byId2 = nodeEntityMapper.findById(1525L);
            byId2.getProperties();
            NodeEntity node1 = nodeEntityMapper.findByCategoryAndQualifiedName(TypeCategory.ENTITY.name(), "hive@@dwdd@@dwd_agt_ass_asset_all@@spu_id");
            NodeEntity node2 = nodeEntityMapper.findByCategoryAndQualifiedName(TypeCategory.ENTITY.name(), "hive@@dwdd@@dwd_agt_ass_asset_all@@spu_id");
            return Boolean.TRUE;
        });
    }

    @Test
    @Disabled
    @Transactional
    void testCache() {
        for (int i = 0; i < 100;i++) {
            NodeEntity byId = nodeEntityMapper.findById(1525L);
            byId.setUpdateTime(null);
            NodeEntity update = nodeEntityMapper.update(byId);
            NodeEntity node1 = nodeEntityMapper.findByCategoryAndQualifiedName(TypeCategory.ENTITY.name(), "hive@@dwdd@@dwd_agt_ass_asset_all@@spu_id");
        }
    }
}
