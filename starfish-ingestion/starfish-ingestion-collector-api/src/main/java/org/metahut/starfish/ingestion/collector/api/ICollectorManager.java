package org.metahut.starfish.ingestion.collector.api;

public interface ICollectorManager {

    String getType();

    ICollector generateInstance(String parameter);
}
