package org.metahut.starfish.parser.domain.instance;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class Graph<K,T> {

    protected Map<K, Node<K,T>> nodesMap;

    protected List<Relation<K>> links;

    public Map<K, Node<K, T>> getNodesMap() {
        return nodesMap;
    }

    public void setNodesMap(Map<K, Node<K, T>> nodesMap) {
        this.nodesMap = nodesMap;
    }

    public List<Relation<K>> getLinks() {
        return links;
    }

    public void setLinks(List<Relation<K>> links) {
        this.links = links;
    }

    public Graph(Map<K, Node<K, T>> nodesMap, List<Relation<K>> links) {
        this.nodesMap = nodesMap;
        this.links = links;
    }

    public Graph() {
    }

    /**
     * TODO
     * @param instanceId
     * @param property
     * @param obj
     */
    public void merge(K instanceId, String property, T obj) {
    }
    //find Object (perfect ? )or do cache
    //visit method
    //单纯序列化用 ？
    //内存如果不做更新的话？ 更新应该交给其他方式实现
    //1。生成 link 和 relink 单向成图 序列化用
    //2。提供更新 方法，内存不做保留 （不应该封死上下都是通道）
    //3。提供更改 方法，有默认修改（实现类）
    //4。

}
