package org.metahut.starfish.ingestion.server.service;

import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.server.dto.CollectorExecuteRequestDTO;

public interface CollectorService {

    CollectorResult execute(CollectorExecuteRequestDTO collectorExecuteRequestDTO);
}
