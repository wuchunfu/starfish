package org.metahut.starfish.unit.expression;

/**
 *
 */
public class BetweenAndExpression extends BinaryExpression<StringExpression, StringPairExpression> {

    private StringPairExpression rightExpression;

    @Override
    public StringPairExpression getRightExpression() {
        return rightExpression;
    }

    @Override
    public void setRightExpression(StringPairExpression rightExpression) {
        this.rightExpression = rightExpression;
    }

    @Override
    public String getStringExpression() {
        return "between";
    }
}
