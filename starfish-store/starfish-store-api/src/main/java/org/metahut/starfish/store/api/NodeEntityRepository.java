package org.metahut.starfish.store.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.metahut.starfish.store.model.EntityProperty;
import org.metahut.starfish.store.model.NodeEntity;

public interface NodeEntityRepository<ID extends Serializable,T> {

    /**
     *
     * @param entity
     * @return the number of affected records
     */
    int create(NodeEntity<ID, T> entity);

    /**
     *
     * @param entities
     * @return the number of affected records
     */
    int createBatch(Collection<NodeEntity<ID, T>> entities);

    /**
     * @param entity
     * @return the number of affected records
     */
    int remove(NodeEntity<ID, T> entity);

    /**
     * @param entities
     * @return the number of affected records
     */
    int removeBatchById(Collection<NodeEntity<ID, T>> entities);

    /**
     * @param ids
     * @return the number of affected records
     */
    int removeBatchById(Collection<ID> ids);

    /**
     *
     * @param category
     * @return
     */
    int removeAllByCategory(String category);

    /**
     *
     * @param entity
     * @return
     */
    int removeAllByCategory(NodeEntity<ID, T> entity);

    /**
     * @param entity
     * @return the number of affected records
     */
    int update(NodeEntity<ID, T> entity);

    /**
     * @param entities
     * @return the number of affected records
     */
    int updateBatchById(Collection<NodeEntity<ID, T>> entities);

    /**
     *
     * @param entityId
     * @param property
     * @return the instance of a node entity with all properties
     */
    NodeEntity<ID, T> updatePropertyByEntityId(ID entityId, EntityProperty<ID, T> property);

    /**
     *
     * @param entity
     * @param property
     * @return the instance of a node entity with all properties
     */
    NodeEntity<ID, T> updatePropertyByEntityId(NodeEntity<ID, T> entity, EntityProperty<ID, T> property);

    /**
     *
     * @param entity
     * @param property
     * @return the instance of a node entity with all properties
     */
    NodeEntity<ID, T> updatePropertiesByEntityId(NodeEntity<ID, T> entity, Collection<EntityProperty<ID, T>> property);

    /**
     *
     * @param entity
     * @param property
     * @return the instance of a node entity with all properties
     */
    NodeEntity<ID, T> removePropertyByEntityId(NodeEntity<ID, T> entity, Collection<EntityProperty<ID, T>> property);

    /**
     *
     * @param entityId
     * @param property
     * @return the instance of a node entity with all properties
     */
    NodeEntity<ID, T> removePropertyByEntityId(ID entityId, Collection<EntityProperty<ID, T>> property);

    /**
     *
     * @param entityId
     * @param property
     * @return
     */
    NodeEntity<ID, T> removeAllPropertiesByEntityId(ID entityId, Collection<EntityProperty<ID, T>> property);

    /**
     *
     * @param entity
     * @param property
     * @return
     */
    NodeEntity<ID, T> removeAllPropertiesByEntityId(NodeEntity<ID, T> entity, Collection<EntityProperty<ID, T>> property);

    /**
     *
     * @param id
     * @return the instance of a node entity with all properties
     */
    NodeEntity<ID, T> findById(ID id);

    /**
     *
     * @param entity
     * @return the instance of a node entity with all properties
     */
    NodeEntity<ID, T> findById(NodeEntity<ID, T> entity);

    /**
     *
     * @param category
     * @param propertyName
     * @param propertyValue
     * @return
     */
    List<NodeEntity<ID, T>> findByProperty(String category, String propertyName, String propertyValue);


//    List<NodeEntity<ID, T>> findByProperties(QueryConditionWrapper<NodeEntity<ID, T>> wrapper);

}
