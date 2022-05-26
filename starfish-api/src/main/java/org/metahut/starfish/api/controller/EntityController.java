package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.EntityQueryDTO;
import org.metahut.starfish.api.dto.HiveClusterQueryDTO;
import org.metahut.starfish.api.dto.HiveDBQueryDTO;
import org.metahut.starfish.api.dto.HiveTableQueryDTO;
import org.metahut.starfish.api.dto.HiveTableResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Api(tags = "ENTITY_TAG")
@RequestMapping("entity")
public interface EntityController {

    @GetMapping("querySample")
    @ApiOperation(value = "querySample", notes = "QUERY_SAMPLE")
    ResultEntity<EntityQueryDTO> querySample(EntityQueryDTO entityQueryDTO);

    @GetMapping("hiveClusters")
    ResultEntity<Collection<HiveClusterQueryDTO>> hiveClusters();

    @GetMapping("hiveDbs")
    ResultEntity<Collection<HiveDBQueryDTO>> hiveDbs();

    @GetMapping("tables")
    ResultEntity<PageResponseDTO<HiveTableResponseDTO>> tables(HiveTableQueryDTO hiveTableQueryDTO);

}
