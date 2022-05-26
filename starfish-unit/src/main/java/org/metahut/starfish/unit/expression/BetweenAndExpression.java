package org.metahut.starfish.unit.expression;

/**
 *
 */
public class BetweenAndExpression extends BinaryExpression<StringExpression,AndExpression> {

    private AndExpression rightExpression;

    @Override
    public AndExpression getRightExpression() {
        return rightExpression;
    }

    @Override
    public void setRightExpression(AndExpression rightExpression) {
        this.rightExpression = rightExpression;
    }

    @Override
    public String getStringExpression() {
        return "between";
    }
}
