package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.MetaDataController;
import org.metahut.starfish.api.dto.BatchMetaDataDTO;
import org.metahut.starfish.api.dto.TypeRequestBatchCreateOrUpdateDTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

    private static TypeRequestBatchCreateOrUpdateDTO hiveSchema;

    static Stream<TypeRequestBatchCreateOrUpdateDTO> hiveSchemaProvider() throws IOException {
        TypeRequestBatchCreateOrUpdateDTO dto = new ObjectMapper().readValue(MetaDataControllerTest.class.getResourceAsStream("/json/hiveSchema.json"), TypeRequestBatchCreateOrUpdateDTO.class);
        hiveSchema = dto;
        return Stream.of(dto);
    }

    static Stream<TypeRequestBatchCreateOrUpdateDTO> pulsarSchemaProvider() throws IOException {
        TypeRequestBatchCreateOrUpdateDTO dto = new ObjectMapper().readValue(MetaDataControllerTest.class.getResourceAsStream("/json/pulsarSchema.json"), TypeRequestBatchCreateOrUpdateDTO.class);
        return Stream.of(dto);
    }

    //static Stream<CreateOrUpdateDatasourceDataRequestDTO> datasourceDataRequestDTOProvider() throws IOException {
    //    CreateOrUpdateDatasourceDataRequestDTO dto = new ObjectMapper()
    //            .readValue(MetaDataControllerTest.class.getResourceAsStream("/json/datasourceDataRequestDTO.json"), CreateOrUpdateDatasourceDataRequestDTO.class);
    //    return Stream.of(dto);
    //}

    @ParameterizedTest
    @MethodSource("hiveSchemaProvider")
    @Commit
    public void hiveSchemaSaveTest(TypeRequestBatchCreateOrUpdateDTO dto) throws Exception {
        metaDataController.batchType(dto);
    }

    @Test
    @Commit
    public void hiveInstanceSaveTest() throws Exception {
        BatchMetaDataDTO batchInstanceBody = new BatchMetaDataDTO();
        batchInstanceBody.setSourceName("Hive");
        Map<String,String> instances = new HashMap();
        String json = "{\"tableName\":\"testTable\"}";
        instances.put("org.starfish.HiveTable",json);
        batchInstanceBody.setInstances(instances);
        metaDataController.batchInstances(batchInstanceBody);
    }

    @ParameterizedTest
    @MethodSource("pulsarSchemaProvider")
    @Commit
    public void pulsarSchemaSaveTest(TypeRequestBatchCreateOrUpdateDTO dto) throws Exception {
        metaDataController.batchType(dto);
    }

    //@ParameterizedTest
    //@MethodSource("datasourceDataRequestDTOProvider")
    //public void convertDTOTest(CreateOrUpdateDatasourceDataRequestDTO createOrUpdateDatasourceDataRequestDTO) throws Exception {
    //    ConvertUtils.getBatchAll(datasourceToBatchSchemaDTOFactory, datasourceToBatchMetaDataDTOFactory, Arrays.asList(createOrUpdateDatasourceDataRequestDTO));
    //}
}
