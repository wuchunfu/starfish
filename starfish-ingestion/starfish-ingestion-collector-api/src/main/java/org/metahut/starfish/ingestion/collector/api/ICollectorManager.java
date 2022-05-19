package org.metahut.starfish.ingestion.collector.api;

public interface ICollectorManager {

    String getType();

    ICollectorTask generateTaskInstance(String adapterParameter, String parameter);

    ICollectorAdapter generateAdapterInstance(String parameter);
}
