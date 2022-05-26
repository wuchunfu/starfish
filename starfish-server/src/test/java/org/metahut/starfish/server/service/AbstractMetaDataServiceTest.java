package org.metahut.starfish.server.service;

import org.metahut.starfish.parser.domain.enums.LinkCategory;
import org.metahut.starfish.parser.domain.enums.RelType;
import org.metahut.starfish.parser.domain.enums.TypeCategory;
import org.metahut.starfish.parser.domain.instance.Attribute;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.exception.TypeExistsException;
import org.metahut.starfish.scheduler.dolphinscheduler.JSONUtils;
import org.metahut.starfish.service.AbstractMetaDataService;
import org.metahut.starfish.service.AbstractNodeService;
import org.metahut.starfish.service.AbstractQueryCondition;
import org.metahut.starfish.service.expression.ConditionPiece;
import org.metahut.starfish.service.expression.EachPointer;
import org.metahut.starfish.service.expression.RelationType;
import org.metahut.starfish.service.expression.SampleExpression;
import org.metahut.starfish.service.expression.StringExpression;
import org.metahut.starfish.service.expression.TableType;
import org.metahut.starfish.store.rdbms.dao.NodeEntityMapper;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 */
@Transactional
@SpringBootTest
class AbstractMetaDataServiceTest {

    private Logger logger = LoggerFactory.getLogger(AbstractMetaDataServiceTest.class);

    @Autowired
    private AbstractMetaDataService<Long,Object> metaDataService;

    public static class Source {
        private Long id;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    public static class Instance {
        private Long id;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    private Class classInfo() {
        Class classInfo = new Class();
        classInfo.setName("myTest");
        classInfo.setNameAttributeRel("name");
        List<Attribute> attributeList = new ArrayList<>();
        Attribute attribute = new Attribute();
        attribute.setArray(false);
        attribute.setName("name");
        attribute.setClassName("String");
        attribute.setRelType(RelType.PRIMITIVE);
        attribute.setDefaultValue("");
        attributeList.add(attribute);
        Attribute attribute1 = new Attribute();
        attribute1.setArray(false);
        attribute1.setName("password");
        attribute1.setClassName("String");
        attribute1.setRelType(RelType.PRIMITIVE);
        attribute1.setDefaultValue("");
        attributeList.add(attribute1);
        classInfo.setAttributes(attributeList);
        return classInfo;
    }

    private Map<String,Object> instanceInfo() {
        Map<String,Object> map = new HashMap<>();
        map.put("name","1111");
        map.put("password","wor");
        return map;
    }

    @Resource
    private NodeEntityMapper nodeEntityMapper;

    @Resource
    private AbstractNodeService<Long,Object> nodeService;

    private SampleExpression entityCategory() {
        SampleExpression sampleExpression = new SampleExpression();
        sampleExpression.setLeftExpression(new StringExpression("category"));
        sampleExpression.setRightExpression(new StringExpression(TypeCategory.ENTITY.name()));
        return sampleExpression;
    }

    private SampleExpression linkRelationshipCategory() {
        SampleExpression sampleExpression = new SampleExpression();
        sampleExpression.setLeftExpression(new StringExpression("category"));
        sampleExpression.setRightExpression(new StringExpression(LinkCategory.RELATIONSHIP.name()));
        return sampleExpression;
    }

    private SampleExpression classificationCategory() {
        SampleExpression sampleExpression = new SampleExpression();
        sampleExpression.setLeftExpression(new StringExpression("category"));
        sampleExpression.setRightExpression(new StringExpression(TypeCategory.CLASSIFICATION.name()));
        return sampleExpression;
    }

    private SampleExpression typeEntityCategory() {
        SampleExpression sampleExpression = new SampleExpression();
        sampleExpression.setLeftExpression(new StringExpression("category"));
        sampleExpression.setRightExpression(new StringExpression(LinkCategory.TYPE_ENTITY.name()));
        return sampleExpression;
    }

    private ConditionPiece properties() {
        ConditionPiece conditionNext = new ConditionPiece();
        conditionNext.setTableType(TableType.ENTITY_PROPERTY);
        SampleExpression sampleExpression1 = new SampleExpression();
        sampleExpression1.setLeftExpression(new StringExpression("name"));
        sampleExpression1.setRightExpression(new StringExpression("{\"name\":\"1111\"}"));
        //conditionNext.setExpressions(Arrays.asList(sampleExpression1));
        return conditionNext;
    }

    private ConditionPiece typeCondition() {
        ConditionPiece conditionNext = new ConditionPiece();
        conditionNext.setTableType(TableType.ENTITY);
        SampleExpression sampleExpression1 = new SampleExpression();
        sampleExpression1.setLeftExpression(new StringExpression("qualifiedName"));
        sampleExpression1.setRightExpression(new StringExpression("myTest"));
        conditionNext.setExpressions(Arrays.asList(sampleExpression1,classificationCategory()));
        return conditionNext;
    }

    private ConditionPiece parentTypeRelation() {
        ConditionPiece conditionNext = new ConditionPiece();
        conditionNext.setTableType(TableType.RELATION);
        conditionNext.setExpressions(Arrays.asList(typeEntityCategory()));
        Map<String,ConditionPiece> map = new HashMap();
        map.put("startNodeEntity",typeCondition());
        conditionNext.setNextConditionChain(map);
        return conditionNext;
    }

    private ConditionPiece conditionPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        SampleExpression sampleExpression = new SampleExpression();
        sampleExpression.setLeftExpression(new StringExpression("qualifiedName"));
        sampleExpression.setRightExpression(new StringExpression("1111"));
        conditionPiece.setExpressions(Arrays.asList(sampleExpression,entityCategory()));
        Map<String,ConditionPiece> map = new HashMap<>();
        map.put("properties",properties());
        map.put("parent",parentTypeRelation());
        conditionPiece.setNextConditionChain(map);
        return conditionPiece;
    }

