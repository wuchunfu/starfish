package org.metahut.starfish.ingestion.collector.api;

import org.metahut.starfish.api.dto.BatchMetaDataDTO;
import org.metahut.starfish.api.dto.BatchSchemaDTO;

import java.util.List;

public interface ICollector extends AutoCloseable {

    CollectorResult execute();

    BatchMetaDataDTO getMsg();

    BatchSchemaDTO getClassInfo();

}
