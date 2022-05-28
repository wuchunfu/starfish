package org.metahut.starfish.unit.expression;

/**
 *
 */
public abstract class AbstractValueExpression<T extends Comparable> implements Expression {

    public abstract T getValue();

}
