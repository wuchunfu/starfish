package org.metahut.starfish.unit.expression;

/**
 *
 */
public class StringPairExpression extends BinaryExpression<StringExpression,StringExpression> {

    @Override
    public String getStringExpression() {
        return "and";
    }
}
