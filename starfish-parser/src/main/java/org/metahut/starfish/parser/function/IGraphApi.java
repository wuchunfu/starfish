package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.Graph;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Map;

/**
 *
 */
interface IGraphApi<V, K, T> extends AbstractQueryService<T> {

    Graph<K, T> graph(V typeName) throws StarFishMetaDataQueryException;

    K create(V typeName, String property,T obj) throws StarFishMetaDataOperatingException;

    K create(V typeName, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    K create(V typeName, K parentInstanceId, String property, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    void add(V typeName, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException;

    void link(V typeName, K headId, K tailId, String property) throws StarFishMetaDataOperatingException;

    void update(V typeName, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException;

    void update(V typeName, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    void modify(V typeName, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    void copy(V totypeName, V fromtypeName,K... instanceIds) throws StarFishMetaDataOperatingException;

    K copy(V typeName, K fromInstanceId, K toInstanceId, String property) throws StarFishMetaDataOperatingException;

    void move(V typeName, K oldInstanceId, K newInstanceId, String property) throws StarFishMetaDataOperatingException;

    void move(V typeName, K oldHeadId, K newHeadId, K tailId, String property) throws StarFishMetaDataOperatingException;

    void delete(V typeName) throws StarFishMetaDataOperatingException;

    void delete(V typeName, K instanceId) throws StarFishMetaDataOperatingException;

    void delete(V typeName, K id, String property) throws StarFishMetaDataOperatingException;

    void crack(V typeName, K headId, K tailId, String property) throws StarFishMetaDataOperatingException;

    void delete(V typeName, K... instanceIds) throws StarFishMetaDataOperatingException;
}
