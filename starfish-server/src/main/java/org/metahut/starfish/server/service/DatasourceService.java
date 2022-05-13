package org.metahut.starfish.server.service;

import org.metahut.starfish.api.dto.CreateOrUpdateDatasourceDataRequestDTO;
import org.metahut.starfish.api.dto.DatasourceDataRequestDTO;
import org.metahut.starfish.datasource.api.AbstractDatasourceParameter;

import java.util.Collection;
import java.util.List;

public interface DatasourceService {

    Collection<String> getTypes();

    AbstractDatasourceParameter getDefaultParameter(String type);

    boolean testConnection(String type, String parameter);

    void createDatasource(CreateOrUpdateDatasourceDataRequestDTO datasourceDataRequestDTO);

    void updateDatasource(Long datasourceId, CreateOrUpdateDatasourceDataRequestDTO datasourceDataRequestDTO);

    void deleteDatasource(Long datasourceId);

    Object queryDatasourceById(Long datasourceId);

    List<Object> queryDatasourcePageList(DatasourceDataRequestDTO datasourceDataRequestDTO);

    List<Object> queryDatasourceList(DatasourceDataRequestDTO datasourceDataRequestDTO);

    List<String> datasourceType();

    List<Object> queryDatasourceInstance(String type);
}
