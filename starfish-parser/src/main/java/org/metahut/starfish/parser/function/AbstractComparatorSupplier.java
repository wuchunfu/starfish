package org.metahut.starfish.parser.function;

import java.util.Comparator;
import java.util.function.Supplier;

/**
 *
 */
class AbstractComparatorSupplier<T extends Comparable> implements Supplier<Comparator<T>> {

    @Override
    public Comparator<T> get() {
        return Comparator.naturalOrder();
    }
}
