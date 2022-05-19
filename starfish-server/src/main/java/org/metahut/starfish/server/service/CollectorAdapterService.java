package org.metahut.starfish.server.service;

import org.metahut.starfish.api.dto.CollectorAdapterCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterResponseDTO;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;

import java.util.List;

public interface CollectorAdapterService {
    CollectorResult testConnection(String type, String parameter);

    CollectorAdapterResponseDTO create(CollectorAdapterCreateOrUpdateRequestDTO requestDTO);

    CollectorAdapterResponseDTO update(Long id, CollectorAdapterCreateOrUpdateRequestDTO requestDTO);

    void deleteById(Long id);

    CollectorAdapterResponseDTO queryById(Long id);

    List<CollectorAdapterResponseDTO> queryListPage(CollectorAdapterRequestDTO requestDTO);

    List<CollectorAdapterResponseDTO> queryList(CollectorAdapterRequestDTO requestDTO);
}
