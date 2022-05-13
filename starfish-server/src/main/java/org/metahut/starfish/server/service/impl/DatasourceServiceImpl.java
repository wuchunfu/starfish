package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.CreateOrUpdateDatasourceDataRequestDTO;
import org.metahut.starfish.api.dto.DatasourceDataRequestDTO;
import org.metahut.starfish.api.enums.Status;
import org.metahut.starfish.datasource.api.AbstractDatasourceParameter;
import org.metahut.starfish.datasource.api.DatasourceResult;
import org.metahut.starfish.parser.exception.InstanceNameNullException;
import org.metahut.starfish.server.converter.factory.DatasourceToBatchMetaDataDTOFactory;
import org.metahut.starfish.server.converter.factory.DatasourceToBatchSchemaDTOFactory;
import org.metahut.starfish.server.datasource.DatasourcePluginHelper;
import org.metahut.starfish.server.service.DatasourceService;
import org.metahut.starfish.server.utils.Assert;
import org.metahut.starfish.server.utils.ConvertUtils;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class DatasourceServiceImpl implements DatasourceService {

    private final DatasourceToBatchSchemaDTOFactory datasourceToBatchSchemaDTOFactory;

    private final DatasourceToBatchMetaDataDTOFactory datasourceToBatchMetaDataDTOFactory;

    private final DatasourcePluginHelper datasourcePluginHelper;

    private final AbstractMetaDataService<Long,Object> abstractMetaDataService;

    private final ConversionService conversionService;

    public DatasourceServiceImpl(DatasourcePluginHelper datasourcePluginHelper
            , DatasourceToBatchMetaDataDTOFactory datasourceToBatchMetaDataDTOFactory
            , DatasourceToBatchSchemaDTOFactory datasourceToBatchSchemaDTOFactory
            , AbstractMetaDataService<Long,Object> abstractMetaDataService
            , ConversionService conversionService) {
        this.datasourcePluginHelper = datasourcePluginHelper;
        this.datasourceToBatchMetaDataDTOFactory = datasourceToBatchMetaDataDTOFactory;
        this.datasourceToBatchSchemaDTOFactory = datasourceToBatchSchemaDTOFactory;
        this.abstractMetaDataService = abstractMetaDataService;
        this.conversionService = conversionService;
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
    public Long createDatasource(CreateOrUpdateDatasourceDataRequestDTO datasourceDataRequestDTO) {
        testConnection(datasourceDataRequestDTO.getType(), datasourceDataRequestDTO.getParamter());
        try {
            Map properties = BeanUtils.describe(datasourceDataRequestDTO);
            return abstractMetaDataService.createEntity(datasourceDataRequestDTO.getTypeId(), properties);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            throw new InstanceNameNullException(exception);
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
