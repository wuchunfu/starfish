package org.metahut.starfish.autoconfigure.data.rdbm;

import org.metahut.starfish.parser.domain.enums.Type;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;
import org.metahut.starfish.service.AbstractGraphService;
import org.metahut.starfish.service.AbstractInstanceService;
import org.metahut.starfish.service.AbstractMetaDataService;
import org.metahut.starfish.service.AbstractNodeService;
import org.metahut.starfish.service.AbstractPropertyService;
import org.metahut.starfish.service.AbstractQueryCondition;
import org.metahut.starfish.service.AbstractRelationService;
import org.metahut.starfish.service.AbstractTypeInstanceBridgeService;
import org.metahut.starfish.service.AbstractTypeService;
import org.metahut.starfish.store.rdbms.dao.NodeEntityMapper;
import org.metahut.starfish.store.rdbms.dao.RelationEntityMapper;
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.entity.NodeEntityProperty;
import org.metahut.starfish.store.rdbms.entity.RelationEntity;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
@Configuration
@ConditionalOnClass({DataSource.class})
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class RdbmDataStorageAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(AbstractInstanceService.class)
    public AbstractInstanceService<String, Long, Object> instanceService() {
        return new AbstractInstanceService<String, Long, Object>() {
            @Override
            public Set<Long> instanceMap(String typeName) throws StarFishMetaDataQueryException {
                return null;
            }

            @Override
            public void valid(String typeName, Collection<Long> instanceIds) throws StarFishMetaDataOperatingException {
                return;
            }

            @Override
            public Long create(String typeName) throws StarFishMetaDataOperatingException {
                return null;
            }

            @Override
            public void copy(String oldtypeName, String newtypeName, boolean deleteOld) throws StarFishMetaDataOperatingException {

            }

            @Override
            public void delete(String typeName) throws StarFishMetaDataOperatingException {

            }

            @Override
            public void delete(String typeName, Long instanceId) throws StarFishMetaDataOperatingException {

            }

            @Override
            public void delete(String typeName, Collection<Long> instanceIds) throws StarFishMetaDataOperatingException {

            }

            @Override
            public Collection<Object> query(AbstractQueryCondition condition) {
                return null;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractRelationService.class)
    public AbstractRelationService<String, Long, Object> relationService(NodeEntityMapper nodeEntityMapper, RelationEntityMapper relationEntityMapper) {
        return new AbstractRelationService<String, Long, Object>() {
            @Override
            public Collection query(AbstractQueryCondition condition) {
                return null;
            }

            @Override
            public void link(String typeName, Long headId, Long tailId, String property) throws StarFishMetaDataOperatingException {
                NodeEntity head = nodeEntityMapper.findById(headId);
                NodeEntity tail = nodeEntityMapper.findById(tailId);
                RelationEntity relationEntity = new RelationEntity();
                relationEntity.setCategory(typeName);
                relationEntity.setStartNodeEntity(head);
                relationEntity.setEndNodeEntity(tail);
                relationEntity.setName(property);
                relationEntityMapper.create(relationEntity);
            }

            @Override
            public void delete(String typeName, Long instanceId) throws StarFishMetaDataOperatingException {
                relationEntityMapper.removeBatchById(Arrays.asList(instanceId));
            }

            @Override
            public void delete(String typeName, Collection<Long> instanceIds) throws StarFishMetaDataOperatingException {
                relationEntityMapper.removeBatchById(instanceIds);
            }

            @Override
            public void move(String typeName, Long oldHeadId, Long newHeadId, Long tailId, String property) throws StarFishMetaDataOperatingException {

            }

            @Override
            public void copy(String oldTypeName, String newTypeName, Collection<Long> instanceIds) throws StarFishMetaDataOperatingException {

            }

            @Override
            public void crack(String typeName, Long headId, Long tailId, String property) throws StarFishMetaDataOperatingException {
                RelationEntity relationEntity = new RelationEntity();
                relationEntity.setName(property);
                relationEntity.setCategory(typeName);
                NodeEntity head = new NodeEntity();
                head.setId(headId);
                relationEntity.setStartNodeEntity(head);
                NodeEntity tail = new NodeEntity();
                tail.setId(tailId);
                relationEntity.setEndNodeEntity(tail);
                relationEntityMapper.remove(relationEntity);
            }

            @Override
            public void delete(String typeName) throws StarFishMetaDataOperatingException {

            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractTypeService.class)
    public AbstractTypeService typeService() {
        return new AbstractTypeService() {
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractTypeInstanceBridgeService.class)
    public AbstractTypeInstanceBridgeService typeInstanceBridgeService() {
        return new AbstractTypeInstanceBridgeService() {
            @Override
            public Set<Type> query(Object env) {
                return new HashSet<>();
            }

            @Override
            public Type query(Object env, Object instanceId) {
                return new Type();
            }

            @Override
            public Set<Type> query(Object env, Object[] instanceId) {
                return new HashSet<>();
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractNodeService.class)
    public AbstractNodeService<String, Long, Object> nodeService(NodeEntityMapper nodeEntityMapper, AbstractInstanceService<String, Long, Object> instanceService) {
        return new AbstractNodeService<String, Long, Object>() {
            @Override
            protected AbstractInstanceService<String, Long, Object> getInstanceService() {
                return instanceService;
            }

            @Override
            protected AbstractPropertyService getPropertyService() {
                return null;
            }

            @Override
            public Long create(String typeName, Map<String, Object> attributes) throws StarFishMetaDataOperatingException {
                final NodeEntity nodeEntity = new NodeEntity();
                nodeEntity.setCategory(typeName);
                nodeEntity.setName(String.valueOf(attributes.get("name")));
                nodeEntity.setProperties(attributes
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
                return nodeEntityMapper.create(nodeEntity).getId();
            }

            @Override
            public void update(String typeName, Long entityId, Map<String, Object> attributes) throws StarFishMetaDataOperatingException {
                final NodeEntity nodeEntity = new NodeEntity();
                nodeEntity.setId(entityId);
                nodeEntity.setCategory(typeName);
                nodeEntity.setName(String.valueOf(attributes.get("name")));
                nodeEntity.setProperties(attributes
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
                nodeEntityMapper.update(nodeEntity);
            }

            @Override
            public void delete(String typeName, Long entityId) throws StarFishMetaDataOperatingException {
                nodeEntityMapper.removeBatchById(Arrays.asList(entityId));
            }

            @Override
            public void delete(String typeName, Collection<Long> entityIds) throws StarFishMetaDataOperatingException {
                nodeEntityMapper.removeBatchById(entityIds);
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractGraphService.class)
    public AbstractGraphService<String,Long,Object> graphService(AbstractNodeService<String,Long,Object> nodeService,AbstractRelationService<String,Long,Object> relationService) {
        return new AbstractGraphService<String,Long,Object>() {
            @Override
            protected AbstractNodeService<String, Long, Object> getNodeService() {
                return nodeService;
            }

            @Override
            protected AbstractRelationService<String, Long, Object> getRelationService() {
                return relationService;
            }

            @Override
            public void delete(String typeName, Collection<Long> instanceIds) throws StarFishMetaDataOperatingException {
                getNodeService().delete(typeName,instanceIds);
            }

            @Override
            public void delete(String typeName) throws StarFishMetaDataOperatingException {
                getNodeService().delete(typeName);
            }

            @Override
            public void delete(String typeName, Long instanceId) throws StarFishMetaDataOperatingException {
                getNodeService().delete(typeName,instanceId);
            }

            @Override
            public void delete(String typeName, Long id, String property) throws StarFishMetaDataOperatingException {
                getNodeService().delete(typeName,id,property);
            }

        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractMetaDataService.class)
    public AbstractMetaDataService<String, Long, Object> metaDataService(
            AbstractGraphService<String, Long, Object> abstractGraphService,
            AbstractTypeService abstractTypeServce,
            AbstractTypeInstanceBridgeService abstractTypeInstanceBridgeService) {
        return new AbstractMetaDataService() {
            @Override
            protected AbstractGraphService<String, Long, Object> graphApi() {
                return abstractGraphService;
            }

            @Override
            protected AbstractTypeService classApi() {
                return abstractTypeServce;
            }

            @Override
            protected AbstractTypeInstanceBridgeService classInstanceBridgeApi() {
                return abstractTypeInstanceBridgeService;
            }

            @Override
            public void move(Object toTypeName, Object fromTypeName, long... classIds) throws AbstractMetaParserException {

            }

            @Override
            public void move(Object toTypeName, Object fromTypeName, Object[] instanceIds) throws AbstractMetaParserException {

            }
        };
    }
}
