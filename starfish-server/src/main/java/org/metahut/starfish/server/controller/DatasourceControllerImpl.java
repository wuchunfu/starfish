package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.DatasourceController;
import org.metahut.starfish.api.dto.DatasourceDataRequestDTO;
import org.metahut.starfish.api.dto.DatasourceDataResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.enums.Status;
import org.metahut.starfish.datasource.api.DatasourceResult;
import org.metahut.starfish.server.service.DatasourceService;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatasourceControllerImpl implements DatasourceController {

    private final DatasourceService datasourceService;

    public DatasourceControllerImpl(DatasourceService datasourceService) {
        this.datasourceService = datasourceService;
    }

    public ResultEntity getTypes() {
        return ResultEntity.success(datasourceService.getTypes());
    }

    public ResultEntity getDefaultParameter(String type) {
        return ResultEntity.success(datasourceService.getDefaultParameter(type));
    }

    /**
     * test connection
     *
     * @param type
     * @param parameter
     * @return
     */
    @Override
    public ResultEntity testConnection(String type, String parameter) {
        DatasourceResult datasourceResult = datasourceService.testConnection(type, parameter);
        return datasourceResult.isStatus() ? ResultEntity.success(datasourceResult) : ResultEntity.of(Status.DATASOURCE_TEST_FAIL.getCode(), String.format(Status.DATASOURCE_TEST_FAIL.getMessage(), datasourceResult.getMessage()));
    }

    // insert
    @Override
    public ResultEntity createDatasource(DatasourceDataRequestDTO datasourceDataRequestDTO) {

        return null;
    }

    // update
    @Override
    public ResultEntity updateDatasource(Long datasourceId,
                                         DatasourceDataRequestDTO datasourceDataRequestDTO) {
        return null;
    }

    // delete
    @Override
    public ResultEntity deleteDatasource(Long datasourceId) {
        return null;
    }

    // query
    @Override
    public ResultEntity queryDatasourceById(Long datasourceId) {
        return null;
    }

    // page query
    @Override
    public ResultEntity queryDatasourcePageList(DatasourceDataRequestDTO datasourceDataRequestDTO) {
        return null;
    }

    @Override
    public ResultEntity<DatasourceDataResponseDTO> queryDatasourceList(DatasourceDataRequestDTO datasourceDataRequestDTO) {
        return null;
    }

    @Override
    public ResultEntity datasourceType() {
        return null;
    }

    @Override
    public ResultEntity queryDatasourceInstance(String type) {
        return null;
    }
}
