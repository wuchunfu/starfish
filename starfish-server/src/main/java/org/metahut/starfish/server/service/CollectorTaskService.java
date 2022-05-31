package org.metahut.starfish.server.service;

import org.metahut.starfish.api.dto.CollectorTaskCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceLogResponseDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceResponseDTO;
import org.metahut.starfish.api.dto.CollectorTaskRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;

import java.util.Collection;

public interface CollectorTaskService {
    CollectorTaskResponseDTO create(CollectorTaskCreateOrUpdateRequestDTO requestDTO);

    CollectorTaskResponseDTO update(Long id, CollectorTaskCreateOrUpdateRequestDTO requestDTO);

    void deleteById(Long id);

    CollectorTaskResponseDTO queryById(Long id);

    PageResponseDTO<CollectorTaskResponseDTO> queryListPage(CollectorTaskRequestDTO requestDTO);

    Collection<CollectorTaskResponseDTO> queryList(CollectorTaskRequestDTO requestDTO);

    PageResponseDTO<CollectorTaskInstanceResponseDTO> queryInstanceListPage(CollectorTaskInstanceRequestDTO requestDTO);

    CollectorTaskInstanceLogResponseDTO queryInstanceLog(Integer instanceId, Integer offset, Integer limit);
}
