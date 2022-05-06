package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.DatasourceDataRequestDTO;
import org.metahut.starfish.api.dto.ResultEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "DATASOURCE_TAG")
@RequestMapping("datasource")
public interface DatasourceController {

    /**
     * datasource test connection
     *
     * @param type
     * @param parameter
     * @return
     */
    @GetMapping("testConnection")
    @ApiOperation(value = "testConnection", notes = "TEST_CONNECTION_DATASOURCE_NOTES")
    ResultEntity testConnection(@ApiParam("datasource type") @RequestParam(required = true) String type, @ApiParam("datasource connection param") @RequestParam(required = true) String parameter);

    // insert
    @PostMapping
    @ApiOperation(value = "createDatasource", notes = "CREATE_DATASOURCE_NOTES")
    ResultEntity createDatasource(@RequestBody DatasourceDataRequestDTO datasourceDataRequestDTO);

    // update
    @PutMapping
    @ApiOperation(value = "updateDatasource", notes = "UPDATE_DATASOURCE_NOTES")
    ResultEntity updateDatasource(Long datasourceId,
                                  @RequestBody DatasourceDataRequestDTO datasourceDataRequestDTO);

    // delete
    @DeleteMapping
    @ApiOperation(value = "deleteDatasource", notes = "DELETE_DATASOURCE_NOTES")
    ResultEntity deleteDatasource(Long datasourceId);

    // query
    @GetMapping("queryById")
    @ApiOperation(value = "queryDatasourceById", notes = "QUERY_DATASOURCE_DETAIL_NOTES")
    ResultEntity queryDatasourceById(Long datasourceId);

    // query
    @GetMapping("page")
    @ApiOperation(value = "queryDatasourcePageList", notes = "QUERY_DATASOURCE_PAGE_NOTES")
    ResultEntity queryDatasourcePageList(@RequestBody DatasourceDataRequestDTO datasourceDataRequestDTO,
                                         Long pagesize, Long pageNo);

    // query
    @GetMapping("type")
    @ApiOperation(value = "queryDatasourceType", notes = "QUERY_DATASOURCE_TYPE_NOTES")
    ResultEntity datasourceType();
}
