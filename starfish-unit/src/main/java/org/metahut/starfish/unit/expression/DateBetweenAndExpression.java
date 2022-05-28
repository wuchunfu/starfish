package org.metahut.starfish.unit.expression;

/**
 *
 */
public class DateBetweenAndExpression extends BinaryExpression<StringExpression, DatePairExpression> {

    private DatePairExpression rightExpression;

    @Override
    public DatePairExpression getRightExpression() {
        return rightExpression;
    }

    @Override
    public void setRightExpression(DatePairExpression rightExpression) {
        this.rightExpression = rightExpression;
    }

    @Override
    public String getStringExpression() {
        return "between";
    }
}
