package org.metahut.starfish.ingestion.collector.api;

public interface ICollectorManager {

    String getType();

    ICollector generateInstance(AbstractCollectorParameter parameter);
}
