package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.CollectorAdapterCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterResponseDTO;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.server.collector.CollectorPluginHelper;
import org.metahut.starfish.server.service.CollectorAdapterService;
import org.metahut.starfish.server.utils.Assert;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.metahut.starfish.api.enums.Status.COLLECTOR_ADAPTER_TEST_CONNECTION_FAIL;
import static org.metahut.starfish.server.utils.Constants.COLLECTOR_ADAPTER_TYPE_NAME;

@Service
public class CollectorAdapterServiceImpl implements CollectorAdapterService {

    private final CollectorPluginHelper collectorPluginHelper;
    private final ConversionService conversionService;
    private final AbstractMetaDataService<Long, Object> metaDataService;

    public CollectorAdapterServiceImpl(CollectorPluginHelper collectorPluginHelper, ConversionService conversionService, AbstractMetaDataService<Long, Object> metaDataService) {
        this.collectorPluginHelper = collectorPluginHelper;
        this.conversionService = conversionService;
        this.metaDataService = metaDataService;
    }

    @Override
    public CollectorResult testConnection(String type, String parameter) {
        return collectorPluginHelper.testAdapterConnection(type, parameter);
    }

    @Override
    public CollectorAdapterResponseDTO create(CollectorAdapterCreateOrUpdateRequestDTO requestDTO) {
        CollectorResult collectorResult = this.testConnection(requestDTO.getType(), requestDTO.getParameter());
        Assert.notTrue(collectorResult.getState(), COLLECTOR_ADAPTER_TEST_CONNECTION_FAIL, collectorResult.getMessage());

        // TODO ???
        Long entityId = metaDataService.createEntityByTypeName(COLLECTOR_ADAPTER_TYPE_NAME, requestDTO.getName(), null);
        CollectorAdapterResponseDTO collectorAdapterResponseDTO = new CollectorAdapterResponseDTO();
        collectorAdapterResponseDTO.setId(entityId);
        return collectorAdapterResponseDTO;
    }

    @Override
    public CollectorAdapterResponseDTO update(Long id, CollectorAdapterCreateOrUpdateRequestDTO requestDTO) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public CollectorAdapterResponseDTO queryById(Long id) {
        return null;
    }

    @Override
    public List<CollectorAdapterResponseDTO> queryListPage(CollectorAdapterRequestDTO requestDTO) {
        return null;
    }

    @Override
    public List<CollectorAdapterResponseDTO> queryList(CollectorAdapterRequestDTO requestDTO) {
        return null;
    }

}
