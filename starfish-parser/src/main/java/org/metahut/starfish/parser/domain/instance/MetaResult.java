package org.metahut.starfish.parser.domain.instance;

import org.metahut.starfish.parser.domain.enums.Type;

import java.util.Collection;
import java.util.Set;

/**
 *
 */
public class MetaResult<E,K,T> {
    private Graph<K,T> graph;
    private Collection<Class> classInfos;
    private Set<Type<K>> typesInfos;

    public MetaResult() {
    }

    public MetaResult(Graph<K, T> graph, Collection<Class> classInfos, Set<Type<K>> typesInfos) {
        this.graph = graph;
        this.classInfos = classInfos;
        this.typesInfos = typesInfos;
    }

    public Graph<K, T> getGraph() {
        return graph;
    }

    public void setGraph(Graph<K, T> graph) {
        this.graph = graph;
    }

    public Collection<Class> getClassInfos() {
        return classInfos;
    }

    public void setClassInfos(Collection<Class> classInfos) {
        this.classInfos = classInfos;
    }

    public Set<Type<K>> getTypesInfos() {
        return typesInfos;
    }

    public void setTypesInfos(Set<Type<K>> typesInfos) {
        this.typesInfos = typesInfos;
    }

}
