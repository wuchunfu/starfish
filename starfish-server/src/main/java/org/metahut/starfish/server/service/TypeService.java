package org.metahut.starfish.server.service;

import org.metahut.starfish.api.dto.TypeRequestCreateOrUpdateDTO;
import org.metahut.starfish.api.dto.TypeResponseDTO;

public interface TypeService {

    TypeResponseDTO createType(TypeRequestCreateOrUpdateDTO typeRequestCreateOrUpdateDTO);
}
