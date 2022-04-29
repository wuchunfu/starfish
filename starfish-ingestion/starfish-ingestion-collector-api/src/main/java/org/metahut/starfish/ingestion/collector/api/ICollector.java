package org.metahut.starfish.ingestion.collector.api;

import org.metahut.starfish.api.dto.BatchMetaDataDTO;

import java.util.List;

public interface ICollector extends AutoCloseable {

    CollectorResult execute();

    List<BatchMetaDataDTO> getMsg();

}
