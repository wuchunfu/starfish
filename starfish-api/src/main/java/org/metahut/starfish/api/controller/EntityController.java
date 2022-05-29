package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.EntityQueryDTO;
import org.metahut.starfish.api.dto.HiveClusterQueryDTO;
import org.metahut.starfish.api.dto.HiveDBQueryDTO;
import org.metahut.starfish.api.dto.HiveTableQueryDTO;
import org.metahut.starfish.api.dto.HiveTableResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.PulsarClusterResponseDTO;
import org.metahut.starfish.api.dto.PulsarTopicQueryDTO;
import org.metahut.starfish.api.dto.PulsarTopicResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.unit.TypeNameQueryCondition;
import org.metahut.starfish.unit.TypeNameQueryConditionWithPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Map;

@Api(tags = "ENTITY_TAG")
@RequestMapping("entity")
public interface EntityController {

    @PostMapping("queryByTypeNameAndCondition")
    ResultEntity<Collection<Map>> queryByTypeNameAndCondition(TypeNameQueryCondition typeNameQueryCondition);

    @GetMapping("queryByTypeNameAndConditionWithPage")
    ResultEntity<PageResponseDTO<Map>> queryByTypeNameAndConditionWithPage(TypeNameQueryConditionWithPage condition);

    @GetMapping("querySample")
    @ApiOperation(value = "querySample", notes = "QUERY_SAMPLE")
    ResultEntity<EntityQueryDTO> querySample(EntityQueryDTO entityQueryDTO);

    @GetMapping("hiveClusters")
    ResultEntity<Collection<HiveClusterQueryDTO>> hiveClusters();

    @GetMapping("hiveDbs")
    ResultEntity<Collection<HiveDBQueryDTO>> hiveDbs();

    @GetMapping("tables")
    ResultEntity<PageResponseDTO<HiveTableResponseDTO>> tables(HiveTableQueryDTO hiveTableQueryDTO);

    @GetMapping("pulsarClusters")
    ResultEntity<Collection<PulsarClusterResponseDTO>> pulsarClusters();

    @GetMapping("pulsarTopics")
    ResultEntity<PageResponseDTO<PulsarTopicResponseDTO>> topics(PulsarTopicQueryDTO pulsarTopicQueryDTO);

}
