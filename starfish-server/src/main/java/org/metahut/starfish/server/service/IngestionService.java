package org.metahut.starfish.server.service;

import org.metahut.starfish.api.dto.IngestionCollectorCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.IngestionCollectorResponseDTO;

public interface IngestionService {

    IngestionCollectorResponseDTO createCollector(IngestionCollectorCreateOrUpdateRequestDTO ingestionCollectorCreateOrUpdateRequestDTO);
}
