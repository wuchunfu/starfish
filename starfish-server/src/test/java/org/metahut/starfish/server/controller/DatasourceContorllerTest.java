package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.DatasourceController;
import org.metahut.starfish.api.controller.TypeController;
import org.metahut.starfish.api.dto.CreateOrUpdateDatasourceDataRequestDTO;
import org.metahut.starfish.api.dto.CreateTypeRequestDTO;
import org.metahut.starfish.api.dto.CreateTypeResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.io.IOException;
import java.util.stream.Stream;

@SpringBootTest
@Disabled
public class DatasourceContorllerTest {
    @Autowired
    private DatasourceController datasourceController;
    @Autowired
    private TypeController typeController;

    static Stream<CreateOrUpdateDatasourceDataRequestDTO> datasourceDataRequestDTOProvider() throws IOException {
        CreateOrUpdateDatasourceDataRequestDTO dto = new ObjectMapper().readValue(
                DatasourceContorllerTest.class.getResourceAsStream("/json/datasourceDataRequestDTO.json")
                , CreateOrUpdateDatasourceDataRequestDTO.class);
        return Stream.of(dto);
    }

    static Stream<CreateTypeRequestDTO> createTypeRequestDTO() throws IOException {
        CreateTypeRequestDTO dto = new ObjectMapper().readValue(
                DatasourceContorllerTest.class.getResourceAsStream("/json/createTypeRequestDTO.json")
                , CreateTypeRequestDTO.class);
        return Stream.of(dto);
    }

    @ParameterizedTest
    @MethodSource("datasourceDataRequestDTOProvider")
    public void createDatasourceTest(CreateOrUpdateDatasourceDataRequestDTO createOrUpdateDatasourceDataRequestDTO) throws Exception {
        datasourceController.createDatasource(createOrUpdateDatasourceDataRequestDTO);
    }

    @ParameterizedTest
    @MethodSource("createTypeRequestDTO")
    @Commit
    public void createType(CreateTypeRequestDTO createTypeRequestDTO) throws Exception {
        ResultEntity<CreateTypeResponseDTO> type = typeController.createType(createTypeRequestDTO);
        Assertions.assertNotNull(type.getData().getSourceId());
    }

}
