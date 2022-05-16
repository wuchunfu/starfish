package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.CreateOrUpdateDatasourceDataRequestDTO;
import org.metahut.starfish.api.enums.Status;
import org.metahut.starfish.datasource.api.AbstractDatasourceParameter;
import org.metahut.starfish.datasource.api.DatasourceResult;
import org.metahut.starfish.server.datasource.DatasourcePluginHelper;
import org.metahut.starfish.server.service.DatasourceService;
import org.metahut.starfish.server.utils.ConvertUtils;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Collection;
import java.util.Map;

@Service
public class DatasourceServiceImpl implements DatasourceService {

    private final DatasourcePluginHelper datasourcePluginHelper;
    private final AbstractMetaDataService metaDataService;

    public DatasourceServiceImpl(DatasourcePluginHelper datasourcePluginHelper
            , AbstractMetaDataService metaDataService) {
        this.datasourcePluginHelper = datasourcePluginHelper;
        this.metaDataService = metaDataService;
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
        //testConnection(datasourceDataRequestDTO.getType(), datasourceDataRequestDTO.getParamter());
        Map<String, Object> props = ConvertUtils.getMetaDataProps(datasourceDataRequestDTO, null);
        metaDataService.createEntity(datasourceDataRequestDTO.getTypeId(), datasourceDataRequestDTO.getName(), props);
        return new DatasourceResult(true, Status.SUCCESS.getMessage());
    }
}
