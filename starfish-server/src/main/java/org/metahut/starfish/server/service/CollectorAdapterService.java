package org.metahut.starfish.server.service;

import org.metahut.starfish.api.dto.CollectorAdapterCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterResponseDTO;

public interface CollectorAdapterService {
    CollectorAdapterResponseDTO create(CollectorAdapterCreateOrUpdateRequestDTO requestDTO);
}
