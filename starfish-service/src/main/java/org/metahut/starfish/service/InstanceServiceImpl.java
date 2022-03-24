package org.metahut.starfish.service;

import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;
import org.metahut.starfish.parser.function.AbstractInstanceService;
import org.metahut.starfish.parser.function.AbstractQueryCondition;
import org.metahut.starfish.store.IEntityRepository;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 *
 */
public class InstanceServiceImpl<E,K,T> extends AbstractInstanceService<E,K,T> {

    @Resource
    private IEntityRepository<E,K,T> entityService;

    @Override
    public Set<K> instanceMap(E env) throws StarFishMetaDataQueryException {
        return entityService.instanceMap(env);
    }

    @Override
    public void valid(E env, K... instanceIds) throws StarFishMetaDataOperatingException {
        entityService.valid(env,instanceIds);
    }

    @Override
    public K create(E env) throws StarFishMetaDataOperatingException {
        return entityService.create(env);
    }

    @Override
    public void copy(E oldEnv, E newEnv, boolean deleteOld) throws StarFishMetaDataOperatingException {
        entityService.copy(oldEnv,newEnv,deleteOld);
    }

    @Override
    public void delete(E env) throws StarFishMetaDataOperatingException {
        entityService.delete(env);
    }

    @Override
    public void delete(E env, K instanceId) throws StarFishMetaDataOperatingException {
        entityService.delete(env,instanceId);
    }

    @Override
    public void delete(E env, K... instanceIds) throws StarFishMetaDataOperatingException {
        entityService.delete(env,instanceIds);
    }

    @Override
    //TODO
    public Collection<T> query(AbstractQueryCondition condition) {
        return null;
    }
}
