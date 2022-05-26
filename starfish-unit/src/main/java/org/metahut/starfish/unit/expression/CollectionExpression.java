package org.metahut.starfish.unit.expression;

import java.util.List;

/**
 *
 */
public class CollectionExpression implements Expression {

    private List<String> values;

    public CollectionExpression(List<String> values) {
        this.values = values;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return null;
    }
}
