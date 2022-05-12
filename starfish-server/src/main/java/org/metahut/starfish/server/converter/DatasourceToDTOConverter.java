package org.metahut.starfish.server.converter;

import org.metahut.starfish.api.dto.CreateOrUpdateDatasourceDataRequestDTO;
import org.metahut.starfish.api.dto.DatasourceDataResponseDTO;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface DatasourceToDTOConverter extends Converter<CreateOrUpdateDatasourceDataRequestDTO, DatasourceDataResponseDTO> {

    // TODO Object needs to be modified to a specific entity class
    @Override
    DatasourceDataResponseDTO convert(CreateOrUpdateDatasourceDataRequestDTO source);
}
