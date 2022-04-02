package org.metahut.starfish.store.rdbms.repository;

import java.util.Set;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    public NodeEntity initNodeEntity() {
        NodeEntity entity = new NodeEntity();
        entity.setName(name);
        entity.setCategories(categories);
        entity.setOperator(1);
        entity = repository.save(entity);

        return entity;
    }

    public Set<NodeEntityProperty> initProperties() {
        NodeEntityProperty property = new NodeEntityProperty();

        return null;
    }

    @Test
    public void createNodeEntityTest() {
        NodeEntity entity = new NodeEntity();

        entity.setName("dwd.user_info");

        entity.setCategories(Sets.set("HiveTable", "Hive"));

        entity.setOperator(1);

        entity = repository.save(entity);

        System.out.println(entity);
    }

}
