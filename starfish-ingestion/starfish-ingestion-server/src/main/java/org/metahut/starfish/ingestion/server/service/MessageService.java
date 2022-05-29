package org.metahut.starfish.ingestion.server.service;

import org.springframework.scheduling.annotation.Async;

public interface MessageService {
    @Async("taskExecutor")
    void toMetaEventConsumer();
}
