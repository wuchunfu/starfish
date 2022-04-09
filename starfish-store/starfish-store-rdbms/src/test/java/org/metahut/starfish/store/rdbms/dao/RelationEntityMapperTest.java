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
import org.metahut.starfish.store.model.AbstractRelationEntity;
import org.metahut.starfish.store.rdbms.entity.RelationEntity;
import org.metahut.starfish.store.rdbms.repository.RelationEntityRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@Commit
@Transactional
@SpringBootTest
public class RelationEntityMapperTest {

    @Autowired
    private IRelationEntityMapper mapper;

    @Autowired
    private INodeEntityMapper nodeEntityMapper;

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static final String jsonFilePath = "/json/relation_entity.json";

    static Stream<RelationEntity> relationEntityProvider() throws IOException {
        RelationEntity entity = objectMapper.readValue(RelationEntityRepositoryTest.class.getResourceAsStream(jsonFilePath), RelationEntity.class);
        return Stream.of(entity);
    }

    @BeforeEach
    public void clear() {
        nodeEntityMapper.removeAll();
        mapper.removeAll();
    }

    @ParameterizedTest
    @MethodSource("relationEntityProvider")
    public void saveTest(RelationEntity entity) {

        AbstractRelationEntity  savedRelationEntity = mapper.create(entity);
        Assertions.assertAll(
            () -> Assertions.assertEquals(savedRelationEntity.getName(), entity.getName()),
            () -> Assertions.assertEquals(savedRelationEntity.getCategory(), entity.getCategory()),
            () -> Assertions.assertNotNull(savedRelationEntity.getCreateTime()),
            () -> Assertions.assertNotNull(savedRelationEntity.getUpdateTime())
        );
    }

}
