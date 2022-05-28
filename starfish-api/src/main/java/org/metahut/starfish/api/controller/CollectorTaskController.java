package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.CollectorTaskCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskCreateOrUpdateRequestDTO.Update;
import org.metahut.starfish.api.dto.CollectorTaskInstanceLogResponseDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceResponseDTO;
import org.metahut.starfish.api.dto.CollectorTaskRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Api(tags = "COLLECTOR_TASK_TAG")
@RequestMapping("collectorTask")
@Validated
public interface CollectorTaskController {

    // ---- collector task definition

    @PostMapping
    @ApiOperation(value = "create", notes = "CREATE_COLLECTOR_TASK_NOTES")
    ResultEntity<CollectorTaskResponseDTO> create(@RequestBody @Validated CollectorTaskCreateOrUpdateRequestDTO requestDTO);

    @PutMapping("{id}")
    @ApiOperation(value = "update", notes = "UPDATE_COLLECTOR_TASK_NOTES")
    ResultEntity<CollectorTaskResponseDTO> update(@PathVariable(value = "id") Long id, @RequestBody @Validated({Update.class}) CollectorTaskCreateOrUpdateRequestDTO requestDTO);

    @DeleteMapping("{id}")
    @ApiOperation(value = "deleteById", notes = "DELETE_COLLECTOR_TASK_BY_ID_NOTES")
    ResultEntity deleteById(@PathVariable(value = "ide") Long id);

    @GetMapping("{id}")
    @ApiOperation(value = "queryDatasourceById", notes = "QUERY_COLLECTOR_TASK_BY_ID_NOTES")
    ResultEntity<CollectorTaskResponseDTO> queryById(@PathVariable(value = "id") Long id);

    @GetMapping("queryListPage")
    @ApiOperation(value = "queryListPage", notes = "QUERY_COLLECTOR_TASK_PAGE_NOTES")
    ResultEntity<PageResponseDTO<CollectorTaskResponseDTO>> queryListPage(CollectorTaskRequestDTO requestDTO);

    @GetMapping("queryList")
    @ApiOperation(value = "queryList", notes = "QUERY_COLLECTOR_TASK_LIST_NOTES")
    ResultEntity<Collection<CollectorTaskResponseDTO>> queryList(CollectorTaskRequestDTO requestDTO);

    // ---- collector task instance

    @ApiOperation(value = "queryInstanceListPage", notes = "QUERY_COLLECTOR_TASK_INSTANCE_PAGE_NOTES")
    @GetMapping("instance/queryListPage")
    ResultEntity<PageResponseDTO<CollectorTaskInstanceResponseDTO>> queryInstanceListPage(CollectorTaskInstanceRequestDTO requestDTO);

    @ApiOperation(value = "queryInstanceLog", notes = "QUERY_COLLECTOR_TASK_INSTANCE_LOG_NOTES")
    @GetMapping("instance/log/{id}")
    ResultEntity<CollectorTaskInstanceLogResponseDTO> queryInstanceLog(@PathVariable(value = "id") String instanceId);
}
