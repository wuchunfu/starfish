package org.metahut.starfish.store.rdbms.repository;

import org.metahut.starfish.store.model.AbstractNodeEntity;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootTest
public class NodeEntityRepositoryTest {

    @Autowired
    private NodeEntityRepository repository;

    public static final String jsonFilePath = "/json/node_entity.json";

    public static final String typeJsonData = "/json/type_entity.json";

    public static final ObjectMapper objectMapper = new ObjectMapper();

    static Stream<NodeEntity> nodeEntityWithPropertyProvider() throws IOException {
        NodeEntity entity = objectMapper.readValue(NodeEntityRepositoryTest.class.getResourceAsStream(jsonFilePath), NodeEntity.class);
        entity.getProperties().stream().forEach(property -> {
            property.setEntity(entity);
        });
        return Stream.of(entity);
    }

    static Stream<NodeEntity> typeEntityWithPropertyProvider() throws IOException {
        NodeEntity entity = objectMapper.readValue(NodeEntityRepositoryTest.class.getResourceAsStream(typeJsonData), NodeEntity.class);
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

    @Transactional
    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void saveWithPropertiesTest(NodeEntity entity) {
        NodeEntity savedEntity = repository.save(entity);
        Assertions.assertNotNull(savedEntity.getId());
        Assertions.assertNotNull(savedEntity.getCreateTime());
        Assertions.assertNotNull(savedEntity.getUpdateTime());
        savedEntity.getProperties().stream().forEach(property -> Assertions.assertNotNull(property.getValue()));
    }

    @Transactional
    @ParameterizedTest
    @Disabled
    @MethodSource("typeEntityWithPropertyProvider")
    public void findByIdWithPropertiesTest(NodeEntity entity) {
        NodeEntity savedEntity = repository.save(entity);
        repository.findAllById(Sets.newHashSet(savedEntity.getId())).stream().forEach(nodeEntity -> {
            nodeEntity.getProperties().stream().forEach(property -> {
                Assertions.assertNotNull(property.getValue());
            });
        });
    }

    @Transactional
    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void findWithPropertiesTest(NodeEntity entity) {
        NodeEntity expected = repository.save(entity);

        AbstractNodeEntity actual = repository.findById(expected.getId()).get();

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Transactional
    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void updateTest(NodeEntity entity) {
        NodeEntity savedEntity = repository.save(entity);

        String alteredName = "dwd.user_info_test";
        savedEntity.setQualifiedName(alteredName);

        NodeEntity expected = repository.save(savedEntity);

        Assertions.assertEquals(expected.getQualifiedName(), alteredName);
    }

    @Transactional
    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void removeByNameTest(NodeEntity entity) {
        NodeEntity savedEntity = repository.save(entity);
        List<NodeEntity> list = repository.removeByQualifiedName(entity.getQualifiedName());
        Assertions.assertEquals(1L, list.size());
    }

    @Transactional
    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void findOneTest(NodeEntity entity) {
        NodeEntity savedEntity = repository.save(entity);
        Specification<NodeEntity> specification = new Specification<NodeEntity>() {
            @Override
            public Predicate toPredicate(Root<NodeEntity> root, CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) {
                Path<Object> qualifiedName =  root.get("qualifiedName");
                Predicate predicate = criteriaBuilder.equal(qualifiedName, "idc_hive.dwd.user_info");
                return predicate;
            }
        };

        Optional<NodeEntity> nodeEntityOptional = repository.findOne(specification);

        NodeEntity actualEntity = nodeEntityOptional.get();

        Assertions.assertEquals(savedEntity.toString(), actualEntity.toString());
    }

    @Transactional
    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void findAllTest(NodeEntity entity) {
        NodeEntity savedEntity = repository.save(entity);
        Specification<NodeEntity> specification = new Specification<NodeEntity>() {
            @Override
            public Predicate toPredicate(Root<NodeEntity> root, CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) {
                Path<Object> qualifiedName =  root.get("qualifiedName");
                Predicate predicate = criteriaBuilder.equal(qualifiedName, "idc_hive.dwd.user_info");
                return predicate;
            }
        };

        List<NodeEntity> nodeEntityList = repository.findAll(specification);

        NodeEntity actualEntity = nodeEntityList.get(0);

        Assertions.assertEquals(savedEntity.toString(), actualEntity.toString());
    }

    @Transactional
    @ParameterizedTest
    @MethodSource("nodeEntityWithPropertyProvider")
    public void findAllPagingTest(NodeEntity entity) {
        NodeEntity savedEntity = repository.save(entity);
        Specification<NodeEntity> specification = new Specification<NodeEntity>() {
            @Override
            public Predicate toPredicate(Root<NodeEntity> root, CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) {
                Path<Object> qualifiedName =  root.get("qualifiedName");
                Predicate predicate = criteriaBuilder.equal(qualifiedName, "idc_hive.dwd.user_info");
                return predicate;
            }
        };

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<NodeEntity> page = repository.findAll(specification, pageRequest);

        NodeEntity actualEntity = page.stream().findFirst().get();

        Assertions.assertEquals(savedEntity.toString(), actualEntity.toString());
    }
}
