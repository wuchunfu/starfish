package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.DatasourceController;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.server.service.DatasourceService;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatasourceControllerImpl implements DatasourceController {

    private DatasourceService datasourceService;

    public DatasourceControllerImpl(DatasourceService datasourceService) {
        this.datasourceService = datasourceService;
    }

    public ResultEntity getTypes() {
        return ResultEntity.success(datasourceService.getTypes());
    }

    public ResultEntity getDefaultParameter(String type) {
        return ResultEntity.success(datasourceService.getDefaultParameter(type));
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
        return ResultEntity.success(datasourceService.testConnection(type, parameter));
    }
}
