package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.DatasourceController;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.server.datasource.DatasourcePluginHelper;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatasourceControllerImpl implements DatasourceController {

    private DatasourcePluginHelper datasourcePluginHelper;

    public DatasourceControllerImpl(DatasourcePluginHelper datasourcePluginHelper) {
        this.datasourcePluginHelper = datasourcePluginHelper;
    }

    // insert

    // update

    // delete

    // query

    /**
     * test connection
     * @param type
     * @param parameter
     * @return
     */
    @Override
    public ResultEntity testConnection(String type, String parameter) {
        return ResultEntity.success(datasourcePluginHelper.generateInstance(type, parameter).testConnection());
    }
}
