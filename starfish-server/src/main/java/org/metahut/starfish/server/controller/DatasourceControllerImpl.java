package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.DatasourceController;
import org.metahut.starfish.api.dto.CreateOrUpdateDatasourceDataRequestDTO;
import org.metahut.starfish.api.dto.DatasourceDataRequestDTO;
import org.metahut.starfish.api.dto.DatasourceDataResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.enums.Status;
import org.metahut.starfish.server.service.DatasourceService;
import org.metahut.starfish.server.utils.Assert;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DatasourceControllerImpl implements DatasourceController {

    private final DatasourceService datasourceService;

    private final ConversionService conversionService;

    public DatasourceControllerImpl(DatasourceService datasourceService
            , ConversionService conversionService) {
        this.datasourceService = datasourceService;
        this.conversionService = conversionService;
    }

    @Override
    public ResultEntity getSupportDatasourceTypes() {
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
        boolean status = datasourceService.testConnection(type, parameter);
        return status ? ResultEntity.success(Status.SUCCESS.getMessage()) :
                ResultEntity.of(Status.DATASOURCE_TEST_FAIL.getCode(), String.format(Status.DATASOURCE_TEST_FAIL.getMessage()));
    }

    @Override
    public ResultEntity<DatasourceDataResponseDTO> createDatasource(CreateOrUpdateDatasourceDataRequestDTO datasourceDataRequestDTO) {
        if (StringUtils.isBlank(datasourceDataRequestDTO.getParamter()) || StringUtils.isBlank(datasourceDataRequestDTO.getType())) {
            return ResultEntity.of(Status.DATASOURCE_PARAMETER_NULL.getCode(), Status.DATASOURCE_PARAMETER_NULL.getMessage());
        }
        Long dataSourceId = datasourceService.createDatasource(datasourceDataRequestDTO);
        DatasourceDataResponseDTO datasourceDataResponseDTO = new DatasourceDataResponseDTO();
        datasourceDataResponseDTO.setDatasourceId(dataSourceId);
        return ResultEntity.success(datasourceDataResponseDTO);
    }

    @Override
    public ResultEntity<DatasourceDataResponseDTO> updateDatasource(Long datasourceId, CreateOrUpdateDatasourceDataRequestDTO datasourceDataRequestDTO) {
        DatasourceDataResponseDTO datasourceDataResponseDTO = null;
        datasourceService.updateDatasource(datasourceId, datasourceDataRequestDTO);

        try {
            datasourceDataResponseDTO = conversionService.convert(datasourceDataRequestDTO, DatasourceDataResponseDTO.class);
        } catch (Exception e) {
            Assert.throwException(Status.DATASOURCE_CONVERT_PARAMETER_ERROR,null,e.getCause());
        }
        return ResultEntity.success(datasourceDataResponseDTO);
    }


    // delete
    @Override
    public ResultEntity deleteDatasource(Long datasourceId) {
        datasourceService.deleteDatasource(datasourceId);

        return ResultEntity.success(datasourceId);
    }

    // query
    @Override
    public ResultEntity<DatasourceDataResponseDTO> queryDatasourceById(Long datasourceId) {
        DatasourceDataResponseDTO datasourceDataResponseDTO = null;
        Object datasource = datasourceService.queryDatasourceById(datasourceId);

        try {
            datasourceDataResponseDTO = conversionService.convert(datasource, DatasourceDataResponseDTO.class);
        } catch (Exception e) {
            Assert.throwException(Status.DATASOURCE_CONVERT_PARAMETER_ERROR,null,e.getCause());
        }

        return ResultEntity.success(datasourceDataResponseDTO);
    }

    // page query
    @Override
    public ResultEntity<List<DatasourceDataResponseDTO>> queryDatasourcePageList(DatasourceDataRequestDTO datasourceDataRequestDTO) {
        List<DatasourceDataResponseDTO> datasourceList = null;
        List<Object> datasourceResult = datasourceService.queryDatasourcePageList(datasourceDataRequestDTO);
        try {
            datasourceList = datasourceResult.stream().map(datasource -> {
                return conversionService.convert(datasource, DatasourceDataResponseDTO.class);
            }).collect(Collectors.toList());
        } catch (Exception e) {
            Assert.throwException(Status.DATASOURCE_CONVERT_PARAMETER_ERROR,null,e.getCause());
        }
        return ResultEntity.success(datasourceList);
    }

    @Override
    public ResultEntity<List<DatasourceDataResponseDTO>> queryDatasourceList(DatasourceDataRequestDTO datasourceDataRequestDTO) {
        List<DatasourceDataResponseDTO> datasourceList = null;
        List<Object> datasourceResult = datasourceService.queryDatasourceList(datasourceDataRequestDTO);
        try {
            datasourceList = datasourceResult.stream().map(datasource -> {
                return conversionService.convert(datasource, DatasourceDataResponseDTO.class);
            }).collect(Collectors.toList());
        } catch (Exception e) {
            Assert.throwException(Status.DATASOURCE_CONVERT_PARAMETER_ERROR,null,e.getCause());
        }
        return ResultEntity.success(datasourceList);
    }

    @Override
    public ResultEntity<List<String>> datasourceType() {
        List<String> datasourceResult = datasourceService.datasourceType();

        return ResultEntity.success(datasourceResult);
    }

    @Override
    public ResultEntity<List<DatasourceDataResponseDTO>> queryDatasourceInstance(String type) {
        List<DatasourceDataResponseDTO> datasourceList = null;
        List<Object> datasourceResult = datasourceService.queryDatasourceInstance(type);
        try {
            datasourceList = datasourceResult.stream().map(datasource -> {
                return conversionService.convert(datasource, DatasourceDataResponseDTO.class);
            }).collect(Collectors.toList());
        } catch (Exception e) {
            Assert.throwException(Status.DATASOURCE_CONVERT_PARAMETER_ERROR,null,e.getCause());
        }
        return ResultEntity.success(datasourceList);
    }
}
