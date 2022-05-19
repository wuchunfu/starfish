package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.IngestionCollectorExecuteLogResponseDTO;
import org.metahut.starfish.api.dto.IngestionCollectorLogRequestDTO;
import org.metahut.starfish.api.dto.IngestionCollectorLogResponseDTO;
import org.metahut.starfish.api.dto.IngestionCollectorCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.IngestionCollectorResponseDTO;
import org.metahut.starfish.api.dto.QueryIngestionCollectorRequestDTO;
import org.metahut.starfish.api.dto.ResultEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "INGESTION_TAG")
@RequestMapping("ingestion")
public interface IngestionController {

    // collectorAdapter


    // collectorTask

    @GetMapping("collectorAdapter/testConnection")
    @ApiOperation(value = "testConnection", notes = "TEST_CONNECTION_COLLECTOR_NOTES")
    ResultEntity testConnection(@RequestParam String type, @RequestParam String parameter);

    @ApiOperation(value = "createCollector", notes = "CREATE_INGESTION_COLLECTOR_INSTANCE_NOTES")
    @PostMapping("createCollector")
    ResultEntity<IngestionCollectorResponseDTO> createCollector(@RequestBody IngestionCollectorCreateOrUpdateRequestDTO ingestionCollectorCreateOrUpdateRequestDTO);

    //update
    @ApiOperation(value = "updateCollector", notes = "UPDATE_INGESTION_COLLECTOR_INSTANCE_NOTES")
    @PutMapping("/{code}")
    ResultEntity<IngestionCollectorResponseDTO> updateCollector(@PathVariable(value = "code", required = true) Long collectorId
            , @RequestBody IngestionCollectorCreateOrUpdateRequestDTO ingestionCollectorCreateOrUpdateRequestDTO);

    //delete
    @ApiOperation(value = "deleteCollector", notes = "DELETE_INGESTION_COLLECTOR_INSTANCE_NOTES")
    @DeleteMapping("/{code}")
    ResultEntity deleteCollector(@PathVariable(value = "code") Long collectorId);

    //batchdelete
    @ApiOperation(value = "batchDeleteCollector", notes = "BATCH_DELETE_INGESTION_COLLECTOR_INSTANCE_NOTES")
    @DeleteMapping("batchDelete")
    ResultEntity batchDeleteCollector(List<Long> collectorIdList);

    //page query task config
    @ApiOperation(value = "queryCollectorPage", notes = "QUERY_COLLECTOR_INGESTION_COLLECTOR_PAGE_INSTANCE_NOTES")
    @GetMapping("queryCollectorPage")
    ResultEntity<IngestionCollectorResponseDTO> queryCollectorPage(@RequestBody QueryIngestionCollectorRequestDTO queryIngestionCollectorRequestDTO);

    @ApiOperation(value = "queryCollector", notes = "QUERY_COLLECTOR_INGESTION_COLLECTOR_INSTANCE_NOTES")
    @GetMapping("queryCollector")
    ResultEntity<IngestionCollectorResponseDTO> queryCollectorList(@RequestBody QueryIngestionCollectorRequestDTO queryIngestionCollectorRequestDTO);

    //page query task log
    @ApiOperation(value = "queryCollectorLogPage", notes = "QUERY_COLLECTOR_INGESTION_LOG_PAGE_COLLECTOR_INSTANCE_NOTES")
    @GetMapping("queryCollectorLogPage")
    ResultEntity<IngestionCollectorLogResponseDTO> queryCollectorLogPage(@RequestBody IngestionCollectorLogRequestDTO ingestionCollectorLogRequestDTO);

    //page query task log
    @ApiOperation(value = "queryCollectorLog", notes = "QUERY_COLLECTOR_INGESTION_LOG_COLLECTOR_INSTANCE_NOTES")
    @GetMapping("queryCollectorLog")
    ResultEntity<IngestionCollectorLogResponseDTO> queryCollectorLogList(@RequestBody IngestionCollectorLogRequestDTO ingestionCollectorLogRequestDTO);

    //query task execute log
    @ApiOperation(value = "queryCollectorExecuteLog", notes = "QUERY_COLLECTOR_INGESTION_EXECUTE_LOG_COLLECTOR_INSTANCE_NOTES")
    @GetMapping("queryCollectorExecuteLog")
    ResultEntity<IngestionCollectorExecuteLogResponseDTO> queryCollectorExecuteLog(Long taskId);
}
