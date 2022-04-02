package org.metahut.starfish.store.rdbms.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.entity.NodeEntityProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NodeEntityRepositoryTest {

    @Autowired
    private NodeEntityRepository repository;

    public static final String name = "dwd.user_info";

    public static final Set<String> categories = Sets.set("HiveTable", "Hive");

    public static final String jsonFilePath = "/json/node_entity.json";


    static Stream<NodeEntity> nodeEntityProvider() {
        NodeEntity entity = new NodeEntity();
        entity.setName(name);
        entity.setCategories(categories);
        entity.setOperator(1);
        return Stream.of(entity);
    }

    @BeforeEach
    public void clear() {
        repository.deleteAll();
    }

    static Stream<NodeEntity> nodeEntityWithPropertyProvider() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        NodeEntity entity = objectMapper.readValue(NodeEntityRepositoryTest.class.getResourceAsStream(jsonFilePath), NodeEntity.class);
        System.out.println(entity);
        return Stream.of(entity);
    }

    public Set<NodeEntityProperty> initProperties() {
        NodeEntityProperty property = new NodeEntityProperty();
        return null;
    }

    @ParameterizedTest
    @MethodSource("nodeEntityProvider")
    public void saveTest(NodeEntity entity) {
        entity = repository.save(entity);
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void saveWithPropertiesTest(NodeEntity entity) {
        repository.save(entity);
    }

}
