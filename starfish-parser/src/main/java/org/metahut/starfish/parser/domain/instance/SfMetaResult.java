package org.metahut.starfish.parser.domain.instance;

/**
 *
 */
public class SfMetaResult<E,K,T> {
    private SfEnvironment<E> environment;
    private SfGraph<K,T> graph;
    private SfClass classInfo;

    public SfMetaResult() {
    }

    public SfMetaResult(SfEnvironment<E> environment, SfGraph<K, T> graph, SfClass classInfo) {
        this.environment = environment;
        this.graph = graph;
        this.classInfo = classInfo;
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

    public SfClass getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(SfClass classInfo) {
        this.classInfo = classInfo;
    }
}
