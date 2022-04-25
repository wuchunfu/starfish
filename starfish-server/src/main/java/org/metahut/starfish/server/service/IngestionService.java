package org.metahut.starfish.server.service;

import org.metahut.starfish.api.dto.IngestionCollectorRequestDTO;

public interface IngestionService {

    String createCollector(IngestionCollectorRequestDTO ingestionCollectorRequestDTO);
}
