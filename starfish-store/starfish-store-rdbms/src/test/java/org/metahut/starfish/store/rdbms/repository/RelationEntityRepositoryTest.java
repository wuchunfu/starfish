package org.metahut.starfish.store.rdbms.repository;

import org.metahut.starfish.store.rdbms.entity.RelationEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Commit
@Transactional
@SpringBootTest
public class RelationEntityRepositoryTest {

    @Autowired
    private RelationEntityRepository repository;

    @Autowired
    private NodeEntityRepository nodeEntityRepository;

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static final String jsonFilePath = "/json/relation_entity.json";

    static Stream<RelationEntity> relationEntityProvider() throws IOException {
        RelationEntity entity = objectMapper.readValue(RelationEntityRepositoryTest.class.getResourceAsStream(jsonFilePath), RelationEntity.class);
        return Stream.of(entity);
    }

    @BeforeEach
    public void clear() {
        nodeEntityRepository.deleteAll();
        repository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("relationEntityProvider")
    public void saveTest(RelationEntity entity) {
        nodeEntityRepository.save(entity.getStartNodeEntity());
        nodeEntityRepository.save(entity.getEndNodeEntity());

        RelationEntity savedRelationEntity = repository.save(entity);
        Assertions.assertAll(
            () -> Assertions.assertEquals(savedRelationEntity.getName(), entity.getName()),
            () -> Assertions.assertEquals(savedRelationEntity.getCategory(), entity.getCategory()),
            () -> Assertions.assertNotNull(savedRelationEntity.getCreateTime()),
            () -> Assertions.assertNotNull(savedRelationEntity.getUpdateTime())
        );
    }

    @ParameterizedTest
    @MethodSource("relationEntityProvider")
    public void findAllTest(RelationEntity entity) {
        nodeEntityRepository.save(entity.getStartNodeEntity());
        nodeEntityRepository.save(entity.getEndNodeEntity());
        RelationEntity savedRelationEntity = repository.save(entity);

        RelationEntity foundEntity = repository.getById(savedRelationEntity.getId());

        Assertions.assertNotNull(foundEntity.getStartNodeEntity());
        Assertions.assertNotNull(foundEntity.getEndNodeEntity());

    }

    @ParameterizedTest
    @MethodSource("relationEntityProvider")
    public void deleteRelationTest(RelationEntity entity) {
        nodeEntityRepository.save(entity.getStartNodeEntity());
        nodeEntityRepository.save(entity.getEndNodeEntity());

        RelationEntity savedRelationEntity = repository.save(entity);

        repository.delete(savedRelationEntity);

        Assertions.assertAll(
            () -> Assertions.assertNotNull(nodeEntityRepository.getById(entity.getStartNodeEntity().getId())),
            () -> Assertions.assertNotNull(nodeEntityRepository.getById(entity.getEndNodeEntity().getId()))
        );
    }

    @ParameterizedTest
    @MethodSource("relationEntityProvider")
    public void removeByStartNodeEntityAndEndNodeEntityTest(RelationEntity entity) {
        nodeEntityRepository.save(entity.getStartNodeEntity());
        nodeEntityRepository.save(entity.getEndNodeEntity());

        RelationEntity savedRelationEntity = repository.save(entity);

        List<RelationEntity> list = repository.removeByStartNodeEntityAndEndNodeEntity(entity.getStartNodeEntity(), entity.getEndNodeEntity());

        Assertions.assertEquals(list.size(), 1L);
    }

    @ParameterizedTest
    @MethodSource("relationEntityProvider")
    public void findOneTest(RelationEntity entity) {
        nodeEntityRepository.save(entity.getStartNodeEntity());
        nodeEntityRepository.save(entity.getEndNodeEntity());

        RelationEntity savedRelationEntity = repository.save(entity);

        Specification<RelationEntity> specification = new Specification<RelationEntity>() {
            @Override
            public Predicate toPredicate(Root<RelationEntity> root, CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) {
                Path<Object> name = root.get("name");

                Predicate predicate = criteriaBuilder.equal(name, "own");
                return predicate;
            }
        };

        RelationEntity actualEntity = repository.findOne(specification).get();

        Assertions.assertEquals(savedRelationEntity.toString(), actualEntity.toString());

    }

}
