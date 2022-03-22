package org.metahut.starfish.ingestion.server.controller;

import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.server.collector.CollectorHelper;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CollectorController {

    private CollectorHelper collectorHelper;

    public CollectorController(CollectorHelper collectorHelper) {
        this.collectorHelper = collectorHelper;
    }

    public void test() {
        ICollector collector = collectorHelper.generateInstance("", "");
    }
}
