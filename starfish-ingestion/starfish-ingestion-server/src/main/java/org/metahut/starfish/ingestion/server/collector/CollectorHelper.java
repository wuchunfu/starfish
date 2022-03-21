package org.metahut.starfish.ingestion.server.collector;

import org.metahut.starfish.ingestion.collector.api.CollectorManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;

@Component
public class CollectorHelper {

    private static final Map<String, CollectorManager> collectorMap = new HashMap<>();

    @PostConstruct
    public void init() {
        ServiceLoader.load(CollectorManager.class).forEach(manager -> {

            String type = manager.getType();

            CollectorManager collectorManager = collectorMap.get(type);

            if (Objects.nonNull(collectorManager)) {
                throw new IllegalArgumentException(MessageFormat.format("Duplicate ingestion collector type exists: {0}", type));
            }

            collectorMap.put(type, manager);

        });
    }

    public CollectorManager getCollector(String type) {
        return collectorMap.get(type);
    }
}
