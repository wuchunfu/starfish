package org.metahut.starfish.parser.domain.instance;

import org.metahut.starfish.parser.domain.enums.SfType;

import java.util.Collection;
import java.util.Set;

/**
 *
 */
public class SfMetaResult<E,K,T> {
    private SfEnvironment<E> environment;
    private SfGraph<K,T> graph;
    private Collection<SfClass> classInfos;
    private Set<SfType<K>> typesInfos;

    public SfMetaResult() {
    }

    public SfMetaResult(SfEnvironment<E> environment, SfGraph<K, T> graph, Collection<SfClass> classInfos, Set<SfType<K>> typesInfos) {
        this.environment = environment;
        this.graph = graph;
        this.classInfos = classInfos;
        this.typesInfos = typesInfos;
    }

    public SfEnvironment<E> getEnvironment() {
        return environment;
    }

    public void setEnvironment(SfEnvironment<E> environment) {
        this.environment = environment;
    }

    public SfGraph<K, T> getGraph() {
        return graph;
    }

    public void setGraph(SfGraph<K, T> graph) {
        this.graph = graph;
    }

    public Collection<SfClass> getClassInfos() {
        return classInfos;
    }

    public void setClassInfos(Collection<SfClass> classInfos) {
        this.classInfos = classInfos;
    }

    public Set<SfType<K>> getTypesInfos() {
        return typesInfos;
    }

    public void setTypesInfos(Set<SfType<K>> typesInfos) {
        this.typesInfos = typesInfos;
    }

}
