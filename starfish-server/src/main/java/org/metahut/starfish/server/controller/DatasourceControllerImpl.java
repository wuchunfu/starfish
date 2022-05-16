package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.DatasourceController;
import org.metahut.starfish.api.dto.CreateOrUpdateDatasourceDataRequestDTO;
import org.metahut.starfish.api.dto.DatasourceDataRequestDTO;
import org.metahut.starfish.api.dto.DatasourceDataResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.enums.Status;
import org.metahut.starfish.datasource.api.DatasourceResult;
import org.metahut.starfish.server.service.DatasourceService;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DatasourceControllerImpl implements DatasourceController {

    private final DatasourceService datasourceService;

    private final ConversionService conversionService;

    public DatasourceControllerImpl(DatasourceService datasourceService
            , ConversionService conversionService) {
        this.datasourceService = datasourceService;
        this.conversionService = conversionService;
    }

    public ResultEntity getTypes() {
        return ResultEntity.success(datasourceService.getTypes());
    }

    public ResultEntity getDefaultParameter(String type) {
        return ResultEntity.success(datasourceService.getDefaultParameter(type));
    }

    @Override
    public ResultEntity getSupportDatasourceTypes() {
        return null;
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
        return datasourceResult.isStatus() ? ResultEntity.success(datasourceResult) :
                ResultEntity.of(Status.DATASOURCE_TEST_FAIL.getCode(), String.format(Status.DATASOURCE_TEST_FAIL.getMessage(), datasourceResult.getMessage()));
    }

    @Override
    public ResultEntity<DatasourceDataResponseDTO> createDatasource(CreateOrUpdateDatasourceDataRequestDTO datasourceDataRequestDTO) {
        if (StringUtils.isBlank(datasourceDataRequestDTO.getParamter()) || StringUtils.isBlank(datasourceDataRequestDTO.getType())) {
            return ResultEntity.of(Status.DATASOURCE_PARAMETER_NULL.getCode(), Status.DATASOURCE_PARAMETER_NULL.getMessage());
        }
        DatasourceResult datasourceResult = datasourceService.createDatasource(datasourceDataRequestDTO);
        if (!datasourceResult.isStatus()) {
            return ResultEntity.of(Status.DATASOURCE_PARAMETER_NULL.getCode(), Status.DATASOURCE_PARAMETER_NULL.getMessage());
        }
        DatasourceDataResponseDTO datasourceDataResponseDTO = conversionService.convert(datasourceDataRequestDTO, DatasourceDataResponseDTO.class);
        return ResultEntity.success(datasourceDataResponseDTO);
    }

    @Override
    public ResultEntity<DatasourceDataResponseDTO> updateDatasource(Long datasourceId, CreateOrUpdateDatasourceDataRequestDTO datasourceDataRequestDTO) {
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
    public ResultEntity<List<DatasourceDataResponseDTO>> queryDatasourceList(DatasourceDataRequestDTO datasourceDataRequestDTO) {
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
