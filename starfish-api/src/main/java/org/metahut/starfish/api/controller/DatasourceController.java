package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.DatasourceDataRequestDTO;
import org.metahut.starfish.api.dto.DatasourceDataResponseDTO;
import org.metahut.starfish.api.dto.DatasourceTypeResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "datasource type", required = true, dataType = "String"),
                        @ApiImplicitParam(name = "ingestionCollectorRequestDTO", value = "collector update request dto", required = true,
                    dataType = "IngestionCollectorRequestDTO")})
    ResultEntity testConnection(@RequestParam(required = true) String type, String parameter);

    // insert
    @PostMapping
    @ApiOperation(value = "createDatasource", notes = "CREATE_DATASOURCE_NOTES")
    ResultEntity<DatasourceDataResponseDTO> createDatasource(@RequestBody DatasourceDataRequestDTO datasourceDataRequestDTO);

    // update
    @PutMapping("/{code}")
    @ApiOperation(value = "updateDatasource", notes = "UPDATE_DATASOURCE_NOTES")
    ResultEntity<DatasourceDataResponseDTO> updateDatasource(@PathVariable(value = "code", required = true) Long datasourceId,
                                                             @RequestBody DatasourceDataRequestDTO datasourceDataRequestDTO);

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
    ResultEntity<DatasourceDataResponseDTO> queryDatasourcePageList(@RequestBody DatasourceDataRequestDTO datasourceDataRequestDTO);

    @GetMapping
    @ApiOperation(value = "queryDatasourceList", notes = "QUERY_DATASOURCE_NOTES")
    ResultEntity<DatasourceDataResponseDTO> queryDatasourceList(@RequestBody DatasourceDataRequestDTO datasourceDataRequestDTO);

    // query
    @GetMapping("type")
    @ApiOperation(value = "queryDatasourceType", notes = "QUERY_DATASOURCE_TYPE_NOTES")
    ResultEntity<DatasourceTypeResponseDTO> datasourceType();

    //query datasource instance
    @GetMapping("queryDatasourceInstance")
    @ApiOperation(value = "queryDatasourceInstance", notes = "QUERY_DATASOURCE_INSTANCE_NOTES")
    ResultEntity<DatasourceDataResponseDTO> queryDatasourceInstance(@RequestParam(required = false) String type);

}
