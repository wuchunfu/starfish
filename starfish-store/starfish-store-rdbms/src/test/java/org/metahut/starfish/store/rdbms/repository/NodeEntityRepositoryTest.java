package org.metahut.starfish.store.rdbms.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void clear() {
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

        NodeEntity actual = repository.findById(expected.getId()).get();

        JsonNode expectedJson = objectMapper.readTree(objectMapper.writeValueAsString(expected));

        JsonNode actualJson = objectMapper.readTree(objectMapper.writeValueAsString(actual));

        Assertions.assertEquals(expectedJson, actualJson);

    }

}
