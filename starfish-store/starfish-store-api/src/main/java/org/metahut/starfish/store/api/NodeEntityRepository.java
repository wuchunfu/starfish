package org.metahut.starfish.store.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.metahut.starfish.store.model.NodeEntity;

public interface NodeEntityRepository<ID extends Serializable> {

    /**
     *
     * @param entity
     * @return the number of affected records
     */
    int insert(NodeEntity<ID> entity);

    /**
     *
     * @param entities
     * @return the number of affected records
     */
    int insertBatch(Collection<NodeEntity<ID>> entities);

    /**
     * @param entity
     * @return the number of affected records
     */
    int delete(NodeEntity<ID> entity);

    /**
     * @param entities
     * @return the number of affected records
     */
    int deleteBatchById(Collection<NodeEntity<ID>> entities);

    /**
     * @param ids
     * @return the number of affected records
     */
    int deleteBatchById(Collection<ID> ids);

    /**
     * @param entity
     * @return the number of affected records
     */
    int update(NodeEntity<ID> entity);

    /**
     * @param entities
     * @return the number of affected records
     */
    int updateBatchById(Collection<NodeEntity<ID>> entities);

    /**
     *
     * @param id
     * @return the instance of a NodeEntity
     */
    NodeEntity<ID> findById(ID id);

    /**
     *
     * @param entity
     * @return the instance of a NodeEntity
     */
    NodeEntity<ID> findById(NodeEntity<ID> entity);

    /**
     *
     * @param category
     * @param propertyName
     * @param propertyValue
     * @return
     */
    List<NodeEntity<ID>> findByProperty(String category, String propertyName, String propertyValue);


    List<NodeEntity<ID>> findByProperties(QueryConditionWrapper<NodeEntity<ID>> wrapper);

}
