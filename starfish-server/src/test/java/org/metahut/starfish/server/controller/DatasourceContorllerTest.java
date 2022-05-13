package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.DatasourceController;
import org.metahut.starfish.api.dto.CreateOrUpdateDatasourceDataRequestDTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.stream.Stream;

@SpringBootTest
@Disabled
public class DatasourceContorllerTest {
    @Autowired
    private DatasourceController datasourceController;

    static Stream<CreateOrUpdateDatasourceDataRequestDTO> datasourceDataRequestDTOProvider() throws IOException {
        CreateOrUpdateDatasourceDataRequestDTO dto = new ObjectMapper().readValue(
                MetaDataControllerTest.class.getResourceAsStream("/json/datasourceDataRequestDTO.json")
                , CreateOrUpdateDatasourceDataRequestDTO.class);
        return Stream.of(dto);
    }

    @ParameterizedTest
    @MethodSource("datasourceDataRequestDTOProvider")
    public void createDatasourceTest(CreateOrUpdateDatasourceDataRequestDTO createOrUpdateDatasourceDataRequestDTO) throws Exception {
        datasourceController.createDatasource(createOrUpdateDatasourceDataRequestDTO);
    }

}
