package org.metahut.starfish.server.service;

import org.metahut.starfish.api.dto.CollectorTaskCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskResponseDTO;

public interface CollectorTaskService {
    CollectorTaskResponseDTO create(CollectorTaskCreateOrUpdateRequestDTO requestDTO);
}
