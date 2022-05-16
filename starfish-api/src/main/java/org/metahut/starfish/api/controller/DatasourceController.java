package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.CreateOrUpdateDatasourceDataRequestDTO;
import org.metahut.starfish.api.dto.DatasourceDataRequestDTO;
import org.metahut.starfish.api.dto.DatasourceDataResponseDTO;
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

@Api(tags = "DATASOURCE_TAG")
@RequestMapping("datasource")
public interface DatasourceController {

    /**
     *
     * @return
     */
    ResultEntity getSupportDatasourceTypes();

    /**
     * datasource test connection
     *
     * @param type
     * @param parameter
     * @return
     */
    @GetMapping("testConnection")
    @ApiOperation(value = "testConnection", notes = "TEST_CONNECTION_DATASOURCE_NOTES")
    ResultEntity testConnection(@RequestParam(required = true) String type, String parameter);

    // insert
    @PostMapping
    @ApiOperation(value = "createDatasource", notes = "CREATE_DATASOURCE_NOTES")
    ResultEntity<DatasourceDataResponseDTO> createDatasource(@RequestBody CreateOrUpdateDatasourceDataRequestDTO datasourceDataRequestDTO);

    // update
    @PutMapping("/{code}")
    @ApiOperation(value = "updateDatasource", notes = "UPDATE_DATASOURCE_NOTES")
    ResultEntity<DatasourceDataResponseDTO> updateDatasource(@PathVariable(value = "code", required = true) Long datasourceId,
                                                             @RequestBody CreateOrUpdateDatasourceDataRequestDTO datasourceDataRequestDTO);

    // delete
    @DeleteMapping("/{code}")
    @ApiOperation(value = "deleteDatasource", notes = "DELETE_DATASOURCE_NOTES")
    ResultEntity deleteDatasource(@PathVariable(value = "code", required = true) Long datasourceId);

    // query
    @GetMapping("queryById")
    @ApiOperation(value = "queryDatasourceById", notes = "QUERY_DATASOURCE_DETAIL_NOTES")
    ResultEntity<DatasourceDataResponseDTO> queryDatasourceById(Long datasourceId);

    // query
    @GetMapping("page")
    @ApiOperation(value = "queryDatasourcePageList", notes = "QUERY_DATASOURCE_PAGE_NOTES")
    ResultEntity<List<DatasourceDataResponseDTO>> queryDatasourcePageList(@RequestBody DatasourceDataRequestDTO datasourceDataRequestDTO);

    @GetMapping
    @ApiOperation(value = "queryDatasourceList", notes = "QUERY_DATASOURCE_NOTES")
    ResultEntity<List<DatasourceDataResponseDTO>> queryDatasourceList(@RequestBody DatasourceDataRequestDTO datasourceDataRequestDTO);

    // query
    @GetMapping("type")
    @ApiOperation(value = "queryDatasourceType", notes = "QUERY_DATASOURCE_TYPE_NOTES")
    ResultEntity<List<String>> datasourceType();

    //query datasource instance
    @GetMapping("queryDatasourceInstance")
    @ApiOperation(value = "queryDatasourceInstance", notes = "QUERY_DATASOURCE_INSTANCE_NOTES")
    ResultEntity<List<DatasourceDataResponseDTO>> queryDatasourceInstance(@RequestParam(required = false) String type);

}