    private ConditionPiece conditionPiece1() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        SampleExpression sampleExpression = new SampleExpression();
        sampleExpression.setLeftExpression(new StringExpression("qualifiedName"));
        sampleExpression.setRightExpression(new StringExpression("1111"));
        conditionPiece.setExpressions(Arrays.asList(sampleExpression));
        Map<String,ConditionPiece> map = new HashMap<>();
        ConditionPiece conditionNext = new ConditionPiece();
        conditionNext.setTableType(TableType.RELATION);
        SampleExpression sampleExpression1 = new SampleExpression();
        sampleExpression1.setLeftExpression(new StringExpression("name"));
        sampleExpression1.setRightExpression(new StringExpression("1111"));
        //conditionNext.setExpressions(Arrays.asList(sampleExpression1));
        map.put("properties",conditionNext);
        conditionPiece.setNextConditionChain(map);
        return conditionPiece;
    }

    private EachPointer sourceEachPointer() {
        EachPointer eachPointer = new EachPointer();
        eachPointer.setCategory(LinkCategory.SOURCE_ENTITY);
        eachPointer.setRelationType(RelationType.PARENT);
        return eachPointer;
    }

    private EachPointer typeEachPointer() {
        EachPointer eachPointer = new EachPointer();
        eachPointer.setCategory(LinkCategory.TYPE_ENTITY);
        eachPointer.setRelationType(RelationType.PARENT);
        return eachPointer;
    }

    private Map<String, EachPointer> eachPointerMap() {
        Map<String, EachPointer> map = new HashMap<>();
        map.put(LinkCategory.SOURCE_ENTITY.name(),sourceEachPointer());
        map.put(LinkCategory.TYPE_ENTITY.name(),typeEachPointer());
        return map;
    }

    @Test
    public void test() {
        assertDoesNotThrow(() -> {
            AbstractQueryCondition<Map> condition = new AbstractQueryCondition<>();
            condition.setResultType(Map.class);
            condition.setFilters(Arrays.asList(
                    conditionPiece()
            ));
            condition.setEachPointers(eachPointerMap());
            Collection<Map> query = nodeService.query(condition);
            logger.info("{}",query);
        });
    }

