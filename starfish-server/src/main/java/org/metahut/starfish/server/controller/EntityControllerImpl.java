package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.EntityController;
import org.metahut.starfish.api.dto.EntityQueryDTO;
import org.metahut.starfish.api.dto.HiveClusterQueryDTO;
import org.metahut.starfish.api.dto.HiveDBQueryDTO;
import org.metahut.starfish.api.dto.HiveTableQueryDTO;
import org.metahut.starfish.api.dto.HiveTableResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.Collection;

@RestController
public class EntityControllerImpl implements EntityController {

    @Resource
    private AbstractMetaDataService<Long,Object> abstractMetaDataService;

    @Override
    public ResultEntity<EntityQueryDTO> querySample(EntityQueryDTO entityQueryDTO) {
        return null;
    }

    @Override
    public ResultEntity<Collection<HiveClusterQueryDTO>> hiveClusters() {
        return ResultEntity.success(abstractMetaDataService.instancesByTypeName("org.starfish.HiveTable",HiveClusterQueryDTO.class));
    }

    @Override
    public ResultEntity<Collection<HiveDBQueryDTO>> hiveDbs() {
        return ResultEntity.success(abstractMetaDataService.instancesByTypeName("org.starfish.HiveDB",HiveDBQueryDTO.class));
    }

    @Override
    public ResultEntity<PageResponseDTO<HiveTableResponseDTO>> tables(HiveTableQueryDTO hiveTableQueryDTO) {
        Pageable pageable = PageRequest.of(hiveTableQueryDTO.getPageNo() - 1, hiveTableQueryDTO.getPageSize());
        Page<HiveTableResponseDTO> instances = abstractMetaDataService.instances(hiveTableQueryDTO.toQueryCondition(), pageable);
        PageResponseDTO<HiveTableResponseDTO> result = new PageResponseDTO<>();
        result.setData(instances.getContent());
        result.setPageNo(result.getPageNo());
        result.setPageSize(result.getPageSize());
        result.setTotal(result.getTotal());
        return ResultEntity.success(result);

    }
}
