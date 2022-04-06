package org.metahut.starfish.store.rdbms.dao;

import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.repository.NodeEntityRepositoryTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.stream.Stream;

@SpringBootTest
public class NodeEntityMapperTest {

    @Autowired
    private NodeEntityMapper mapper;

    public static final String jsonFilePath = "/json/node_entity.json";

    public static final ObjectMapper objectMapper = new ObjectMapper();

    static Stream<NodeEntity> nodeEntityWithPropertyProvider() throws IOException {
        NodeEntity entity = objectMapper.readValue(NodeEntityRepositoryTest.class.getResourceAsStream(jsonFilePath), NodeEntity.class);
        return Stream.of(entity);
    }

    @BeforeEach
    public void clearEach() {
        mapper.removeAll();
    }

    @AfterEach
    public void cleanUp() {
        // mapper.removeAll();
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void saveTest(NodeEntity entity) {
        NodeEntity actual = mapper.create(entity);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertNotNull(actual.getCreateTime());
        Assertions.assertNotNull(actual.getUpdateTime());
    }
}
