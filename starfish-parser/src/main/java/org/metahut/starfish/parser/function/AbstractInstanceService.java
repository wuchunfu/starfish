package org.metahut.starfish.parser.function;

import java.util.Set;

/**
 *  Graph [Node {Class:{properties}}] - line - Graph
 */
public abstract class AbstractInstanceService<K extends Comparable> {

    /**
     * read classInfo
     * @param env
     * @return
     */
    abstract Set<K> instanceMap(String env);

}
