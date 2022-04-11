package org.metahut.starfish.store.rdbms.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.metahut.starfish.store.dao.INodeEntityMapper;
import org.metahut.starfish.store.model.AbstractEntityProperty;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.entity.NodeEntityProperty;
import org.metahut.starfish.store.rdbms.repository.NodeEntityRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@Commit
@Transactional
@SpringBootTest
public class NodeEntityPropertyMapperTest {

    @Autowired
    private NodeEntityPropertyMapper mapper;

    @Autowired
    private INodeEntityMapper nodeEntityMapper;

    public static final String jsonFilePath = "/json/node_entity.json";

    public static final ObjectMapper objectMapper = new ObjectMapper();

    static Stream<NodeEntity> nodeEntityWithPropertyProvider() throws IOException {
        NodeEntity entity = objectMapper.readValue(NodeEntityRepositoryTest.class.getResourceAsStream(jsonFilePath), NodeEntity.class);
        return Stream.of(entity);
    }

    @BeforeEach
    public void clearEach() {
        mapper.removeAll();
        nodeEntityMapper.removeAll();
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void saveTest(NodeEntity entity) {
        nodeEntityMapper.create(entity);

        NodeEntityProperty property = new NodeEntityProperty();
        property.setEntity(entity);
        property.setName("path");
        property.setValue("hdfs://");

        mapper.create(property);

        Assertions.assertAll(
            () -> Assertions.assertNotNull(property.getId()),
            () -> Assertions.assertNotNull(property.getCreateTime()),
            () -> Assertions.assertNotNull(property.getUpdateTime())
        );
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void updateTest(NodeEntity entity) {
        nodeEntityMapper.create(entity);

        NodeEntityProperty expect = entity.getProperties().stream().findFirst().get();
        expect.setName("path");
        expect.setValue("hdfs://");

        AbstractEntityProperty actual = mapper.update(expect);

       Assertions.assertEquals(expect.toString(), actual.toString());
    }
}
