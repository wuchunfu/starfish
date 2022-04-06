package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.instance.Node;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;
import org.metahut.starfish.parser.function.AbstractInstanceService;
import org.metahut.starfish.parser.function.AbstractNodeService;
import org.metahut.starfish.parser.function.AbstractPropertyService;
import org.metahut.starfish.store.INodeRepository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class Node1ServiceImpl<E,K,T>  extends AbstractNodeService<E,K,T> {

    @Resource
    private INodeRepository<E,K,T> nodeRepository;

    @Override
    protected AbstractInstanceService<E, K, T> getInstanceService() {
        return null;
    }

    @Override
    protected AbstractPropertyService<E,K, T> getPropertyService() {
        return null;
    }

    @Override
    public Map<K, Node<K, T>> nodes(E env) throws StarFishMetaDataQueryException {
        Map<K, Map<String, T>> nodes = nodeRepository.nodes(env);
        Map<K, Node<K, T>> map = new HashMap<>(nodes.size());
        nodes.forEach((key,value) -> {
            Node<K,T> node = new Node<>();
            node.setInstanceId(key);
            node.setValues(value);
            map.put(key,node);
        });
        return map;
    }

    @Override
    public K create(E env, Map<String, T> attributes) throws StarFishMetaDataOperatingException {
        return super.create(env, attributes);
    }

    @Override
    public void add(E env, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException {
        super.add(env, instanceId, property, obj);
    }

    @Override
    public void update(E env, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException {
        super.update(env, instanceId, property, obj);
    }

    @Override
    public void update(E env, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException {
        super.update(env, instanceId, attributes);
    }

    @Override
    public void modify(E env, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException {
        super.modify(env, instanceId, attributes);
    }

    @Override
    public K copy(E env, K instanceId) throws StarFishMetaDataOperatingException {
        return super.copy(env, instanceId);
    }

    @Override
    public void copy(E toEnv, E fromEnv, K... instanceIds) throws StarFishMetaDataOperatingException {
        super.copy(toEnv, fromEnv, instanceIds);
    }

    @Override
    public void move(E env, K oldInstanceId, K newInstanceId, String property) throws StarFishMetaDataOperatingException {
        super.move(env, oldInstanceId, newInstanceId, property);
    }

    @Override
    public void delete(E env) throws StarFishMetaDataOperatingException {
        super.delete(env);
    }

    @Override
    public void delete(E env, K instanceId) throws StarFishMetaDataOperatingException {
        super.delete(env, instanceId);
    }

    @Override
    public void delete(E env, K instanceId, String property) throws StarFishMetaDataOperatingException {
        super.delete(env, instanceId, property);
    }

    @Override
    public void delete(E env, K... instanceIds) throws StarFishMetaDataOperatingException {
        super.delete(env, instanceIds);
    }
}
