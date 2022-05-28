package org.metahut.starfish.unit.expression;

import java.util.Date;

/**
 *
 */
public class DateExpression extends AbstractValueExpression<Date> {

    private Date value;

    public DateExpression(Date value) {
        this.value = value;
    }

    @Override
    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
