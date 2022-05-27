package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.CollectorAdapterCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterRequestDTO;
import org.metahut.starfish.api.dto.CollectorAdapterResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.server.collector.CollectorPluginHelper;
import org.metahut.starfish.server.service.CollectorAdapterService;
import org.metahut.starfish.server.utils.Assert;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

import static org.metahut.starfish.api.Constants.COLLECTOR_ADAPTER_TYPE_NAME;
import static org.metahut.starfish.api.enums.Status.COLLECTOR_ADAPTER_TEST_CONNECTION_FAIL;

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
        Map<String, Object> convert = conversionService.convert(requestDTO, Map.class);
        Long entityId = metaDataService.createEntityByTypeName(COLLECTOR_ADAPTER_TYPE_NAME, requestDTO.getName(), convert);
        CollectorAdapterResponseDTO collectorAdapterResponseDTO = new CollectorAdapterResponseDTO();
        collectorAdapterResponseDTO.setId(entityId);
        return collectorAdapterResponseDTO;
    }

    @Override
    public CollectorAdapterResponseDTO update(Long id, CollectorAdapterCreateOrUpdateRequestDTO requestDTO) {
        CollectorResult collectorResult = this.testConnection(requestDTO.getType(), requestDTO.getParameter());
        Assert.notTrue(collectorResult.getState(), COLLECTOR_ADAPTER_TEST_CONNECTION_FAIL, collectorResult.getMessage());
        Map<String, Object> convert = conversionService.convert(requestDTO, Map.class);
        metaDataService.updateEntity(id, requestDTO.getName(), convert);
        CollectorAdapterResponseDTO collectorAdapterResponseDTO = new CollectorAdapterResponseDTO();
        collectorAdapterResponseDTO.setId(id);
        return collectorAdapterResponseDTO;
    }

    @Override
    public void deleteById(Long id) {
        metaDataService.deleteEntity(id);
    }

    @Override
    public CollectorAdapterResponseDTO queryById(Long id) {
        return metaDataService.instance(id, CollectorAdapterResponseDTO.class);
    }

    @Override
    public PageResponseDTO<CollectorAdapterResponseDTO> queryListPage(CollectorAdapterRequestDTO requestDTO) {
        Pageable pageable = PageRequest.of(requestDTO.getPageNo() - 1, requestDTO.getPageSize());
        // TODO Assembly conditions
        Page<CollectorAdapterResponseDTO> page = metaDataService.instancesByTypeName(COLLECTOR_ADAPTER_TYPE_NAME, pageable,CollectorAdapterResponseDTO.class);
        return PageResponseDTO.of(page.getNumber(), page.getSize(), page.getTotalElements(), page.getContent());
    }

    @Override
    public Collection<CollectorAdapterResponseDTO> queryList(CollectorAdapterRequestDTO requestDTO) {
        // TODO Assembly conditions

        return metaDataService.instancesByTypeName(COLLECTOR_ADAPTER_TYPE_NAME, CollectorAdapterResponseDTO.class);
    }

}
