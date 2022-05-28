package org.metahut.starfish.unit.expression;

/**
 *
 */
public class DatePairExpression extends BinaryExpression<DateExpression,DateExpression> {

    @Override
    public String getStringExpression() {
        return "and";
    }
}
