package org.metahut.starfish.store.rdbms.repository;

import org.metahut.starfish.store.model.AbstractNodeEntity;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Commit
@Transactional
@SpringBootTest
public class NodeEntityRepositoryTest {

    @Autowired
    private NodeEntityRepository repository;

    public static final String jsonFilePath = "/json/node_entity.json";

    public static final ObjectMapper objectMapper = new ObjectMapper();

    static Stream<NodeEntity> nodeEntityWithPropertyProvider() throws IOException {
        NodeEntity entity = objectMapper.readValue(NodeEntityRepositoryTest.class.getResourceAsStream(jsonFilePath), NodeEntity.class);
        entity.getProperties().stream().forEach(property -> {
            property.setEntity(entity);
        });
        return Stream.of(entity);
    }

    @BeforeEach
    public void clearEach() {
        repository.deleteAll();
    }

    // @AfterEach
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
        savedEntity.getProperties().stream().forEach(property -> Assertions.assertNotNull(property.getValue()));
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void findWithPropertiesTest(NodeEntity entity) {
        NodeEntity expected = repository.save(entity);

        AbstractNodeEntity actual = repository.findById(expected.getId()).get();

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void updateTest(NodeEntity entity) {
        NodeEntity savedEntity = repository.save(entity);

        String alteredName = "dwd.user_info_test";
        savedEntity.setName(alteredName);

        NodeEntity expected = repository.save(savedEntity);

        Assertions.assertEquals(expected.getName(), alteredName);
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void removeByNameTest(NodeEntity entity) {
        NodeEntity savedEntity = repository.save(entity);

        List<NodeEntity> list = repository.removeByName(entity.getName());

        Assertions.assertEquals(1L, list.size());
    }
}
