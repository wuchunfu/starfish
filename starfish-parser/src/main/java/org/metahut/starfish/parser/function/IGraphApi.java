package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.Graph;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Map;

/**
 *
 */
interface IGraphApi<E, K, T> extends AbstractQueryService<T> {

    Graph<K, T> graph(E env) throws StarFishMetaDataQueryException;

    K create(E env, String property,T obj) throws StarFishMetaDataOperatingException;

    K create(E env, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    K create(E env, K parentInstanceId, String property, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    void add(E env, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException;

    void link(E env, K headId, K tailId, String property) throws StarFishMetaDataOperatingException;

    void update(E env, K instanceId, String property, T obj) throws StarFishMetaDataOperatingException;

    void update(E env, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    void modify(E env, K instanceId, Map<String, T> attributes) throws StarFishMetaDataOperatingException;

    void copy(E toEnv,E fromEnv,K... instanceIds) throws StarFishMetaDataOperatingException;

    K copy(E env, K fromInstanceId, K toInstanceId, String property) throws StarFishMetaDataOperatingException;

    void move(E env, K oldInstanceId, K newInstanceId, String property) throws StarFishMetaDataOperatingException;

    void move(E env, K oldHeadId, K newHeadId, K tailId, String property) throws StarFishMetaDataOperatingException;

    void delete(E env) throws StarFishMetaDataOperatingException;

    void delete(E env, K instanceId) throws StarFishMetaDataOperatingException;

    void delete(E env, K id, String property) throws StarFishMetaDataOperatingException;

    void crack(E env, K headId, K tailId, String property) throws StarFishMetaDataOperatingException;

    void delete(E env, K... instanceIds) throws StarFishMetaDataOperatingException;
}
