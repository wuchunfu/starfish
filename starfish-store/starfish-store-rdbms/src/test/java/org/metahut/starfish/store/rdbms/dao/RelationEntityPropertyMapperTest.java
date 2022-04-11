package org.metahut.starfish.store.rdbms.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.metahut.starfish.store.dao.INodeEntityMapper;
import org.metahut.starfish.store.dao.IRelationEntityMapper;
import org.metahut.starfish.store.model.AbstractEntityProperty;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.entity.NodeEntityProperty;
import org.metahut.starfish.store.rdbms.entity.RelationEntity;
import org.metahut.starfish.store.rdbms.entity.RelationEntityProperty;
import org.metahut.starfish.store.rdbms.repository.NodeEntityRepositoryTest;
import org.metahut.starfish.store.rdbms.repository.RelationEntityRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@Commit
@Transactional
@SpringBootTest
public class RelationEntityPropertyMapperTest {

    @Autowired
    private RelationEntityPropertyMapper mapper;

    @Autowired
    private IRelationEntityMapper relationEntityMapper;

    @Autowired
    private INodeEntityMapper nodeEntityMapper;

    public static final String jsonFilePath = "/json/relation_entity.json";

    public static final ObjectMapper objectMapper = new ObjectMapper();

    static Stream<RelationEntity> relationEntityProvider() throws IOException {
        RelationEntity entity = objectMapper.readValue(RelationEntityRepositoryTest.class.getResourceAsStream(jsonFilePath), RelationEntity.class);
        return Stream.of(entity);
    }


    @BeforeEach
    public void clearEach() {
        mapper.removeAll();
        relationEntityMapper.removeAll();
    }

    @ParameterizedTest
    @MethodSource("relationEntityProvider")
    public void saveTest(RelationEntity relation) {
        nodeEntityMapper.create(relation.getStartNodeEntity());
        nodeEntityMapper.create(relation.getEndNodeEntity());

        relationEntityMapper.create(relation);

        RelationEntityProperty property = new RelationEntityProperty();
        property.setEntity(relation);
        property.setName("path");
        property.setValue("hdfs://");
        property.setOperator(property.getOperator());

        mapper.create(property);

        Assertions.assertAll(
            () -> Assertions.assertNotNull(property.getId()),
            () -> Assertions.assertNotNull(property.getCreateTime()),
            () -> Assertions.assertNotNull(property.getUpdateTime())
        );
    }

    @ParameterizedTest
    @MethodSource("relationEntityProvider")
    public void updateTest(RelationEntity relation) {
        nodeEntityMapper.create(relation.getStartNodeEntity());
        nodeEntityMapper.create(relation.getEndNodeEntity());

        relationEntityMapper.create(relation);

        RelationEntityProperty expect = relation.getProperties().stream().findFirst().get();
        expect.setName("path");
        expect.setValue("hdfs://");

        AbstractEntityProperty actual = mapper.update(expect);

        Assertions.assertEquals(expect.toString(), actual.toString());
    }
}
