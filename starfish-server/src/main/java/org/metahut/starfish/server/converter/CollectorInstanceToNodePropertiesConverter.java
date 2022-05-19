package org.metahut.starfish.server.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.metahut.starfish.api.dto.IngestionCollectorCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.NodePropertiesRequestDTO;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CollectorInstanceToNodePropertiesConverter extends Converter<IngestionCollectorCreateOrUpdateRequestDTO, NodePropertiesRequestDTO> {

    // TODO expression???
    @Override
    @Mapping(target = "properties", expression = "java(typeConversionWorker.generateRelatedField(source))")
    NodePropertiesRequestDTO convert(IngestionCollectorCreateOrUpdateRequestDTO source);
}
