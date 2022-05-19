package org.metahut.starfish.ingestion.collector.api;

public interface ICollectorManager {

    String getType();

    ICollectorTask generateTaskInstance(String adapterParameter, String parameter);

    AbstractCollectorParameter deserializeAdapterParameter(String parameter);

    ICollectorAdapter generateAdapterInstance(String parameter);
}
