package org.metahut.starfish.ingestion.collector.api;

public interface ICollector extends AutoCloseable {

    CollectorResult execute();
}
