package org.metahut.starfish.store.rdbms.dao;

import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.repository.NodeEntityRepositoryTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Commit
@Transactional
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

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void saveTest(NodeEntity entity) {
        NodeEntity actual = mapper.create(entity);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertNotNull(actual.getCreateTime());
        Assertions.assertNotNull(actual.getUpdateTime());
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void removeByCategoryTest(NodeEntity entity) {
        NodeEntity actual = mapper.create(entity);
        mapper.removeAllByCategory(entity.getCategory());
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void findByNameTest(NodeEntity entity) {
        NodeEntity actual = mapper.create(entity);
        List<NodeEntity> list = mapper.findByName(entity.getName());
        Assertions.assertEquals(1L, list.size());
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void findAllByIdTest(NodeEntity entity) {
        NodeEntity actual = mapper.create(entity);
        List<NodeEntity> list = mapper.findAllById(Lists.list(entity.getId()));
        Assertions.assertEquals(1L, list.size());
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void findByCategoryTest(NodeEntity entity) {
        NodeEntity actual = mapper.create(entity);
        List<NodeEntity> list = mapper.findByCategory(entity.getCategory());
        Assertions.assertEquals(1L, list.size());
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void findByCategoryAndNameTest(NodeEntity entity) {
        NodeEntity actual = mapper.create(entity);
        List<NodeEntity> list = mapper.findByCategoryAndName(entity.getCategory(), entity.getName());
        Assertions.assertEquals(1L, list.size());
    }

    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void findByCategoryAndNamePagingTest(NodeEntity entity) {
        PageRequest request = PageRequest.of(1, 10, Sort.by("createTime"));
        NodeEntity actual = mapper.create(entity);
        Page<NodeEntity> list = mapper.findByCategoryAndName(entity.getCategory(), entity.getName(), request);
        Assertions.assertEquals(1L, list.getTotalElements());
    }

}