    @Test
    void sources() {
        assertDoesNotThrow(() -> {
            AbstractQueryCondition<Source> condition = new AbstractQueryCondition<>();
            condition.setResultType(Source.class);
            Collection<Source> sources = metaDataService.sources(condition);
            logger.info("{}",sources);
        });
    }

    @Test
    void testSources() {
        assertDoesNotThrow(() -> {
            AbstractQueryCondition<Source> condition = new AbstractQueryCondition<>();
            condition.setResultType(Source.class);
            Page<Source> pageSource = metaDataService.sources(condition, PageRequest.of(0,10));
            logger.info("page:{},all:{},constant:{}",pageSource.getNumber(),pageSource.getTotalElements(),pageSource.getContent());
        });
    }

    @Test
    void types() {
        assertDoesNotThrow(() -> {
            AbstractQueryCondition<Source> condition = new AbstractQueryCondition<>();
            condition.setResultType(Source.class);
            Collection<Source> sources = metaDataService.sources(condition);
            for (Source source : sources) {
                Collection<Class> classInfos = metaDataService.types(source.getId());
                for (Class classInfo : classInfos) {
                    logger.info("classInfo:{}", JSONUtils.toJSONString(classInfo));
                }
            }
        });
    }

    @Test
    void instance() {
        assertDoesNotThrow(() -> {
            Map<String,Object> map = new HashMap<>();
            map.put("name","123");
            Long sourceId = metaDataService.createSource("test",map);
            Long typeId = metaDataService.createType(sourceId,classInfo(),null);
            Map<String, Object> instanceInfo = instanceInfo();
            Long entityId = metaDataService.createEntity(typeId, String.valueOf(instanceInfo.get("name")), instanceInfo);
            Instance instance = metaDataService.instance(entityId, Instance.class);
            assertTrue(instance.getName().equals(String.valueOf(instanceInfo.get("name"))));
        });
    }

    @Test
    void instances() {
        assertDoesNotThrow(() -> {
            Map<String,Object> map = new HashMap<>();
            map.put("name","123");
            Long sourceId = metaDataService.createSource("test",map);
            Long typeId = metaDataService.createType(sourceId,classInfo(),null);
            Map<String, Object> instanceInfo = instanceInfo();
            Long entityId = metaDataService.createEntity(typeId, String.valueOf(instanceInfo.get("name")), instanceInfo);
            Collection<Instance> instance = metaDataService.instances(typeId, Instance.class);
            assertTrue(instance.size() == 1);
            assertTrue(Long.compare(instance.stream().findFirst().get().getId(),entityId) == 0);
        });
    }

    @Test
    void instancesByName() {
        assertDoesNotThrow(() -> {
            Map<String,Object> map = new HashMap<>();
            map.put("name","123");
            Long sourceId = metaDataService.createSource("test",map);
            Long typeId = metaDataService.createType(sourceId,classInfo(),null);
            Map<String, Object> instanceInfo = instanceInfo();
            Long entityId = metaDataService.createEntity(typeId, String.valueOf(instanceInfo.get("name")), instanceInfo);
            Collection<Instance> instance = metaDataService.instancesByName(classInfo().fullClassName(), Instance.class);
            assertTrue(instance.size() == 1);
            assertTrue(Long.compare(instance.stream().findFirst().get().getId(),entityId) == 0);
        });
    }

    @Test
    void createSource() {
        Map<String,Object> map = new HashMap<>();
        map.put("wh",1);
        metaDataService.createSource("test",map);
    }

    @Test
    void createType() {
        Map<String,Object> map = new HashMap<>();
        map.put("wh",1);
        Long sourceId = metaDataService.createSource("test",map);
        Class classInfo = new Class();
        classInfo.setName("mytest");
        assertDoesNotThrow(() -> {
            metaDataService.createType(sourceId, classInfo, null);
        });
        assertThrowsExactly(TypeExistsException.class,() -> {
            metaDataService.createType(sourceId, classInfo, null);
        });
    }

    @Test
    void createEntityByTypeName() {
        assertDoesNotThrow(() -> {
            Map<String,Object> map = new HashMap<>();
            map.put("name","123");
            Long sourceId = metaDataService.createSource("test",map);
            metaDataService.createType(sourceId,classInfo(),null);
            Long id = metaDataService.createEntityByTypeName(classInfo().fullClassName(), "testEntity", null);
            logger.info("{}",id);
        });
    }

