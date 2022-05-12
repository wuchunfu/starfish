package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.controller.MetaDataController;
import org.metahut.starfish.api.dto.BatchMetaDataDTO;
import org.metahut.starfish.api.dto.BatchSchemaDTO;
import org.metahut.starfish.api.dto.CreateOrUpdateDatasourceDataRequestDTO;
import org.metahut.starfish.api.enums.Status;
import org.metahut.starfish.api.exception.DatasourceException;
import org.metahut.starfish.datasource.api.AbstractDatasourceParameter;
import org.metahut.starfish.datasource.api.DatasourceResult;
import org.metahut.starfish.server.converter.factory.DatasourceToBatchMetaDataDTOFactory;
import org.metahut.starfish.server.converter.factory.DatasourceToBatchSchemaDTOFactory;
import org.metahut.starfish.server.datasource.DatasourcePluginHelper;
import org.metahut.starfish.server.service.DatasourceService;
import org.metahut.starfish.server.utils.ConvertUtils;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Service
public class DatasourceServiceImpl implements DatasourceService {

    private final MetaDataController metaDataController;

    private final DatasourceToBatchSchemaDTOFactory datasourceToBatchSchemaDTOFactory;

    private final DatasourceToBatchMetaDataDTOFactory datasourceToBatchMetaDataDTOFactory;

    private final DatasourcePluginHelper datasourcePluginHelper;

    public DatasourceServiceImpl(DatasourcePluginHelper datasourcePluginHelper
            , DatasourceToBatchMetaDataDTOFactory datasourceToBatchMetaDataDTOFactory
            , DatasourceToBatchSchemaDTOFactory datasourceToBatchSchemaDTOFactory
            , MetaDataController metaDataController) {
        this.datasourcePluginHelper = datasourcePluginHelper;
        this.datasourceToBatchMetaDataDTOFactory = datasourceToBatchMetaDataDTOFactory;
        this.datasourceToBatchSchemaDTOFactory = datasourceToBatchSchemaDTOFactory;
        this.metaDataController = metaDataController;
    }

    @Override
    public Collection<String> getTypes() {
        return datasourcePluginHelper.getTypes();
    }

    @Override
    public AbstractDatasourceParameter getDefaultParameter(String type) {
        return datasourcePluginHelper.getDefaultParameter(type);
    }

    @Override
    public DatasourceResult testConnection(String type, String parameter) {
        return datasourcePluginHelper.generateInstance(type, parameter).testConnection();
    }

    @Override
    @Transactional
    public DatasourceResult createDatasource(CreateOrUpdateDatasourceDataRequestDTO datasourceDataRequestDTO) {
        testConnection(datasourceDataRequestDTO.getType(), datasourceDataRequestDTO.getParamter());
        Map<String, Object> requestDTOMap = ConvertUtils.getBatchAll(datasourceToBatchSchemaDTOFactory, datasourceToBatchMetaDataDTOFactory, Arrays.asList(datasourceDataRequestDTO));
        if (!requestDTOMap.containsKey("type") || !requestDTOMap.containsKey("instance")) {
            return new DatasourceResult(false, Status.DATASOURCE_PARAMETER_NULL.getMessage());
        }
        try {
            metaDataController.batchType((BatchSchemaDTO) requestDTOMap.get("type"));
            metaDataController.batchInstances((BatchMetaDataDTO) requestDTOMap.get("instance"));
        } catch (Exception e) {
            throw new DatasourceException(Status.DATASOURCE_PARAMETER_NULL, e);
        }
        return new DatasourceResult(true, Status.SUCCESS.getMessage());
    }
}
