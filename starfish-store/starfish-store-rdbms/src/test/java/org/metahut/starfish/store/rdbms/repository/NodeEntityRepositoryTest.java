package org.metahut.starfish.store.rdbms.repository;

import org.metahut.starfish.store.model.AbstractNodeEntity;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
public class NodeEntityRepositoryTest {

    @Autowired
    private NodeEntityRepository repository;

    public static final String jsonFilePath = "/json/node_entity.json";

    public static final ObjectMapper objectMapper = new ObjectMapper();

    static Stream<NodeEntity> nodeEntityWithPropertyProvider() throws IOException {
        NodeEntity entity = objectMapper.readValue(NodeEntityRepositoryTest.class.getResourceAsStream(jsonFilePath), NodeEntity.class);
        return Stream.of(entity);
    }

    @BeforeEach
    public void clearEach() {
        repository.deleteAll();
    }

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void saveWithPropertiesTest(NodeEntity entity) {
        NodeEntity savedEntity = repository.save(entity);
        Assertions.assertNotNull(savedEntity.getId());
        Assertions.assertNotNull(savedEntity.getCreateTime());
        Assertions.assertNotNull(savedEntity.getUpdateTime());
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void findWithPropertiesTest(NodeEntity entity) throws JsonProcessingException {
        NodeEntity expected = repository.save(entity);

        AbstractNodeEntity actual = repository.findById(expected.getId()).get();

        JsonNode expectedJson = objectMapper.readTree(objectMapper.writeValueAsString(expected));

        JsonNode actualJson = objectMapper.readTree(objectMapper.writeValueAsString(actual));

        Assertions.assertEquals(expectedJson, actualJson);
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void updateTest(NodeEntity entity) {
        NodeEntity saveEntity = repository.save(entity);
        String alteredName = "dwd.user_info_test";
        saveEntity.setName(alteredName);

        NodeEntity expected = repository.save(saveEntity);

        Assertions.assertEquals(expected.getName(), alteredName);

    }

}