    @Test
    void createEntity() {
        assertDoesNotThrow(() -> {
            Map<String,Object> map = new HashMap<>();
            map.put("name","123");
            Long sourceId = metaDataService.createSource("test",map);
            Long typeId = metaDataService.createType(sourceId,classInfo(),null);
            Map<String, Object> instanceInfo = instanceInfo();
            Long entityId = metaDataService.createEntity(typeId, String.valueOf(instanceInfo.get("name")), instanceInfo);
            assertNotNull(entityId);
        });
    }

    @Test
    void updateSource() {
        assertDoesNotThrow(() -> {
            Map<String,Object> map = new HashMap<>();
            map.put("name","123");
            Long sourceId = metaDataService.createSource("test",map);
            metaDataService.updateSource(sourceId,"testNew",null);
            Source source = metaDataService.source(sourceId,Source.class);
            assertTrue("testNew".equals(source.getName()));
            logger.info("{}",sourceId);
        });
    }

    @Test
    void updateType() {
        assertDoesNotThrow(() -> {
            Map<String,Object> map = new HashMap<>();
            map.put("name","123");
            Long sourceId = metaDataService.createSource("test",map);
            Long typeId = metaDataService.createType(sourceId,classInfo(),null);
            Class update = classInfo();
            update.setName("testNew");
            metaDataService.updateType(typeId,update,null);
            Class type = metaDataService.type(typeId);
            assertTrue("testNew".equals(type.getName()));
        });
    }

    @Test
    void updateEntity() {
        assertDoesNotThrow(() -> {
            Map<String,Object> map = new HashMap<>();
            map.put("name","123");
            Long sourceId = metaDataService.createSource("test",map);
            Long typeId = metaDataService.createType(sourceId,classInfo(),null);
            Map<String, Object> instanceInfo = instanceInfo();
            Long entityId = metaDataService.createEntity(typeId, String.valueOf(instanceInfo.get("name")), instanceInfo);
            instanceInfo.put("name","nameNew");
            metaDataService.updateEntity(entityId, String.valueOf(instanceInfo.get("name")), instanceInfo);
            Instance instance = metaDataService.instance(entityId, Instance.class);
            assertTrue(instance.getName().equals("nameNew"));
        });
    }

    @Test
    void deleteSource() {
        assertDoesNotThrow(() -> {
            Map<String,Object> map = new HashMap<>();
            map.put("name","123");
            Long sourceId = metaDataService.createSource("test",map);
            Long typeId = metaDataService.createType(sourceId,classInfo(),null);
            Map<String, Object> instanceInfo = instanceInfo();
            Long entityId = metaDataService.createEntity(typeId, String.valueOf(instanceInfo.get("name")), instanceInfo);
            metaDataService.deleteSource(sourceId);
        });
    }

    @Test
    void deleteType() {
        assertDoesNotThrow(() -> {
            Map<String,Object> map = new HashMap<>();
            map.put("name","123");
            Long sourceId = metaDataService.createSource("test",map);
            Long typeId = metaDataService.createType(sourceId,classInfo(),null);
            Map<String, Object> instanceInfo = instanceInfo();
            Long entityId = metaDataService.createEntity(typeId, String.valueOf(instanceInfo.get("name")), instanceInfo);
            metaDataService.deleteType(typeId);
        });
    }

    @Test
    void deleteEntity() {
        assertDoesNotThrow(() -> {
            Map<String,Object> map = new HashMap<>();
            map.put("name","123");
            Long sourceId = metaDataService.createSource("test",map);
            Long typeId = metaDataService.createType(sourceId,classInfo(),null);
            Map<String, Object> instanceInfo = instanceInfo();
            Long entityId = metaDataService.createEntity(typeId, String.valueOf(instanceInfo.get("name")), instanceInfo);
            metaDataService.deleteEntity(entityId);
        });
    }

    @Test
    void link() {
    }

    @Test
    void crack() {
    }
}
