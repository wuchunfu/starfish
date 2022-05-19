package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.CollectorAdapterCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "COLLECTOR_ADAPTER_TAG")
@RequestMapping("collectorAdapter")
public interface CollectorAdapterController {

    @GetMapping("testConnection")
    @ApiOperation(value = "testConnection", notes = "TEST_CONNECTION_COLLECTOR_ADAPTER_NOTES")
    ResultEntity testConnection(@RequestParam String type, @RequestParam String parameter);

    @PostMapping
    @ApiOperation(value = "create", notes = "CREATE_COLLECTOR_ADAPTER_NOTES")
    ResultEntity<CollectorAdapterResponseDTO> create(@RequestBody CollectorAdapterCreateOrUpdateRequestDTO requestDTO);

    @PutMapping("{id}")
    @ApiOperation(value = "update", notes = "UPDATE_COLLECTOR_ADAPTER_NOTES")
    ResultEntity<CollectorAdapterResponseDTO> update(@PathVariable(value = "id") Long id, @RequestBody CollectorAdapterCreateOrUpdateRequestDTO requestDTO);

    @DeleteMapping("{id}")
    @ApiOperation(value = "deleteById", notes = "DELETE_COLLECTOR_ADAPTER_BY_ID_NOTES")
    ResultEntity deleteById(@PathVariable(value = "ide") Long id);

    @GetMapping("{id}")
    @ApiOperation(value = "queryDatasourceById", notes = "QUERY_COLLECTOR_ADAPTER_BY_ID_NOTES")
    ResultEntity<CollectorAdapterResponseDTO> queryById(@PathVariable(value = "id") Long id);

    @GetMapping("queryListPage")
    @ApiOperation(value = "queryListPage", notes = "QUERY_COLLECTOR_ADAPTER_PAGE_NOTES")
    ResultEntity<List<CollectorAdapterResponseDTO>> queryListPage(CollectorAdapterRequestDTO requestDTO);

    @GetMapping("queryList")
    @ApiOperation(value = "queryList", notes = "QUERY_COLLECTOR_ADAPTER_LIST_NOTES")
    ResultEntity<List<CollectorAdapterResponseDTO>> queryList(CollectorAdapterRequestDTO requestDTO);

}
