package org.metahut.starfish.parser.domain;

/**
 *
 */
public enum KeyWord {
    /**
     *
     */
    CLASS("_class");

    private String value;

    public String getValue() {
        return value;
    }

    KeyWord(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
