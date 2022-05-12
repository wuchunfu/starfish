package org.metahut.starfish.server.converter.factory;

import org.metahut.starfish.api.dto.BatchSchemaDTO;

import org.springframework.stereotype.Component;

@Component
public interface DatasourceToBatchSchemaDTOFactory {

    BatchSchemaDTO.ClassDTO convert(Object source);
}
