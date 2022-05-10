package org.metahut.starfish.parser.domain.instance;

import java.util.List;

/**
 *
 */
public class BatchTypeBody<T> {

    private BodyStruct<T> source;

    private List<Class> types;

    public BodyStruct<T> getSource() {
        return source;
    }

    public void setSource(BodyStruct<T> source) {
        this.source = source;
    }

    public List<Class> getTypes() {
        return types;
    }

    public void setTypes(List<Class> types) {
        this.types = types;
    }
}
