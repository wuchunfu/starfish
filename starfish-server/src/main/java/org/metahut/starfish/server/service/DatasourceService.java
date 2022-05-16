package org.metahut.starfish.server.service;

import org.metahut.starfish.api.dto.CreateOrUpdateDatasourceDataRequestDTO;
import org.metahut.starfish.datasource.api.AbstractDatasourceParameter;
import org.metahut.starfish.datasource.api.DatasourceResult;

import java.util.Collection;

public interface DatasourceService {

    Collection<String> getTypes();

    AbstractDatasourceParameter getDefaultParameter(String type);

    DatasourceResult testConnection(String type, String parameter);

    DatasourceResult createDatasource(CreateOrUpdateDatasourceDataRequestDTO datasourceDataRequestDTO);
}
