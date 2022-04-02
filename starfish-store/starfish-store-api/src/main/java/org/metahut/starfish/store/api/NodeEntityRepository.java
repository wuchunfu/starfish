package org.metahut.starfish.store.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.metahut.starfish.store.model.AbstractEntityProperty;
import org.metahut.starfish.store.model.AbstractNodeEntity;

public interface NodeEntityRepository<ID extends Serializable, P extends AbstractEntityProperty> {

    /**
     *
     * @param entity
     * @return the number of affected records
     */
    int create(AbstractNodeEntity<ID, P> entity);

    /**
     *
     * @param entities
     * @return the number of affected records
     */
    int createBatch(Collection<AbstractNodeEntity<ID, P>> entities);

    /**
     * @param entity
     * @return the number of affected records
     */
    int remove(AbstractNodeEntity<ID, P> entity);

    /**
     * @param entities
     * @return the number of affected records
     */
    int removeBatch(Collection<AbstractNodeEntity<ID, P>> entities);

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
    int removeAllByCategory(AbstractNodeEntity<ID, P> entity);

    /**
     * @param entity
     * @return the number of affected records
     */
    int update(AbstractNodeEntity<ID, P> entity);

    /**
     * @param entities
     * @return the number of affected records
     */
    int updateBatchById(Collection<AbstractNodeEntity<ID, P>> entities);

    /**
     *
     * @param entityId
     * @param property
     * @return the instance of a node entity with all properties
     */
    AbstractNodeEntity<ID, P> updatePropertyByEntityId(ID entityId, P property);

    /**
     *
     * @param entity
     * @param property
     * @return the instance of a node entity with all properties
     */
    AbstractNodeEntity<ID, P> updatePropertyByEntityId(AbstractNodeEntity<ID, P> entity, P property);

    /**
     *
     * @param entity
     * @param property
     * @return the instance of a node entity with all properties
     */
    AbstractNodeEntity<ID, P> updatePropertiesByEntityId(AbstractNodeEntity<ID, P> entity, Collection<P> property);

    /**
     *
     * @param entity
     * @param property
     * @return the instance of a node entity with all properties
     */
    AbstractNodeEntity<ID, P> removePropertyByEntityId(AbstractNodeEntity<ID, P> entity, Collection<P> property);

    /**
     *
     * @param entityId
     * @param property
     * @return the instance of a node entity with all properties
     */
    AbstractNodeEntity<ID, P> removePropertyByEntityId(ID entityId, Collection<P> property);

    /**
     *
     * @param entityId
     * @param property
     * @return
     */
    AbstractNodeEntity<ID, P> removeAllPropertiesByEntityId(ID entityId, Collection<P> property);

    /**
     *
     * @param entity
     * @param property
     * @return
     */
    AbstractNodeEntity<ID, P> removeAllPropertiesByEntityId(AbstractNodeEntity<ID, P> entity, Collection<P> property);

    /**
     *
     * @param id
     * @return the instance of a node entity with all properties
     */
    AbstractNodeEntity<ID, P> findById(ID id);

    /**
     *
     * @param entity
     * @return the instance of a node entity with all properties
     */
    AbstractNodeEntity<ID, P> findById(AbstractNodeEntity<ID, P> entity);

    /**
     *
     * @param category
     * @param propertyName
     * @param propertyValue
     * @return
     */
    List<AbstractNodeEntity<ID, P>> findByProperty(String category, String propertyName, String propertyValue);


//    List<AbstractNodeEntity<ID, P>> findByProperties(QueryConditionWrapper<AbstractNodeEntity<ID, P>> wrapper);

}
