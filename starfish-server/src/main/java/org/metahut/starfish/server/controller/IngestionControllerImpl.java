package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.IngestionController;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.server.collector.CollectorHelper;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngestionControllerImpl implements IngestionController {

    private CollectorHelper collectorHelper;

    public IngestionControllerImpl(CollectorHelper collectorHelper) {
        this.collectorHelper = collectorHelper;
    }

    /**
     * test connection
     * @param type
     * @param parameter
     * @return
     */
    @Override
    public ResultEntity testConnection(String type, String parameter) {
        return ResultEntity.success(collectorHelper.generateInstance(type, parameter).testConnection());
    }
}
