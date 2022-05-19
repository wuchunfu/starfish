package org.metahut.starfish.ingestion.collector.api;

public interface ICollectorAdapter extends AutoCloseable {

    CollectorResult testConnection();

    <T> T getMetaClient();

}
