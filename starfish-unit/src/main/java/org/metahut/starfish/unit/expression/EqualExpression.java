package org.metahut.starfish.unit.expression;

/**
 *
 */
public class EqualExpression extends BinaryExpression<StringExpression,StringExpression> {

    @Override
    public String getStringExpression() {
        return "=";
    }
}
