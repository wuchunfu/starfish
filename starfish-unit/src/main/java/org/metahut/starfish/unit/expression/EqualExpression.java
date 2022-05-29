package org.metahut.starfish.unit.expression;

/**
 *
 */
public class EqualExpression extends BinaryExpression<StringExpression,AbstractValueExpression> {

    @Override
    public String getStringExpression() {
        return "=";
    }
}
