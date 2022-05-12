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
import org.metahut.starfish.store.rdbms.entity.RelationEntity;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import java.lang.reflect.InvocationTargetException;
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
            public Collection query(AbstractQueryCondition condition) {
                return null;
            }

            @Override
            public void link(Long headId, Long tailId, String property) {
                //TODO typeName
                NodeEntity head = nodeEntityMapper.findById(headId);
                NodeEntity tail = nodeEntityMapper.findById(tailId);
                RelationEntity relationEntity = new RelationEntity();
                relationEntity.setCategory(TypeCategory.RELATIONSHIP.name());
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
            public Collection<Object> query(AbstractQueryCondition condition) {
                return null;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractTypeService.class)
    public AbstractTypeService<Long,Object> typeService(final NodeEntityMapper nodeEntityMapper) {
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
            public Collection<Object> query(AbstractQueryCondition condition) {
                return null;
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
                        nodeEntity -> {
                            Optional<NodeEntityProperty> first = nodeEntity
                                    .getProperties().stream().filter(nodeEntityProperty -> KeyWord.CLASS.getValue().equals(nodeEntityProperty.getName())).findFirst();
                            if (first.isPresent()) {
                                Class resultClass = new Class();
                                try {
                                    BeanUtils.copyProperties(resultClass,first.get().getValue());
                                } catch (IllegalAccessException | InvocationTargetException exception) {
                                    throw new TypeConvertException("Convert type error.",exception);
                                }
                                return resultClass;
                            } else {
                                return null;
                            }
                        }));
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
            public Collection<Object> query(AbstractQueryCondition condition) {
                return null;
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
