package org.metahut.starfish.autoconfigure.data.rdbm;

import org.metahut.starfish.parser.domain.KeyWord;
import org.metahut.starfish.parser.domain.enums.LinkCategory;
import org.metahut.starfish.parser.domain.enums.TypeCategory;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.TypeConvertException;
import org.metahut.starfish.service.AbstractGraphService;
import org.metahut.starfish.service.AbstractLinkService;
import org.metahut.starfish.service.AbstractMetaDataService;
import org.metahut.starfish.service.AbstractNodeService;
import org.metahut.starfish.service.AbstractQueryCondition;
import org.metahut.starfish.service.AbstractRelationService;
import org.metahut.starfish.service.AbstractSourceService;
import org.metahut.starfish.service.AbstractTypeService;
import org.metahut.starfish.store.rdbms.dao.NodeEntityMapper;
import org.metahut.starfish.store.rdbms.dao.RelationEntityMapper;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.entity.NodeEntityProperty;
import org.metahut.starfish.store.rdbms.entity.NodeEntityProperty_;
import org.metahut.starfish.store.rdbms.entity.NodeEntity_;
import org.metahut.starfish.store.rdbms.entity.RelationEntity;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 */
@Configuration
@ConditionalOnClass({DataSource.class})
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class RdbmDataStorageAutoConfiguration {

    private <T> T convert(NodeEntity nodeEntity,java.lang.Class<T> classInfo) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        Map<String,Object> map = new HashMap<>();
        if (nodeEntity.getProperties() != null) {
            nodeEntity.getProperties().stream().forEach(
                    nodeEntityProperty -> map.put(nodeEntityProperty.getName(), nodeEntityProperty.getValue())
            );
        }
        map.put("name",nodeEntity.getName());
        map.put("id",nodeEntity.getId());
        return objectMapper.convertValue(map,classInfo);
    }

    private <T> Collection<T> convert(Collection<NodeEntity> nodeEntities,java.lang.Class<T> classInfo) {
        return nodeEntities
                .stream()
                .map(nodeEntity -> convert(nodeEntity,classInfo))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private <T> Page<T> convert(Page<NodeEntity> pageResult,java.lang.Class<T> classInfo) {
        return new PageImpl<>(pageResult.get()
                .map(nodeEntity -> convert(nodeEntity,classInfo))
                .collect(Collectors.toCollection(ArrayList::new)),pageResult.getPageable(),pageResult.getTotalElements());
    }

    private NodeEntity convert(Long id,TypeCategory category,String name,Map<String,Object> properties) {
        final NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setId(id);
        nodeEntity.setCategory(category.name());
        nodeEntity.setName(name);
        if (properties != null) {
            nodeEntity.setProperties(properties
                    .entrySet()
                    .stream()
                    .map(entry -> {
                        NodeEntityProperty entityProperty = new NodeEntityProperty();
                        entityProperty.setName(entry.getKey());
                        entityProperty.setEntity(nodeEntity);
                        entityProperty.setValue(entry.getValue());
                        return entityProperty;
                    })
                    .collect(Collectors.toSet()));
        }
        return nodeEntity;
    }

    private NodeEntity convert(TypeCategory category,String name,Map<String,Object> properties) {
        return convert(null,category,name,properties);
    }

    private RelationEntity convert(Long id,LinkCategory category,NodeEntity head,NodeEntity tail,String property) {
        RelationEntity relationEntity = new RelationEntity();
        relationEntity.setId(id);
        relationEntity.setCategory(category.name());
        relationEntity.setName(property);
        relationEntity.setStartNodeEntity(head);
        relationEntity.setEndNodeEntity(tail);
        return relationEntity;
    }

    private RelationEntity convert(LinkCategory category,NodeEntity head,NodeEntity tail,String property) {
        return convert(null,category,head,tail,property);
    }

    private <U> Specification<NodeEntity> convertEntity(AbstractQueryCondition<U> condition,TypeCategory typeCategory,String name) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> conditions = new ArrayList<>();
            if (StringUtils.isNotEmpty(name)) {
                conditions.add(criteriaBuilder.equal(root.get(NodeEntity_.NAME),name));
            }
            if (typeCategory != null) {
                conditions.add(criteriaBuilder.equal(root.get(NodeEntity_.CATEGORY),typeCategory.name()));
            }
            SetJoin<NodeEntity, NodeEntityProperty> join = query.from(NodeEntity.class).join(NodeEntity_.properties);
            Map<String,Object> properties = new HashMap<>();
            properties.entrySet().stream().forEach(
                    stringObjectEntry -> {
                        criteriaBuilder.and(
                                criteriaBuilder.equal(join.get(NodeEntityProperty_.name),stringObjectEntry.getKey()),
                                criteriaBuilder.equal(join.get(NodeEntityProperty_.value),stringObjectEntry.getValue())
                        );
                    }
            );

            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractLinkService.class)
    public AbstractLinkService<Long> linkService(RelationEntityMapper relationEntityMapper,NodeEntityMapper nodeEntityMapper) {
        return new AbstractLinkService<Long>() {
            @Override
            public void link(Long parentId, Long childId, LinkCategory linkCategory) throws AbstractMetaParserException {
                relationEntityMapper.create(convert(linkCategory, nodeEntityMapper.findById(parentId), nodeEntityMapper.findById(childId),linkCategory.name()));
            }

            @Override
            public void deleteLinkRelatedToIds(Collection<Long> headOrTailIds) throws AbstractMetaParserException {
                if (headOrTailIds != null) {
                    headOrTailIds.stream().forEach(id -> {
                        NodeEntity nodeEntity = new NodeEntity();
                        nodeEntity.setId(id);
                        relationEntityMapper.removeAllByEndNodeEntity(nodeEntity);
                        relationEntityMapper.removeAllByEndNodeEntity(nodeEntity);
                    });
                }
            }

            @Override
            public Long findParent(Long childId, LinkCategory linkCategory) throws AbstractMetaParserException {
                NodeEntity entity = new NodeEntity();
                entity.setId(childId);
                Optional<RelationEntity> optionalRelationEntity = relationEntityMapper.findByEndNodeEntityAndCategory(entity, linkCategory.name()).stream().findFirst();
                return optionalRelationEntity.isPresent() ? optionalRelationEntity.get().getStartNodeEntity().getId() : null;
            }

            @Override
            public Map<LinkCategory, Long> findParents(Long childId) throws AbstractMetaParserException {
                List<String> names = Arrays.stream(LinkCategory.values()).map(LinkCategory::name).collect(Collectors.toList());
                NodeEntity entity = new NodeEntity();
                entity.setId(childId);
                Collection<RelationEntity> relationEntities = relationEntityMapper.findByEndNodeEntity(entity);
                Map<LinkCategory, Long> result = new HashMap<>();
                if (relationEntities != null) {
                    for (RelationEntity relationEntity : relationEntities) {
                        if (names.contains(relationEntity.getCategory())) {
                            LinkCategory linkCategory = LinkCategory.valueOf(relationEntity.getCategory());
                            result.put(linkCategory, relationEntity.getStartNodeEntity().getId());
                        }
                    }
                }
                return result;
            }

            @Override
            public Map<LinkCategory, Collection<Long>> findChildren(Long parentId) throws AbstractMetaParserException {
                List<String> names = Arrays.stream(LinkCategory.values()).map(LinkCategory::name).collect(Collectors.toList());
                NodeEntity entity = new NodeEntity();
                entity.setId(parentId);
                Collection<RelationEntity> relationEntities = relationEntityMapper.findByStartNodeEntity(entity);
                Map<LinkCategory, Collection<Long>> result = new HashMap<>();
                if (relationEntities != null) {
                    for (RelationEntity relationEntity : relationEntities) {
                        if (names.contains(relationEntity.getCategory())) {
                            LinkCategory linkCategory = LinkCategory.valueOf(relationEntity.getCategory());
                            Collection<Long> ids = result.get(linkCategory);
                            if (ids == null) {
                                ids = new ArrayList<>();
                                result.put(linkCategory,ids);
                            }
                            ids.add(relationEntity.getEndNodeEntity().getId());
                        }
                    }
                }
                return result;
            }

            @Override
            public Collection<Long> findChildren(Long parentId, LinkCategory linkCategory) throws AbstractMetaParserException {
                NodeEntity entity = new NodeEntity();
                entity.setId(parentId);
                Collection<RelationEntity> relationEntities = relationEntityMapper.findByStartNodeEntityAndCategory(entity,linkCategory.name());
                return relationEntities.stream().map(relationEntity -> relationEntity.getEndNodeEntity().getId()).collect(Collectors.toList());
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractRelationService.class)
    public AbstractRelationService<Long,Object> relationService(NodeEntityMapper nodeEntityMapper, RelationEntityMapper relationEntityMapper) {
        return new AbstractRelationService<Long,Object>() {
            @Override
            public <T> Collection<T> query(AbstractQueryCondition<T> condition) {
                //TODO
                return null;
            }

            @Override
            public <T> Page<T> query(AbstractQueryCondition<T> condition, Pageable pageable) {
                //TODO
                return null;
            }

            @Override
            public void link(Long headId, Long tailId,LinkCategory linkCategory, String property) {
                //TODO typeName
                NodeEntity head = nodeEntityMapper.findById(headId);
                NodeEntity tail = nodeEntityMapper.findById(tailId);
                RelationEntity relationEntity = new RelationEntity();
                relationEntity.setCategory(linkCategory.name());
                relationEntity.setStartNodeEntity(head);
                relationEntity.setEndNodeEntity(tail);
                relationEntity.setName(property);
                relationEntityMapper.create(relationEntity);
            }

            @Override
            public void crack(Long headId, Long tailId, String property) throws StarFishMetaDataOperatingException {

            }

            @Override
            public void delete(Long instanceId) throws StarFishMetaDataOperatingException {
                relationEntityMapper.removeBatchById(Arrays.asList(instanceId));
            }

            @Override
            public void delete(Collection<Long> instanceIds) throws StarFishMetaDataOperatingException {
                relationEntityMapper.removeBatchById(instanceIds);
            }

            @Override
            public void deleteByHeadId(Long headId) throws StarFishMetaDataOperatingException {
                NodeEntity nodeEntity = new NodeEntity();
                nodeEntity.setId(headId);
                relationEntityMapper.removeAllByStartNodeEntity(nodeEntity);
            }

            @Override
            public void deleteByTailId(Long tailId) throws StarFishMetaDataOperatingException {
                NodeEntity nodeEntity = new NodeEntity();
                nodeEntity.setId(tailId);
                relationEntityMapper.removeAllByEndNodeEntity(nodeEntity);
            }

            @Override
            public void deleteRelationRelatedToIds(Collection<Long> headOrTailIds) throws StarFishMetaDataOperatingException {
                if (headOrTailIds != null) {
                    headOrTailIds.stream().forEach(id -> {
                        NodeEntity temp = new NodeEntity();
                        temp.setId(id);
                        relationEntityMapper.removeAllByNodeEntity(temp);
                    });
                }
            }

            @Override
            public void move(Long oldHeadId, Long newHeadId, Long tailId, String property) throws StarFishMetaDataOperatingException {

            }

            @Override
            public Collection<Long> findChildren(Long headId, LinkCategory category, String property) throws StarFishMetaDataOperatingException {
                NodeEntity nodeEntity = new NodeEntity();
                nodeEntity.setId(headId);
                Collection<RelationEntity> children = relationEntityMapper.findByStartNodeEntityAndCategoryAndName(nodeEntity, category.name(), property);
                return children.stream().map(relationEntity -> relationEntity.getEndNodeEntity().getId()).collect(Collectors.toCollection(ArrayList::new));
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractSourceService.class)
    public AbstractSourceService<Long,Object> typeInstanceBridgeService(final NodeEntityMapper nodeEntityMapper) {
        return new AbstractSourceService<Long,Object>() {
            @Override
            public Long create(String name, Map<String,Object> properties) {
                final NodeEntity nodeEntity = convert(TypeCategory.SOURCE,name,properties);
                return nodeEntityMapper.create(nodeEntity).getId();
            }

            @Override
            public void update(Long id, String name, Map<String, Object> properties) throws AbstractMetaParserException {
                final NodeEntity nodeEntity = convert(id,TypeCategory.SOURCE,name,properties);
                nodeEntityMapper.update(nodeEntity);
            }

            @Override
            public void delete(Long id) {
                nodeEntityMapper.removeBatchById(Arrays.asList(id));
            }

            @Override
            public Long getIdByName(String name) throws AbstractMetaParserException {
                List<NodeEntity> nodeEntities = nodeEntityMapper.findByCategoryAndName(TypeCategory.SOURCE.name(), name);
                if (nodeEntities == null || nodeEntities.size() < 1) {
                    return null;
                }
                return nodeEntities.get(0).getId();
            }

            @Override
            public <U> U source(Long sourceId, AbstractQueryCondition<U> condition) {
                return convert(nodeEntityMapper.findById(sourceId),condition.getResultType());
            }

            @Override
            public <T> Collection<T> query(AbstractQueryCondition<T> condition) {
                return convert(nodeEntityMapper.findByCategory(TypeCategory.SOURCE.name()),condition.getResultType());
            }

            @Override
            public <T> Page<T> query(AbstractQueryCondition<T> condition, Pageable pageable) {
                return convert(nodeEntityMapper.findByCategory(TypeCategory.SOURCE.name(),pageable),condition.getResultType());
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractTypeService.class)
    public AbstractTypeService<Long,Object> typeService(final NodeEntityMapper nodeEntityMapper,final RelationEntityMapper relationEntityMapper) {
        return new AbstractTypeService<Long,Object>() {

            private void addClassInfo(NodeEntity nodeEntity,Class classInfo) {
                if (nodeEntity.getProperties() == null) {
                    nodeEntity.setProperties(new HashSet<>());
                }
                NodeEntityProperty classProperty = new NodeEntityProperty();
                classProperty.setName(KeyWord.CLASS.getValue());
                classProperty.setValue(classInfo);
                classProperty.setEntity(nodeEntity);
                nodeEntity.getProperties().add(classProperty);
            }

            @Override
            public <T> Collection<T> query(AbstractQueryCondition<T> condition) {
                return convert(nodeEntityMapper.findByCategory(TypeCategory.CLASSIFICATION.name()), condition.getResultType());
            }

            @Override
            public <T> Page<T> query(AbstractQueryCondition<T> condition, Pageable pageable) {
                return convert(nodeEntityMapper.findByCategory(TypeCategory.CLASSIFICATION.name(),pageable), condition.getResultType());
            }

            @Override
            public Long create(Long sourceId, Class classInfo, Map<String, Object> properties) throws AbstractMetaParserException {
                final NodeEntity nodeEntity = convert(TypeCategory.CLASSIFICATION,classInfo.fullClassName(),properties);
                addClassInfo(nodeEntity,classInfo);
                return nodeEntityMapper.create(nodeEntity).getId();
            }

            @Override
            public void update(Long id, Class classInfo, Map<String, Object> properties) throws AbstractMetaParserException {
                final NodeEntity nodeEntity = convert(id,TypeCategory.CLASSIFICATION,classInfo.fullClassName(),properties);
                addClassInfo(nodeEntity,classInfo);
                nodeEntityMapper.update(nodeEntity);
            }

            @Override
            public void delete(Long id) throws AbstractMetaParserException {
                delete(Arrays.asList(id));
            }

            @Override
            public void delete(Collection<Long> ids) throws AbstractMetaParserException {
                nodeEntityMapper.removeBatchById(ids);
            }

            @Override
            public Map<Long,Class> types(Collection<Long> typeIds) {
                List<NodeEntity> types = nodeEntityMapper.findAllById(typeIds);
                return types.stream().filter(bean -> bean != null).collect(Collectors.toMap(NodeEntity::getId,
                        nodeEntity -> readClassFromNodeEntity(nodeEntity)));
            }

            @Override
            public Map<Long,Class> types(Long sourceId) {
                NodeEntity sourceEntity = new NodeEntity();
                sourceEntity.setId(sourceId);
                Collection<RelationEntity> relationEntityCollection = relationEntityMapper.findByStartNodeEntityAndCategory(sourceEntity, LinkCategory.SOURCE_TYPE.name());
                return relationEntityCollection.stream().collect(Collectors.toMap(relationEntity -> relationEntity.getEndNodeEntity().getId(),
                        relationEntity -> readClassFromNodeEntity(relationEntity.getEndNodeEntity())));
            }

            @Override
            public Long getIdByName(String name) {
                List<NodeEntity> nodeEntities = nodeEntityMapper.findByCategoryAndName(TypeCategory.CLASSIFICATION.name(), name);
                if (nodeEntities == null || nodeEntities.size() < 1) {
                    return null;
                }
                return nodeEntities.get(0).getId();
            }

            @Override
            public Class type(Long typeId) {
                NodeEntity typeEntity = nodeEntityMapper.findById(typeId);
                return readClassFromNodeEntity(typeEntity);
            }

            private Class readClassFromNodeEntity (NodeEntity nodeEntity) {
                Optional<NodeEntityProperty> first = nodeEntity
                        .getProperties().stream().filter(nodeEntityProperty -> KeyWord.CLASS.getValue().equals(nodeEntityProperty.getName())).findFirst();
                if (first.isPresent()) {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        return objectMapper.convertValue(first.get().getValue(),Class.class);
                    } catch (IllegalArgumentException exception) {
                        throw new TypeConvertException("Convert type error.",exception);
                    }
                } else {
                    throw new TypeConvertException("Null type of entity " + nodeEntity.getId());
                }
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractNodeService.class)
    public AbstractNodeService<Long,Object> nodeService(final NodeEntityMapper nodeEntityMapper) {
        return new AbstractNodeService<Long, Object>() {
            @Override
            public Long create(String name, Map<String, Object> properties) throws AbstractMetaParserException {
                return nodeEntityMapper.create(convert(TypeCategory.ENTITY,name,properties)).getId();
            }

            @Override
            public void update(Long id, String name, Map<String, Object> properties) throws AbstractMetaParserException {
                nodeEntityMapper.update(convert(id,TypeCategory.ENTITY,name,properties));
            }

            @Override
            public void delete(Long id) throws AbstractMetaParserException {
                delete(Arrays.asList(id));
            }

            @Override
            public void delete(Collection<Long> ids) throws AbstractMetaParserException {
                nodeEntityMapper.removeBatchById(ids);
            }

            @Override
            public <U> U node(Long nodeId, AbstractQueryCondition<U> condition) throws AbstractMetaParserException {
                return convert(nodeEntityMapper.findById(nodeId),condition.getResultType());
            }

            @Override
            public <U> Collection<U> nodes(Collection<Long> nodeIds, AbstractQueryCondition<U> condition) throws AbstractMetaParserException {
                List<NodeEntity> nodeEntities = nodeEntityMapper.findAllById(nodeIds);
                return convert(nodeEntities,condition.getResultType());
            }

            @Override
            public <U> Page<U> nodes(Collection<Long> nodeIds, AbstractQueryCondition<U> condition, Pageable page) throws AbstractMetaParserException {
                Page<NodeEntity> pageResult = nodeEntityMapper.findAllById(nodeIds, page);
                return convert(pageResult,condition.getResultType());
            }

            @Override
            public <U> Collection<U> query(AbstractQueryCondition<U> condition) {
                return convert(nodeEntityMapper.findByCategory(TypeCategory.ENTITY.name()),condition.getResultType());
            }

            @Override
            public <T> Page<T> query(AbstractQueryCondition<T> condition, Pageable pageable) {
                return convert(nodeEntityMapper.findByCategory(TypeCategory.ENTITY.name(),pageable),condition.getResultType());
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractGraphService.class)
    public AbstractGraphService<Long,Object> graphService(NodeEntityMapper nodeEntityMapper, AbstractNodeService<Long,Object> nodeService, AbstractRelationService<Long,Object> relationService) {
        return new AbstractGraphService<Long, Object>() {
            @Override
            protected AbstractNodeService<Long, Object> nodeService() {
                return nodeService;
            }

            @Override
            protected AbstractRelationService<Long, Object> relationService() {
                return relationService;
            }

            @Override
            public void deleteNode(Long id) throws AbstractMetaParserException {
                this.deleteNodes(Arrays.asList(id));
            }

            @Override
            public void deleteNodes(Collection<Long> ids) throws AbstractMetaParserException {
                nodeEntityMapper.removeBatchById(ids);
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractMetaDataService.class)
    public AbstractMetaDataService<Long,Object> metaDataService(
            AbstractSourceService<Long,Object> sourceService,
            AbstractGraphService<Long,Object> graphService,
            AbstractTypeService<Long,Object> typeService,
            AbstractLinkService<Long> linkService) {
        return new AbstractMetaDataService<Long,Object>() {
            @Override
            protected AbstractSourceService<Long, Object> sourceApi() {
                return sourceService;
            }

            @Override
            protected AbstractGraphService<Long, Object> graphApi() {
                return graphService;
            }

            @Override
            protected AbstractTypeService<Long, Object> typeApi() {
                return typeService;
            }

            @Override
            protected AbstractLinkService<Long> linkApi() {
                return linkService;
            }
        };
    }
}
