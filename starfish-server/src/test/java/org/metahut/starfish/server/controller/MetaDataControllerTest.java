package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.MetaDataController;
import org.metahut.starfish.api.dto.BatchSchemaDTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.stream.Stream;

/**
 *
 */
@Transactional
@SpringBootTest
public class MetaDataControllerTest {

    @Autowired
    private MetaDataController metaDataController;

    private static BatchSchemaDTO hiveSchema;

    static Stream<BatchSchemaDTO> hiveSchemaProvider() throws IOException {
        BatchSchemaDTO dto = new ObjectMapper().readValue(MetaDataControllerTest.class.getResourceAsStream("/json/hiveSchema.json"), BatchSchemaDTO.class);
        hiveSchema = dto;
        return Stream.of(dto);
    }

    static Stream<BatchSchemaDTO> pulsarSchemaProvider() throws IOException {
        BatchSchemaDTO dto = new ObjectMapper().readValue(MetaDataControllerTest.class.getResourceAsStream("/json/pulsarSchema.json"), BatchSchemaDTO.class);
        return Stream.of(dto);
    }

    @ParameterizedTest
    @MethodSource("hiveSchemaProvider")
    public void hiveSchemaSaveTest(BatchSchemaDTO dto) throws Exception {
        metaDataController.batchType(dto);
    }

    @ParameterizedTest
    @MethodSource("pulsarSchemaProvider")
    public void pulsarSchemaSaveTest(BatchSchemaDTO dto) throws Exception {
        metaDataController.batchType(dto);
    }
}
