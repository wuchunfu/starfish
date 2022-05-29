package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.EntityController;
import org.metahut.starfish.api.dto.EntityQueryDTO;
import org.metahut.starfish.api.dto.HiveClusterResponseDTO;
import org.metahut.starfish.api.dto.HiveDBResponseDTO;
import org.metahut.starfish.api.dto.HiveTableQueryDTO;
import org.metahut.starfish.api.dto.HiveTableResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.PulsarClusterResponseDTO;
import org.metahut.starfish.api.dto.PulsarTopicQueryDTO;
import org.metahut.starfish.api.dto.PulsarTopicResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.service.AbstractMetaDataService;
import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.TypeNameQueryCondition;
import org.metahut.starfish.unit.TypeNameQueryConditionWithPage;
import org.metahut.starfish.unit.expression.ConditionPiece;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static org.metahut.starfish.api.Constants.HIVE_DB_TYPE_NAME;
import static org.metahut.starfish.api.Constants.HIVE_TABLE_TYPE_NAME;
import static org.metahut.starfish.api.Constants.PULSAR_CLUSTER_TYPE_NAME;

@RestController
public class EntityControllerImpl implements EntityController {

    @Resource
    private AbstractMetaDataService<Long,Object> abstractMetaDataService;

    @Override
    public ResultEntity<EntityQueryDTO> querySample(EntityQueryDTO entityQueryDTO) {
        return null;
    }

    @Override
    public ResultEntity<Collection<Map>> queryByTypeNameAndCondition(TypeNameQueryCondition typeNameQueryCondition) {
        AbstractQueryCondition<Map> condition = typeNameQueryCondition;
        if (condition.getFilters() == null) {
            condition.setFilters(new ArrayList<>());
        }
        condition.getFilters().add(ConditionPiece.entityWithType(typeNameQueryCondition.getTypeName()));
        return ResultEntity.success(abstractMetaDataService.instances(condition));
    }

    @Override
    public ResultEntity<PageResponseDTO<Map>> queryByTypeNameAndConditionWithPage(TypeNameQueryConditionWithPage typeNameQueryCondition) {
        AbstractQueryCondition<Map> condition = typeNameQueryCondition;
        if (condition.getFilters() == null) {
            condition.setFilters(new ArrayList<>());
        }
        condition.getFilters().add(ConditionPiece.entityWithType(typeNameQueryCondition.getTypeName()));
        Page<Map> result = abstractMetaDataService.instances(condition,PageRequest.of(typeNameQueryCondition.getPageNo() - 1, typeNameQueryCondition.getPageNo()));
        return ResultEntity.success(PageResponseDTO.of(result.getNumber(),result.getSize(),result.getTotalElements(),result.getContent()));
    }

    @Override
    public ResultEntity<Collection<HiveClusterResponseDTO>> hiveClusters() {
        return ResultEntity.success(abstractMetaDataService.instancesByTypeName(HIVE_TABLE_TYPE_NAME,HiveClusterResponseDTO.class));
    }

    @Override
    public ResultEntity<Collection<HiveDBResponseDTO>> hiveDbs() {
        return ResultEntity.success(abstractMetaDataService.instancesByTypeName(HIVE_DB_TYPE_NAME,HiveDBResponseDTO.class));
    }

    @Override
    public ResultEntity<PageResponseDTO<HiveTableResponseDTO>> hiveTables(HiveTableQueryDTO hiveTableQueryDTO) {
        Pageable pageable = PageRequest.of(hiveTableQueryDTO.getPageNo() - 1, hiveTableQueryDTO.getPageSize());
        Page<HiveTableResponseDTO> instances = abstractMetaDataService.instances(hiveTableQueryDTO.toQueryCondition(), pageable);
        PageResponseDTO<HiveTableResponseDTO> result = new PageResponseDTO<>();
        result.setData(instances.getContent());
        result.setPageNo(result.getPageNo());
        result.setPageSize(result.getPageSize());
        result.setTotal(result.getTotal());
        return ResultEntity.success(result);

    }

    @Override
    public ResultEntity<Collection<PulsarClusterResponseDTO>> pulsarClusters() {
        return ResultEntity.success(abstractMetaDataService.instancesByTypeName(PULSAR_CLUSTER_TYPE_NAME,PulsarClusterResponseDTO.class));
    }

    @Override
    public ResultEntity<PageResponseDTO<PulsarTopicResponseDTO>> pulsarTopics(PulsarTopicQueryDTO pulsarTopicQueryDTO) {
        Pageable pageable = PageRequest.of(pulsarTopicQueryDTO.getPageNo() - 1, pulsarTopicQueryDTO.getPageSize());
        Page<PulsarTopicResponseDTO> instances = abstractMetaDataService.instances(pulsarTopicQueryDTO.toCondition(), pageable);
        PageResponseDTO<PulsarTopicResponseDTO> result = new PageResponseDTO<>();
        result.setData(instances.getContent());
        result.setPageNo(result.getPageNo());
        result.setPageSize(result.getPageSize());
        result.setTotal(result.getTotal());
        return ResultEntity.success(result);
    }
}
