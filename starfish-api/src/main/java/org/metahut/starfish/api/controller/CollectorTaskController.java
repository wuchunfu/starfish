package org.metahut.starfish.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.metahut.starfish.api.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "COLLECTOR_TASK_TAG")
@RequestMapping("collectorTask")
public interface CollectorTaskController {

    @PostMapping
    @ApiOperation(value = "create", notes = "CREATE_COLLECTOR_TASK_NOTES")
    ResultEntity<CollectorTaskResponseDTO> create(@RequestBody CollectorTaskCreateOrUpdateRequestDTO requestDTO);

    @PutMapping("/{id}")
    @ApiOperation(value = "update", notes = "UPDATE_COLLECTOR_TASK_NOTES")
    ResultEntity<CollectorTaskResponseDTO> update(@PathVariable(value = "id") Long id, @RequestBody CollectorTaskCreateOrUpdateRequestDTO requestDTO);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "deleteById", notes = "DELETE_COLLECTOR_TASK_BY_ID_NOTES")
    ResultEntity deleteById(@PathVariable(value = "ide") Long id);

    @GetMapping("{id}")
    @ApiOperation(value = "queryDatasourceById", notes = "QUERY_COLLECTOR_TASK_BY_ID_NOTES")
    ResultEntity<CollectorTaskResponseDTO> queryById(@PathVariable(value = "id") Long id);

    @GetMapping("page")
    @ApiOperation(value = "queryPageList", notes = "QUERY_COLLECTOR_TASK_PAGE_NOTES")
    ResultEntity<List<CollectorTaskResponseDTO>> queryPageList(CollectorTaskRequestDTO requestDTO);

    @GetMapping
    @ApiOperation(value = "queryList", notes = "QUERY_COLLECTOR_TASK_LIST_NOTES")
    ResultEntity<List<CollectorTaskResponseDTO>> queryList(CollectorTaskRequestDTO requestDTO);
}
