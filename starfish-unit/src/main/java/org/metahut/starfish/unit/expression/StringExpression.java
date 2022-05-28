package org.metahut.starfish.unit.expression;

/**
 *
 */
public class StringExpression extends AbstractValueExpression<String> {

    private String value;

    public StringExpression(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
