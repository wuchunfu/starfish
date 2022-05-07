package org.metahut.starfish.server.converter;

import org.metahut.starfish.api.dto.IngestionCollectorResponseDTO;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CollectorInstanceToDTOConverter extends Converter<Object, IngestionCollectorResponseDTO> {

    // TODO Object needs to be modified to a specific entity class
    @Override
    IngestionCollectorResponseDTO convert(Object source);
}
