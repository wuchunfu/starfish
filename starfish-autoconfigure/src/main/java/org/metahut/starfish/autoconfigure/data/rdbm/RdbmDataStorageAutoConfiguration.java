package org.metahut.starfish.autoconfigure.data.rdbm;

import org.metahut.starfish.parser.exception.AbstractMetaParserException;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
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
import org.metahut.starfish.store.rdbms.entity.NodeEntity;
import org.metahut.starfish.store.rdbms.entity.NodeEntityProperty;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ DataSource.class})
@AutoConfigureAfter({ DataSourceAutoConfiguration.class })
public class RdbmDataStorageAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(AbstractRelationService.class)
    public AbstractRelationService relationService() {
        return new AbstractRelationService() {
            @Override
            public Collection query(AbstractQueryCondition condition) {
                return null;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractTypeService.class)
    public AbstractTypeService typeService() {
        return new AbstractTypeService() {};
    }

    @Bean
    @ConditionalOnMissingBean(AbstractTypeInstanceBridgeService.class)
    public AbstractTypeInstanceBridgeService typeInstanceBridgeService() {
        return new AbstractTypeInstanceBridgeService() {};
    }

    @Bean
    @ConditionalOnMissingBean(AbstractNodeService.class)
    public AbstractNodeService<String,Long,Object> nodeService(NodeEntityMapper nodeEntityMapper) {
        return new AbstractNodeService<String,Long,Object>() {
            @Override
            protected AbstractInstanceService getInstanceService() {
                return null;
            }

            @Override
            protected AbstractPropertyService getPropertyService() {
                return null;
            }

            @Override
            public Long create(String typeName, Map<String, Object> attributes) throws StarFishMetaDataOperatingException {
                final NodeEntity nodeEntity = new NodeEntity();
                nodeEntity.setCategories(Stream.of(typeName).collect(Collectors.toSet()));
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
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractGraphService.class)
    public AbstractGraphService graphService(AbstractNodeService nodeService,AbstractRelationService relationService) {
        return new AbstractGraphService() {
            @Override
            protected AbstractNodeService getNodeService() {
                return nodeService;
            }

            @Override
            protected AbstractRelationService getRelationService() {
                return relationService;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(AbstractMetaDataService.class)
    public AbstractMetaDataService<String,Long,Object> metaDataService(
            AbstractGraphService abstractGraphService,
            AbstractTypeService abstractTypeServce,
            AbstractTypeInstanceBridgeService abstractTypeInstanceBridgeService) {
        return new AbstractMetaDataService() {
            @Override
            protected AbstractGraphService graphApi() {
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
