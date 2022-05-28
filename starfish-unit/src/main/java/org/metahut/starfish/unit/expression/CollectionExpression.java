package org.metahut.starfish.unit.expression;

import java.util.List;

/**
 *
 */
public class CollectionExpression extends AbstractValuesExpression<String> {

    private List<String> values;

    public CollectionExpression(List<String> values) {
        this.values = values;
    }

    @Override
    public List<String> getValue() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

}
