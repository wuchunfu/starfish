package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.instance.SfClass;
import org.metahut.starfish.parser.domain.instance.SfEnvironment;
import org.metahut.starfish.parser.domain.instance.SfGraph;
import org.metahut.starfish.parser.exception.StarFishMetaDataOperatingException;
import org.metahut.starfish.parser.exception.StarFishMetaDataQueryException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 * T or interface or Object it's hard to define
 * env 不断修改
 * env修改过程中可以制订 class 可以修改
 *
 */
public abstract class AbstractDatabaseService<E,K,T> extends AbstractQueryService<T> implements AbstractApiService<E,K,T> {

    abstract AbstractGraphService<E,K,T> getGraphService();

    abstract AbstractClassService<E,T> getClassService();

    abstract AbstractEnvironmentService<E,T> getEnvironmentService();

    abstract EnvironmentUnit environmentUnit();

    public SfGraph<K,T> graph(E env) throws StarFishMetaDataQueryException {
        return getGraphService().graph(env);
    }

    @Override
    public Collection<T> query(AbstractQueryCondition condition) {
        return merge(getGraphService().query(condition),getClassService().query(condition),getEnvironmentService().query(condition));
    }

    @Override
    public Future<Collection<T>> query(Supplier<AbstractQueryCondition> condition) {
        return new FakeFuture<>(query(condition.get()));
    }
    //TODO 查询

    //批量和全量 增删改查
    /**
     * create a new env
     * @return
     */
    @Override
    public SfEnvironment<E> create() throws StarFishMetaDataOperatingException {
        return getEnvironmentService().create();
    }


    // add env, change env,  env 独立管理
    // 在已有下面修改 class ?
    //TODO 大量api
    public void modify(E env) throws StarFishMetaDataOperatingException {
//        getEnvironmentService().modify(env);
    }

    //valid
    @Override
    public K create(E env, long classId, Map<String,T> attributes) throws StarFishMetaDataOperatingException, StarFishMetaDataQueryException {
        getEnvironmentService().valid(env);
        getEnvironmentService().testUnderDevelopment(env,false);
        List<SfClass> search = environmentUnit().search(env);
        //添加后校验？
        K key = getGraphService().create(env,attributes);

        SfClass sfClass = environmentUnit().search(env, classId);
//        graph.merge(instanceId,attributes);
//        valid(search,graph);
//        getGraphService().add(env,instanceId,property,obj);
        return key;
    }

    public void add(E env,K instanceId,String property,T obj) throws StarFishMetaDataOperatingException, StarFishMetaDataQueryException {
        getEnvironmentService().valid(env);
        getEnvironmentService().testUnderDevelopment(env,false);
        List<SfClass> classes = environmentUnit().search(env);
        SfGraph<K, T> graph = getGraphService().graph(env);
        graph.merge(instanceId,property,obj);
        valid(graph,classes.toArray(new SfClass[classes.size()]));
        getGraphService().add(env,instanceId,property,obj);
    }


    @Override
    public void delete(E env) throws StarFishMetaDataOperatingException {
        getEnvironmentService().delete(env);
        getGraphService().delete(env);
        getClassService().delete(env);
    }

    private void merge(SfGraph<K,T> graph,K instanceId, T obj) {

    }

    private SfGraph<K,T> load(E env) {
        return null;
    }

    /**
     * valid
     * @param classes
     * @param graph
     */
    abstract void valid(SfGraph<K,T> graph,SfClass... classes);
}
