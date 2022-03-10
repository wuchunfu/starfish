package org.metahut.starfish.parser.domain.instance;

import java.util.Map;

/**
 *
 */
public class SfNode<K extends Comparable> {

    private K instanceId;

    private Map<String,Object> values;

    public K getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(K instanceId) {
        this.instanceId = instanceId;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    public SfNode() {
    }

    public SfNode(K instanceId, Map<String, Object> values) {
        this.instanceId = instanceId;
        this.values = values;
    }
}
