package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.instance.Graph;
import org.metahut.starfish.parser.domain.instance.Node;
import org.metahut.starfish.parser.domain.instance.Relation;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 *
 */
public abstract class AbstractGraphService<K,T> implements IGraphApi<K, T> {

    protected abstract AbstractNodeService<K,T> nodeService();

    protected abstract AbstractRelationService<K,T> relationService();

    public Graph<K,T> union(Map<K, Node<K,T>> nodes, List<Relation<K>> lines) {
        return new Graph<>(nodes,lines);
    }

    @Override
    public Collection<T> query(AbstractQueryCondition condition) {
        return merge(nodeService().query(condition), relationService().query(condition));
    }

    @Override
    public Future<Collection<T>> query(Supplier<AbstractQueryCondition> condition) {
        return new FakeFuture<>(merge(nodeService().query(condition), relationService().query(condition)));
    }

    @Override
    public K createNode(String name, Map<String, T> properties) throws AbstractMetaParserException {
        return nodeService().create(name,properties);
    }

    @Override
    public void updateNode(K id,String name, Map<String, T> properties) throws AbstractMetaParserException {
        nodeService().update(id,name,properties);
    }

    @Override
    public void deleteNode(K id) throws AbstractMetaParserException {
        nodeService().delete(id);
        relationService().deleteRelationRelatedToIds(Arrays.asList(id));
    }

    @Override
    public void deleteNodes(Collection<K> ids) throws AbstractMetaParserException {
        nodeService().delete(ids);
        relationService().deleteRelationRelatedToIds(ids);
    }

    @Override
    public void link(K headId, K tailId, String property) throws AbstractMetaParserException {
        relationService().link(headId,tailId,property);
    }

    @Override
    public void crack(K headId, K tailId, String property) throws AbstractMetaParserException {
        relationService().crack(headId,tailId,property);
    }

    @Override
    public void deleteRelation(K id) throws AbstractMetaParserException {
        relationService().delete(id);
    }

    @Override
    public void deleteRelationByHeadId(K headId) throws AbstractMetaParserException {
        relationService().deleteByHeadId(headId);
    }

    @Override
    public void deleteRelationByTailId(K tailId) throws AbstractMetaParserException {
        relationService().deleteByTailId(tailId);
    }

    @Override
    public void deleteRelationRelatedToIds(Collection<K> ids) throws AbstractMetaParserException {
        relationService().deleteRelationRelatedToIds(ids);
    }
}
