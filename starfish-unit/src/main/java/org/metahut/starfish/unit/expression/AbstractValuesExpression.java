package org.metahut.starfish.unit.expression;

import java.util.List;

/**
 *
 */
public abstract class AbstractValuesExpression<T extends Comparable> implements Expression {

    public abstract List<T> getValue();

}
