package org.metahut.starfish.parser.function;

import java.util.Comparator;
import java.util.function.Supplier;

/**
 * @author XuYang
 * Create at 2022/3/8
 * @description
 */
class AbstractComparatorSupplier<T extends Comparable> implements Supplier<Comparator<T>> {

    @Override
    public Comparator<T> get() {
        return Comparator.naturalOrder();
    }
}
