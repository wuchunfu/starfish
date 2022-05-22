package org.metahut.starfish.server.service;

import org.metahut.starfish.api.dto.TypeRequestBatchCreateOrUpdateDTO;
import org.metahut.starfish.api.dto.TypeResponseDTO;

public interface TypeService {

    TypeResponseDTO initTypes(TypeRequestBatchCreateOrUpdateDTO requestDTO);
}
