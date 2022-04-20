package org.metahut.starfish.server.datasource;

import org.metahut.starfish.datasource.api.IDatasource;
import org.metahut.starfish.datasource.api.IDatasourceManager;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;

@Component
public class DatasourcePluginHelper {

    private static final Map<String, IDatasourceManager> datasourceMap = new HashMap<>();

    @PostConstruct
    public void init() {
        ServiceLoader.load(IDatasourceManager.class).forEach(manager -> {

            String type = manager.getType();
            IDatasourceManager datasourceManager = datasourceMap.get(type);

            if (Objects.nonNull(datasourceManager)) {
                throw new IllegalArgumentException(MessageFormat.format("Duplicate datasource type exists: {0}", type));
            }
            datasourceMap.put(type, manager);
        });
    }

    private IDatasourceManager getDatasourceManager(String type) {
        return datasourceMap.get(type);
    }

    public IDatasource generateInstance(String type, String parameter) {
        return getDatasourceManager(type).generateInstance(parameter);
    }
}
