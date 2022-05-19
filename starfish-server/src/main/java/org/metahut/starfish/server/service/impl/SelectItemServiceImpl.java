package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.SelectItemRequestDTO;
import org.metahut.starfish.api.dto.SelectItemResponseDTO;
import org.metahut.starfish.api.enums.SelectItemNameEnum;
import org.metahut.starfish.server.collector.CollectorPluginHelper;
import org.metahut.starfish.server.service.SelectItemService;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SelectItemServiceImpl implements SelectItemService {

    private static final Logger logger = LoggerFactory.getLogger(SelectItemServiceImpl.class);

    private final MessageSource messageSource;
    private final CollectorPluginHelper collectorPluginHelper;

    public SelectItemServiceImpl(MessageSource messageSource, CollectorPluginHelper collectorPluginHelper) {
        this.messageSource = messageSource;
        this.collectorPluginHelper = collectorPluginHelper;
    }

    @Override
    public Map<String, List<SelectItemResponseDTO>> queryList(SelectItemRequestDTO selectItemRequestDTO) {
        Set<SelectItemNameEnum> componentNames = Objects.isNull(selectItemRequestDTO) || CollectionUtils.isEmpty(selectItemRequestDTO.getComponentNames())
                ? Arrays.stream(SelectItemNameEnum.values()).collect(Collectors.toSet())
                : selectItemRequestDTO.getComponentNames();
        return componentNames
                .stream()
                .collect(Collectors.toMap(SelectItemNameEnum::name, nameEnum -> generateSelectItem(nameEnum)));
    }

    private List<SelectItemResponseDTO> generateSelectItem(SelectItemNameEnum nameEnum) {
        switch (nameEnum) {
            case COLLECTOR_TYPE:
                return queryCollectorTypeItem();

            default:
                return Collections.EMPTY_LIST;
        }
    }

    public List<SelectItemResponseDTO> queryCollectorTypeItem() {
       return collectorPluginHelper.getAllTypes().stream()
                .map(value -> toSelectItem(value, value)).collect(Collectors.toList());
    }
    private List<SelectItemResponseDTO> queryMetricsDimensionItem() {
        return null;
    }

    private List<SelectItemResponseDTO> enumsToSelectItems(Enum[] enums) {
        return Arrays.stream(enums)
                .map(value -> toSelectItem(value, value.name())).collect(Collectors.toList());
    }

    private SelectItemResponseDTO toSelectItem(Object value, String message) {
        try {
            message = messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
        } catch (Throwable throwable) {
            logger.error("select item value to i18n exception", throwable);
        }
        return SelectItemResponseDTO.of(value,  message);
    }

}
