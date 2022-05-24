package org.metahut.starfish.store.rdbms.dao;

import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.entity.NodeEntityProperty;
import org.metahut.starfish.store.rdbms.repository.NodeEntityRepositoryTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
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
        mapper.removeAll();
    }

    @Transactional
    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void saveTest(NodeEntity entity) {
        NodeEntity actual = mapper.create(entity);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertNotNull(actual.getCreateTime());
        Assertions.assertNotNull(actual.getUpdateTime());
    }

    @Transactional
    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void removeByCategoryTest(NodeEntity entity) {
        NodeEntity actual = mapper.create(entity);
        mapper.removeAllByCategory(entity.getCategory());
    }

    @Transactional
    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void findByNameTest(NodeEntity entity) {
        NodeEntity actual = mapper.create(entity);
        List<NodeEntity> list = mapper.findByQualifiedName(entity.getQualifiedName());
        Assertions.assertEquals(1L, list.size());
    }

    @Transactional
    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void findAllByIdTest(NodeEntity entity) {
        NodeEntity actual = mapper.create(entity);
        List<NodeEntity> list = mapper.findAllById(Lists.list(entity.getId()));
        Assertions.assertEquals(1L, list.size());
    }

    @Transactional
    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void findByCategoryTest(NodeEntity entity) {
        NodeEntity actual = mapper.create(entity);
        List<NodeEntity> list = mapper.findByCategory(entity.getCategory());
        Assertions.assertEquals(1L, list.size());
    }

    @Transactional
    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void findByCategoryAndQualifiedNameTest(NodeEntity entity) {
        NodeEntity actual = mapper.create(entity);
        NodeEntity expected = mapper.findByCategoryAndQualifiedName(entity.getCategory(), entity.getQualifiedName());
        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Transactional
    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void updateTest(NodeEntity entity) {
        NodeEntity savedEntity = mapper.create(entity);

        NodeEntity updateEntity = new NodeEntity();
        updateEntity.setId(savedEntity.getId());
        updateEntity.setQualifiedName(savedEntity.getQualifiedName());
        updateEntity.setCategory(savedEntity.getCategory());
        updateEntity.setOperator(savedEntity.getOperator());

        NodeEntityProperty property = new NodeEntityProperty();
        property.setName("url");
        property.setValue("http://localhost:1234");

        updateEntity.setProperties(Sets.newSet(property));
        NodeEntity actual = mapper.update(updateEntity);
    }

    @Transactional
    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void removeBatchByIdTest(NodeEntity entity) {
        NodeEntity savedEntity = mapper.create(entity);

        Assertions.assertDoesNotThrow(() -> mapper.removeBatchById(Sets.newSet(savedEntity.getId())));

    }

}
