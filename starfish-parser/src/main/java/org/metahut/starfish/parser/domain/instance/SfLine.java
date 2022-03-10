package org.metahut.starfish.parser.domain.instance;

/**
 *
 */
public class SfLine<K extends Comparable> {
    private K headId;
    private K tailId;
    private String property;

    public K getHeadId() {
        return headId;
    }

    public void setHeadId(K headId) {
        this.headId = headId;
    }

    public K getTailId() {
        return tailId;
    }

    public void setTailId(K tailId) {
        this.tailId = tailId;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public SfLine() {
    }

    public SfLine(K headId, K tailId, String property) {
        this.headId = headId;
        this.tailId = tailId;
        this.property = property;
    }
}
