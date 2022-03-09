package org.metahut.starfish.parser.domain.instance;

import java.io.Serializable;
import java.util.Map;

/**
 * @author XuYang
 * Create at 2022/3/9
 * @description
 */
public class SfNode<K extends Comparable> {

    private K instanceId;

    private Map<String,Object> values;

}
