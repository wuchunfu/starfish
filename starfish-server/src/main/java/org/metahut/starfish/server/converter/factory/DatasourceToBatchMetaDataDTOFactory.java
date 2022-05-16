package org.metahut.starfish.server.converter.factory;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DatasourceToBatchMetaDataDTOFactory {
    List<String> convert(Object source);
}
