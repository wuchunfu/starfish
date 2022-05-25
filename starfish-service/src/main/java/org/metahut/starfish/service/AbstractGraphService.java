package org.metahut.starfish.service;

import org.metahut.starfish.parser.domain.enums.LinkCategory;
import org.metahut.starfish.parser.domain.instance.Graph;
import org.metahut.starfish.parser.domain.instance.Node;
import org.metahut.starfish.parser.domain.instance.Relation;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 *
 */
public abstract class AbstractGraphService<K,T> implements IGraphApi<K,T> {

    protected abstract AbstractNodeService<K,T> nodeService();

    protected abstract AbstractRelationService<K,T> relationService();

    public Graph<K,T> union(Map<K, Node<K,T>> nodes, List<Relation<K>> lines) {
        return new Graph<>(nodes,lines);
    }

    @Override
    public <U> Collection<U> query(AbstractQueryCondition<U> condition) {
        return merge(nodeService().query(condition), relationService().query(condition));
    }

    @Override
    public <T> Page<T> query(AbstractQueryCondition<T> condition, Pageable pageable) {
        //TODO page?
        return null;
    }

    @Override
    public <U> U node(K instanceId,Class<U> returnType) throws AbstractMetaParserException {
        return nodeService().node(instanceId,returnType);
    }

    @Override
    public <U> Collection<U> nodes(Collection<K> instanceIds,Class<U> returnType) throws AbstractMetaParserException {
        return nodeService().nodes(instanceIds,returnType);
    }

    @Override
    public <U> Collection<U> nodes(K upperInstanceId, String property,Class<U> returnType) throws AbstractMetaParserException {
        Collection<K> instanceIds = relationService().findChildren(upperInstanceId, LinkCategory.RELATIONSHIP, property);
        return nodeService().nodes(instanceIds,returnType);
    }

    @Override
    public <U> Page<U> nodes(Collection<K> instanceIds, Pageable page,Class<U> returnType) throws AbstractMetaParserException {
        return nodeService().nodes(instanceIds,page,returnType);
    }

    @Override
    public <U> Page<U> nodes(K upperInstanceId, String property, Pageable page,Class<U> returnType) throws AbstractMetaParserException {
        Collection<K> instanceIds = relationService().findChildren(upperInstanceId, LinkCategory.RELATIONSHIP, property);
        return nodeService().nodes(instanceIds,page,returnType);
    }

    @Override
    public <U> Future<Collection<U>> query(Supplier<AbstractQueryCondition<U>> condition) {
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
        relationService().link(headId,tailId, LinkCategory.RELATIONSHIP,property);
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
