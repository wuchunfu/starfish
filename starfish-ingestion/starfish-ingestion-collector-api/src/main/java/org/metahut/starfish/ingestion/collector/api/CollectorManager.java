package org.metahut.starfish.ingestion.collector.api;

public interface CollectorManager {

    String getType();

    Collector generateInstance(String parameter);
}
