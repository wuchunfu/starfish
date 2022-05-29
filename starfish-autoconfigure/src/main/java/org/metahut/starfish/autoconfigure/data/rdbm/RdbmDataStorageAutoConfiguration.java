package org.metahut.starfish.autoconfigure.data.rdbm;

import org.metahut.starfish.parser.domain.KeyWord;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.TypeConvertException;
import org.metahut.starfish.service.AbstractGraphService;
import org.metahut.starfish.service.AbstractLinkService;
import org.metahut.starfish.service.AbstractMetaDataService;
import org.metahut.starfish.service.AbstractNodeService;
import org.metahut.starfish.service.AbstractRelationService;
import org.metahut.starfish.service.AbstractSourceService;
import org.metahut.starfish.service.AbstractTypeService;
import org.metahut.starfish.store.rdbms.dao.NodeEntityMapper;
import org.metahut.starfish.store.rdbms.dao.RelationEntityMapper;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.entity.NodeEntityProperty;
import org.metahut.starfish.store.rdbms.entity.NodeEntity_;
import org.metahut.starfish.store.rdbms.entity.RelationEntity;
import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.enums.Category;
import org.metahut.starfish.unit.enums.LinkCategory;
import org.metahut.starfish.unit.enums.TypeCategory;
import org.metahut.starfish.unit.expression.AndExpression;
import org.metahut.starfish.unit.expression.BetweenAndExpression;
import org.metahut.starfish.unit.expression.BinaryExpression;
import org.metahut.starfish.unit.expression.ConditionPiece;
import org.metahut.starfish.unit.expression.DateBetweenAndExpression;
import org.metahut.starfish.unit.expression.EachPointer;
import org.metahut.starfish.unit.expression.EmptyExpression;
import org.metahut.starfish.unit.expression.EqualExpression;
import org.metahut.starfish.unit.expression.Expression;
import org.metahut.starfish.unit.expression.GreaterThanExpression;
import org.metahut.starfish.unit.expression.GreaterThanOrEqualToExpression;
import org.metahut.starfish.unit.expression.InExpression;
import org.metahut.starfish.unit.expression.LessThanExpression;
import org.metahut.starfish.unit.expression.LessThanOrEqualToExpression;
import org.metahut.starfish.unit.expression.LikeExpression;
import org.metahut.starfish.unit.expression.NullExpression;
import org.metahut.starfish.unit.expression.OrExpression;
import org.metahut.starfish.unit.expression.TrueExpression;

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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 */
@Configuration
@ConditionalOnClass({DataSource.class})
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class RdbmDataStorageAutoConfiguration {

    private <T> T convert(NodeEntity nodeEntity,java.lang.Class<T> classInfo,Map<String,EachPointer> eachPointers) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true);
        objectMapper.configure(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS,true);
        Map<String,Object> result = forEach(nodeEntity,eachPointers);
        return objectMapper.convertValue(result,classInfo);
    }

    private Map<String,Object> forEach(NodeEntity nodeEntity,Map<String,EachPointer> eachPointers) {
        Map<String,Object> resultMap = toMap(nodeEntity);
        if (eachPointers != null) {
            for (Map.Entry<String, EachPointer> entry : eachPointers.entrySet()) {
                forEach(resultMap,nodeEntity,entry);
            }
        }
        return resultMap;
    }

    //TODO how to know children or child  without query typeInfo or other support
    private void forEach(Map<String,Object> map,NodeEntity nodeEntity,Map.Entry<String, EachPointer> eachPointerEntry) {
        List<Map<String,Object>> container = new ArrayList<>();
        EachPointer eachPointer = eachPointerEntry.getValue();
        if (eachPointer != null) {
            if (eachPointer.getRelationType() != null) {
                List<RelationEntity> relations;
                switch (eachPointer.getRelationType()) {
                    case PARENT:
                        relations = nodeEntity.getParent();
                        if (relations != null) {
                            for (RelationEntity relation : relations) {
                                NodeEntity startNodeEntity = relation.getStartNodeEntity();
                                if (startNodeEntity != null && valid(relation,eachPointerEntry.getKey(),eachPointer.getCategory())) {
                                    Map<String, Object> next = forEach(startNodeEntity,eachPointer.getEachPointers());
                                    container.add(next);
                                }
                            }
                        }
                        break;
                    case CHILD:
                        relations = nodeEntity.getChildren();
                        if (relations != null) {
                            for (RelationEntity relation : relations) {
                                NodeEntity endNodeEntity = relation.getEndNodeEntity();
                                if (endNodeEntity != null && valid(relation,eachPointerEntry.getKey(),eachPointer.getCategory())) {
                                    Map<String, Object> next = forEach(endNodeEntity,eachPointer.getEachPointers());
                                    container.add(next);
                                }
                            }
                        }
                        break;
                    default:
                        //do nothing
                }

            }
        }
        if (!container.isEmpty()) {
            map.put(eachPointerEntry.getKey(),container);
        }
    }

    private boolean valid(RelationEntity relation,String name, Category category) {
        if (category != null && !relation.getCategory().equals(category.name())) {
            return false;
        }
        if (StringUtils.isNotEmpty(name) && !relation.getName().equals(name)) {
            return false;
        }
        return true;
    }

    private Map<String,Object> toMap(NodeEntity nodeEntity) {
        Map<String,Object> map = new HashMap<>();
        if (nodeEntity.getProperties() != null) {
            nodeEntity.getProperties().stream().forEach(
                    nodeEntityProperty -> map.put(nodeEntityProperty.getName(), nodeEntityProperty.getValue())
            );
        }
        map.put(Expression.QUALIFIED_NAME,nodeEntity.getQualifiedName());
        map.put(Expression.ID,nodeEntity.getId());
        map.put(Expression.CREATE_TIME,nodeEntity.getCreateTime());
        map.put(Expression.UPDATE_TIME,nodeEntity.getUpdateTime());
        return map;
    }

    private <T> Collection<T> convert(Collection<NodeEntity> nodeEntities,java.lang.Class<T> classInfo,Map<String,EachPointer> eachPointers) {
        return nodeEntities
                .stream()
                .map(nodeEntity -> convert(nodeEntity,classInfo,eachPointers))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private <T> Page<T> convert(Page<NodeEntity> pageResult,java.lang.Class<T> classInfo,Map<String,EachPointer> eachPointers) {
        return new PageImpl<>(pageResult.get()
                .map(nodeEntity -> convert(nodeEntity,classInfo,eachPointers))
                .collect(Collectors.toCollection(ArrayList::new)),pageResult.getPageable(),pageResult.getTotalElements());
    }

    private NodeEntity convert(Long id,TypeCategory category,String name,Map<String,Object> properties) {
        final NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setId(id);
        nodeEntity.setCategory(category.name());
        nodeEntity.setQualifiedName(name);
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

    private <U> Specification<NodeEntity> convertEntity(List<ConditionPiece> conditionPieces) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get(NodeEntity_.CATEGORY),TypeCategory.ENTITY.name()));
            if (conditionPieces != null) {
                for (ConditionPiece filter : conditionPieces) {
                    List<Predicate> piecePredicates = new ArrayList<>();
                    if (filter.getExpressions() != null) {
                        for (BinaryExpression binaryExpression : filter.getExpressions()) {
                            Predicate predicate = handlerExpression(criteriaBuilder, root, binaryExpression);
                            if (predicate != null) {
                                piecePredicates.add(predicate);
                            }
                        }
                    }
                    if (filter.getNextConditionChain() != null) {
                        piecePredicates.addAll(nextConditionHandle(criteriaBuilder, root, query, filter.getNextConditionChain()));
                    }
                    if (piecePredicates.size() != 0) {
                        predicates.add(criteriaBuilder.and(piecePredicates.toArray(new Predicate[piecePredicates.size()])));
                    }
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    private <X,T> Predicate handlerExpression(CriteriaBuilder builder, From<X,T> root, BinaryExpression binaryExpression) {
        if (binaryExpression != null) {
            if (binaryExpression instanceof EqualExpression) {
                EqualExpression expression = (EqualExpression)binaryExpression;
                return builder.equal(root.get(expression.getLeftExpression().toString()), expression.getRightExpression().getValue());
            } else if (binaryExpression instanceof InExpression) {
                InExpression expression = (InExpression)binaryExpression;
                Predicate predicate = root.get(expression.getLeftExpression().toString()).in(expression.getRightExpression().getValue());
                if (expression.isNot()) {
                    predicate = builder.not(predicate);
                }
                return predicate;
            } else if (binaryExpression instanceof LikeExpression) {
                LikeExpression expression = (LikeExpression)binaryExpression;
                if (expression.isNot()) {
                    return builder.notLike(root.get(expression.getLeftExpression().toString()), "%" + expression.getRightExpression().getValue() + "%");
                } else {
                    return builder.like(root.get(expression.getLeftExpression().toString()), "%" + expression.getRightExpression().getValue() + "%");
                }
            } else if (binaryExpression instanceof NullExpression) {
                NullExpression expression = (NullExpression)binaryExpression;
                if (expression.isNot()) {
                    return builder.isNotNull(root.get(expression.getLeftExpression().toString()));
                } else {
                    return builder.isNull(root.get(expression.getLeftExpression().toString()));
                }
            } else if (binaryExpression instanceof EmptyExpression) {
                EmptyExpression expression = (EmptyExpression)binaryExpression;
                if (expression.isNot()) {
                    return builder.isNotEmpty(root.get(expression.getLeftExpression().toString()));
                } else {
                    return builder.isEmpty(root.get(expression.getLeftExpression().toString()));
                }
            } else if (binaryExpression instanceof TrueExpression) {
                TrueExpression expression = (TrueExpression)binaryExpression;
                if (expression.isNot()) {
                    return builder.isFalse(root.get(expression.getLeftExpression().toString()));
                } else {
                    return builder.isTrue(root.get(expression.getLeftExpression().toString()));
                }
            } else if (binaryExpression instanceof BetweenAndExpression) {
                BetweenAndExpression expression = (BetweenAndExpression)binaryExpression;
                return builder.between(root.get(expression.getLeftExpression().toString()),
                        expression.getRightExpression().getLeftExpression().getValue(),
                        expression.getRightExpression().getRightExpression().getValue());
            } else if (binaryExpression instanceof DateBetweenAndExpression) {
                DateBetweenAndExpression expression = (DateBetweenAndExpression)binaryExpression;
                return builder.between(root.get(expression.getLeftExpression().toString()),
                        expression.getRightExpression().getLeftExpression().getValue(),
                        expression.getRightExpression().getRightExpression().getValue());
            } else if (binaryExpression instanceof GreaterThanExpression) {
                GreaterThanExpression expression = (GreaterThanExpression)binaryExpression;
                return builder.greaterThan(root.get(expression.getLeftExpression().toString()),expression.getRightExpression().getValue());
            } else if (binaryExpression instanceof GreaterThanOrEqualToExpression) {
                GreaterThanOrEqualToExpression expression = (GreaterThanOrEqualToExpression)binaryExpression;
                return builder.greaterThanOrEqualTo(root.get(expression.getLeftExpression().toString()),expression.getRightExpression().getValue());
            } else if (binaryExpression instanceof LessThanExpression) {
                LessThanExpression expression = (LessThanExpression)binaryExpression;
                return builder.lessThan(root.get(expression.getLeftExpression().toString()),expression.getRightExpression().getValue());
            } else if (binaryExpression instanceof LessThanOrEqualToExpression) {
                LessThanOrEqualToExpression expression = (LessThanOrEqualToExpression)binaryExpression;
                return builder.lessThanOrEqualTo(root.get(expression.getLeftExpression().toString()),expression.getRightExpression().getValue());
            } else if (binaryExpression instanceof AndExpression) {
                AndExpression expression = (AndExpression)binaryExpression;
                List<Predicate> predicates = new ArrayList<>();
                Predicate predicate = handlerExpression(builder,root,expression.getLeftExpression());
                if (predicate != null) {
                    predicates.add(predicate);
                }
                if (expression.getRightExpression() != null && expression.getRightExpression().getExpressionList() != null) {
                    for (BinaryExpression expressionPiece : expression.getRightExpression().getExpressionList()) {
                        Predicate temp = handlerExpression(builder,root,expressionPiece);
                        if (temp != null) {
                            predicates.add(temp);
                        }
                    }
                }
                if (predicates.size() > 0) {
                    return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            } else if (binaryExpression instanceof OrExpression) {
                OrExpression expression = (OrExpression)binaryExpression;
                List<Predicate> predicates = new ArrayList<>();
                Predicate predicate = handlerExpression(builder,root,expression.getLeftExpression());
                if (predicate != null) {
                    predicates.add(predicate);
                }
                if (expression.getRightExpression() != null && expression.getRightExpression().getExpressionList() != null) {
                    for (BinaryExpression expressionPiece : expression.getRightExpression().getExpressionList()) {
                        Predicate temp = handlerExpression(builder,root,expressionPiece);
                        if (temp != null) {
                            predicates.add(temp);
                        }
                    }
                }
                if (predicates.size() > 0) {
                    return builder.or(predicates.toArray(new Predicate[predicates.size()]));
                }
            }
        }
        return null;
    }

    private <T> List<Predicate> nextConditionHandle(CriteriaBuilder criteriaBuilder, Root<T> root, CriteriaQuery<?> query,Map<String,ConditionPiece> conditionPieceMap) {
        List<Predicate> predicates = new ArrayList<>();
        if (conditionPieceMap != null) {
            for (Map.Entry<String, ConditionPiece> entry : conditionPieceMap.entrySet()) {
                String propertyName = entry.getKey();
                ConditionPiece nextConditionPiece = entry.getValue();
                switch (nextConditionPiece.getTableType()) {
                    case ENTITY:
                        Join<T, NodeEntity> join1 = root.join(propertyName);
                        List<BinaryExpression> expressions1 = nextConditionPiece.getExpressions();
                        if (expressions1 != null) {
                            for (BinaryExpression expression : expressions1) {
                                predicates.add(handlerExpression(criteriaBuilder,join1,expression));
                            }
                        }
                        predicates.addAll(nextConditionHandle(criteriaBuilder,join1,query,nextConditionPiece.getNextConditionChain()));
                        break;
                    case RELATION:
                        Join<T, RelationEntity> join2 = root.join(propertyName);
                        List<BinaryExpression> expressions2 = nextConditionPiece.getExpressions();
                        if (expressions2 != null) {
                            for (BinaryExpression expression : expressions2) {
                                predicates.add(handlerExpression(criteriaBuilder,join2,expression));
                            }
                        }
                        predicates.addAll(nextConditionHandle(criteriaBuilder,join2,query,nextConditionPiece.getNextConditionChain()));
                        break;
                    case ENTITY_PROPERTY:
                        List<BinaryExpression> expressions3 = nextConditionPiece.getExpressions();
                        if (expressions3 != null) {
                            for (BinaryExpression expression : expressions3) {
                                Join<T, NodeEntityProperty> join3 = root.join(propertyName);
                                predicates.add(handlerExpression(criteriaBuilder,join3,expression));
                                predicates.addAll(nextConditionHandle(criteriaBuilder,join3,query,nextConditionPiece.getNextConditionChain()));
                            }
                        }
                        break;
                    default:
                }
            }
        }
        return predicates;
    }

    private <X,Y> List<Predicate> nextConditionHandle(CriteriaBuilder criteriaBuilder, Join<X,Y> join, CriteriaQuery<?> query,Map<String,ConditionPiece> conditionPieceMap) {
        List<Predicate> predicates = new ArrayList<>();
        if (conditionPieceMap != null) {
            for (Map.Entry<String, ConditionPiece> entry : conditionPieceMap.entrySet()) {
                String propertyName = entry.getKey();
                ConditionPiece nextConditionPiece = entry.getValue();
                switch (nextConditionPiece.getTableType()) {
                    case ENTITY:
                        Join<Y, NodeEntity> join1 = join.join(propertyName);
                        List<BinaryExpression> expressions1 = nextConditionPiece.getExpressions();
                        if (expressions1 != null) {
                            for (BinaryExpression expression : expressions1) {
                                predicates.add(criteriaBuilder.equal(join1.get(expression.getLeftExpression().toString()), expression.getRightExpression().toString()));
                            }
                        }
                        predicates.addAll(nextConditionHandle(criteriaBuilder,join1,query,nextConditionPiece.getNextConditionChain()));
                        break;
                    case RELATION:
                        Join<Y, RelationEntity> join2 = join.join(propertyName);
                        List<BinaryExpression> expressions2 = nextConditionPiece.getExpressions();
                        if (expressions2 != null) {
                            for (BinaryExpression expression : expressions2) {
                                predicates.add(criteriaBuilder.equal(join2.get(expression.getLeftExpression().toString()), expression.getRightExpression().toString()));
                            }
                        }
                        predicates.addAll(nextConditionHandle(criteriaBuilder,join2,query,nextConditionPiece.getNextConditionChain()));
                        break;
                    case ENTITY_PROPERTY:
                        Join<Y, NodeEntityProperty> join3 = join.join(propertyName);
                        List<BinaryExpression> expressions3 = nextConditionPiece.getExpressions();
                        if (expressions3 != null) {
                            for (BinaryExpression expression : expressions3) {
                                predicates.add(criteriaBuilder.equal(join3.get(expression.getLeftExpression().toString()), expression.getRightExpression().toString()));
                            }
                        }
                        predicates.addAll(nextConditionHandle(criteriaBuilder,join3,query,nextConditionPiece.getNextConditionChain()));
                        break;
                    default:
                }
            }
        }
        return predicates;
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
                RelationEntity relation = relationEntityMapper.findByStartNodeEntityAndEndNodeEntityAndCategoryAndName(head, tail, linkCategory.name(), property);
                if (relation == null) {
                    RelationEntity relationEntity = new RelationEntity();
                    relationEntity.setCategory(linkCategory.name());
                    relationEntity.setStartNodeEntity(head);
                    relationEntity.setEndNodeEntity(tail);
                    relationEntity.setName(property);
                    relationEntityMapper.create(relationEntity);
                }
            }

            @Override
            public void crack(Long headId, Long tailId,LinkCategory linkCategory, String property) throws StarFishMetaDataOperatingException {
                NodeEntity head = nodeEntityMapper.findById(headId);
                NodeEntity tail = nodeEntityMapper.findById(tailId);
                RelationEntity relation = relationEntityMapper.findByStartNodeEntityAndEndNodeEntityAndCategoryAndName(head, tail, linkCategory.name(), property);
                if (relation != null) {
                    relationEntityMapper.remove(relation);
                }
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
                NodeEntity nodeEntity = nodeEntityMapper.findByCategoryAndQualifiedName(TypeCategory.SOURCE.name(), name);
                if (Objects.isNull(nodeEntity)) {
                    return null;
                }
                return nodeEntity.getId();
            }

            @Override
            public <U> U source(Long sourceId, java.lang.Class<U> returnType) {
                return convert(nodeEntityMapper.findById(sourceId),returnType,null);
            }

            @Override
            public <T> Collection<T> query(AbstractQueryCondition<T> condition) {
                return convert(nodeEntityMapper.findByCategory(TypeCategory.SOURCE.name()),condition.getResultType(),condition.getEachPointers());
            }

            @Override
            public <T> Page<T> query(AbstractQueryCondition<T> condition, Pageable pageable) {
                return convert(nodeEntityMapper.findByCategory(TypeCategory.SOURCE.name(),pageable),condition.getResultType(),condition.getEachPointers());
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
                return convert(nodeEntityMapper.findByCategory(TypeCategory.CLASSIFICATION.name()), condition.getResultType(),condition.getEachPointers());
            }

            @Override
            public <T> Page<T> query(AbstractQueryCondition<T> condition, Pageable pageable) {
                return convert(nodeEntityMapper.findByCategory(TypeCategory.CLASSIFICATION.name(),pageable), condition.getResultType(),condition.getEachPointers());
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
                NodeEntity nodeEntity = nodeEntityMapper.findByCategoryAndQualifiedName(TypeCategory.CLASSIFICATION.name(), name);
                if (Objects.isNull(nodeEntity)) {
                    return null;
                }
                return nodeEntity.getId();
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
            public <U> U node(Long nodeId, java.lang.Class<U> returnType) throws AbstractMetaParserException {
                return convert(nodeEntityMapper.findById(nodeId),returnType,null);
            }

            @Override
            public <U> Collection<U> nodes(Collection<Long> nodeIds, java.lang.Class<U> returnType) throws AbstractMetaParserException {
                List<NodeEntity> nodeEntities = nodeEntityMapper.findAllById(nodeIds);
                return convert(nodeEntities,returnType,null);
            }

            @Override
            public <U> Page<U> nodes(Collection<Long> nodeIds, Pageable page, java.lang.Class<U> returnType) throws AbstractMetaParserException {
                Page<NodeEntity> pageResult = nodeEntityMapper.findAllById(nodeIds, page);
                return convert(pageResult,returnType,null);
            }

            @Override
            public <U> Collection<U> query(AbstractQueryCondition<U> condition) {
                Specification<NodeEntity> specification = convertEntity(condition.getFilters());
                List<NodeEntity> all = nodeEntityMapper.findAll(specification);
                return convert(all,condition.getResultType(),condition.getEachPointers());
            }

            @Override
            public <T> Page<T> query(AbstractQueryCondition<T> condition, Pageable pageable) {
                return convert(nodeEntityMapper.findByCategory(TypeCategory.ENTITY.name(),pageable),condition.getResultType(),condition.getEachPointers());
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

            @Override
            public <U> Collection<U> query(AbstractQueryCondition<U> condition) {
                Specification<NodeEntity> specification = convertEntity(condition.getFilters());
                List<NodeEntity> all = nodeEntityMapper.findAll(specification);
                return convert(all,condition.getResultType(),condition.getEachPointers());
            }

            @Override
            public <T> Page<T> query(AbstractQueryCondition<T> condition, Pageable pageable) {
                Specification<NodeEntity> specification = convertEntity(condition.getFilters());
                Page<NodeEntity> all = nodeEntityMapper.findAll(specification,pageable);
                return convert(all,condition.getResultType(),condition.getEachPointers());
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
