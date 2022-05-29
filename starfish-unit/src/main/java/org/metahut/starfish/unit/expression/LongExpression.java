package org.metahut.starfish.unit.expression;

/**
 *
 */
public class LongExpression extends AbstractValueExpression<Long> {

    private Long value;

    public LongExpression(Long value) {
        this.value = value;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
