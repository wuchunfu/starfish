package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.*;
import org.metahut.starfish.server.service.CollectorAdapterService;

import org.metahut.starfish.service.AbstractMetaDataService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static org.metahut.starfish.server.utils.Constants.COLLECTOR_ADAPTER_TYPE_NAME;

@Service
public class CollectorAdapterServiceImpl implements CollectorAdapterService {

    private final ConversionService conversionService;
    private final AbstractMetaDataService<Long, Object> metaDataService;

    public CollectorAdapterServiceImpl(ConversionService conversionService, AbstractMetaDataService metaDataService) {
        this.conversionService = conversionService;
        this.metaDataService = metaDataService;
    }

    @Override
    public CollectorAdapterResponseDTO create(CollectorAdapterCreateOrUpdateRequestDTO requestDTO) {
        NodePropertiesRequestDTO convert = conversionService.convert(requestDTO, NodePropertiesRequestDTO.class);
        Long entityId = metaDataService.createEntityByTypeName(COLLECTOR_ADAPTER_TYPE_NAME, requestDTO.getName(), convert.getProperties());
        CollectorAdapterResponseDTO collectorAdapterResponseDTO = new CollectorAdapterResponseDTO();
        collectorAdapterResponseDTO.setId(entityId);
        return collectorAdapterResponseDTO;
    }

}
