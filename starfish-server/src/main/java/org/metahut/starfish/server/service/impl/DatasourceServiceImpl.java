package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.controller.MetaDataController;
import org.metahut.starfish.api.dto.BatchMetaDataDTO;
import org.metahut.starfish.api.dto.BatchSchemaDTO;
import org.metahut.starfish.api.dto.CreateOrUpdateDatasourceDataRequestDTO;
import org.metahut.starfish.api.dto.DatasourceDataRequestDTO;
import org.metahut.starfish.api.enums.Status;
import org.metahut.starfish.datasource.api.AbstractDatasourceParameter;
import org.metahut.starfish.datasource.api.DatasourceResult;
import org.metahut.starfish.server.converter.factory.DatasourceToBatchMetaDataDTOFactory;
import org.metahut.starfish.server.converter.factory.DatasourceToBatchSchemaDTOFactory;
import org.metahut.starfish.server.datasource.DatasourcePluginHelper;
import org.metahut.starfish.server.service.DatasourceService;
import org.metahut.starfish.server.utils.Assert;
import org.metahut.starfish.server.utils.ConvertUtils;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
    public boolean testConnection(String type, String parameter) {
        DatasourceResult result = datasourcePluginHelper.generateInstance(type, parameter).testConnection();
        return result.isStatus();
    }

    @Override
    @Transactional
    public void createDatasource(CreateOrUpdateDatasourceDataRequestDTO datasourceDataRequestDTO) {
        testConnection(datasourceDataRequestDTO.getType(), datasourceDataRequestDTO.getParamter());
        Map<String, Object> requestDTOMap = ConvertUtils.getBatchAll(datasourceToBatchSchemaDTOFactory, datasourceToBatchMetaDataDTOFactory, Arrays.asList(datasourceDataRequestDTO));
        if (!requestDTOMap.containsKey("type") || !requestDTOMap.containsKey("instance")) {
            Assert.throwException(Status.DATASOURCE_CONVERT_PARAMETER_NULL, null, null);
        }
        try {
            metaDataController.batchType((BatchSchemaDTO) requestDTOMap.get("type"));
            metaDataController.batchInstances((BatchMetaDataDTO) requestDTOMap.get("instance"));
        } catch (Exception e) {
            Assert.throwException(Status.DATASOURCE_CREATE_ERROR, null, e);
        }
    }

    @Override
    public void updateDatasource(Long datasourceId, CreateOrUpdateDatasourceDataRequestDTO datasourceDataRequestDTO) {
        //query datasource by id

        //check dataSource

        //convert parameters
        Map<String, Object> requestDTOMap = ConvertUtils.getBatchAll(datasourceToBatchSchemaDTOFactory, datasourceToBatchMetaDataDTOFactory, Arrays.asList(datasourceDataRequestDTO));
        if (!requestDTOMap.containsKey("type") || !requestDTOMap.containsKey("instance")) {
            Assert.throwException(Status.DATASOURCE_CONVERT_PARAMETER_NULL, null, null);
        }
        //update datasource

    }

    @Override
    public void deleteDatasource(Long datasourceId) {
        //delete datasource

    }

    @Override
    public Object queryDatasourceById(Long datasourceId) {
        //query datasource by id

        return null;
    }

    @Override
    public List<Object> queryDatasourcePageList(DatasourceDataRequestDTO datasourceDataRequestDTO) {

        //get query conditions

        //query datasource by conditions

        return null;
    }

    @Override
    public List<Object> queryDatasourceList(DatasourceDataRequestDTO datasourceDataRequestDTO) {

        //get query conditions

        //query datasource by conditions

        return null;
    }

    @Override
    public List<String> datasourceType() {
        return null;
    }

    @Override
    public List<Object> queryDatasourceInstance(String type) {
        return null;
    }
}
