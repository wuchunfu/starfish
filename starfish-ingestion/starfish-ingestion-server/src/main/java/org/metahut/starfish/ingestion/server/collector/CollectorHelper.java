package org.metahut.starfish.ingestion.server.collector;

import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.ICollectorManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;

@Component
public class CollectorHelper {

    private static final Map<String, ICollectorManager> collectorMap = new HashMap<>();

    @PostConstruct
    public void init() {
        ServiceLoader.load(ICollectorManager.class).forEach(manager -> {

            String type = manager.getType();

            ICollectorManager collectorManager = collectorMap.get(type);

            if (Objects.nonNull(collectorManager)) {
                throw new IllegalArgumentException(MessageFormat.format("Duplicate ingestion collector type exists: {0}", type));
            }

            collectorMap.put(type, manager);

        });
    }

    public ICollectorManager getCollector(String type) {
        return collectorMap.get(type);
    }

    public ICollector generateInstance(String type, String parameter) {
        return getCollector(type).generateInstance(parameter);
    }
}
