package org.metahut.starfish.ingestion.collector.api;

public interface ICollectorTask extends AutoCloseable {

    CollectorResult execute();

}
