package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.datasource.api.AbstractDatasourceParameter;
import org.metahut.starfish.datasource.api.DatasourceResult;
import org.metahut.starfish.server.datasource.DatasourcePluginHelper;
import org.metahut.starfish.server.service.DatasourceService;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DatasourceServiceImpl implements DatasourceService {

    private DatasourcePluginHelper datasourcePluginHelper;

    public DatasourceServiceImpl(DatasourcePluginHelper datasourcePluginHelper) {
        this.datasourcePluginHelper = datasourcePluginHelper;
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
}
