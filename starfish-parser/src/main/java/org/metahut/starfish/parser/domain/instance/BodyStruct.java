package org.metahut.starfish.parser.domain.instance;

import java.util.Map;

public class BodyStruct<T> {

    private String name;

    private Map<String,T> attributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, T> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, T> attributes) {
        this.attributes = attributes;
    }
}
