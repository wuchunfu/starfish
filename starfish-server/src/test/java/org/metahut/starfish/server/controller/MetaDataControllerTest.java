package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.MetaDataController;
import org.metahut.starfish.api.dto.BatchSchemaDTO;
import org.metahut.starfish.api.dto.CreateOrUpdateDatasourceDataRequestDTO;
import org.metahut.starfish.server.converter.factory.DatasourceToBatchMetaDataDTOFactory;
import org.metahut.starfish.server.converter.factory.DatasourceToBatchSchemaDTOFactory;
import org.metahut.starfish.server.utils.ConvertUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;


/**
 *
 */
@Transactional
@SpringBootTest
@Disabled
public class MetaDataControllerTest {

    @Autowired
    private MetaDataController metaDataController;

    @Autowired
    private DatasourceToBatchSchemaDTOFactory datasourceToBatchSchemaDTOFactory;

    @Autowired
    private DatasourceToBatchMetaDataDTOFactory datasourceToBatchMetaDataDTOFactory;

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

    static Stream<CreateOrUpdateDatasourceDataRequestDTO> datasourceDataRequestDTOProvider() throws IOException {
        CreateOrUpdateDatasourceDataRequestDTO dto = new ObjectMapper().readValue(MetaDataControllerTest.class.getResourceAsStream("/json/datasourceDataRequestDTO.json"), CreateOrUpdateDatasourceDataRequestDTO.class);
        return Stream.of(dto);
    }

    @ParameterizedTest
    @MethodSource("hiveSchemaProvider")
    @Commit
    public void hiveSchemaSaveTest(BatchSchemaDTO dto) throws Exception {
        metaDataController.batchType(dto);
    }

    @ParameterizedTest
    @MethodSource("pulsarSchemaProvider")
    @Commit
    public void pulsarSchemaSaveTest(BatchSchemaDTO dto) throws Exception {
        metaDataController.batchType(dto);
    }

    @ParameterizedTest
    @MethodSource("datasourceDataRequestDTOProvider")
    public void convertDTOTest(CreateOrUpdateDatasourceDataRequestDTO createOrUpdateDatasourceDataRequestDTO) throws Exception {
        ConvertUtils.getBatchAll(datasourceToBatchSchemaDTOFactory, datasourceToBatchMetaDataDTOFactory, Arrays.asList(createOrUpdateDatasourceDataRequestDTO));
    }


}
