package org.metahut.starfish.server.converter.factory;

import jline.internal.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface DatasourceToBatchMetaDataDTOFactory {
    List<String> convert(Object source);
}
