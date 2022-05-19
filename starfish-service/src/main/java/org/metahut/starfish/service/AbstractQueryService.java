package org.metahut.starfish.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * abstract class or interface
 */
public interface AbstractQueryService {

    Logger LOG = LoggerFactory.getLogger(AbstractQueryService.class);

    /**
     * query by condition
     * @param condition query condition
     * @return
     */
    <T> Collection<T> query(AbstractQueryCondition<T> condition);

    /**
     * page by condition
     * @param condition
     * @param pageable
     * @param <T>
     * @return
     */
    <T> Page<T> query(AbstractQueryCondition<T> condition, Pageable pageable);

    /**
     *
     * @param condition
     * @param pageableSupplier
     * @param <T>
     * @return
     */
    default <T> Future<Page<T>> query(Supplier<AbstractQueryCondition<T>> condition,Supplier<Pageable> pageableSupplier) {
        return new FakeFuture<>(query(condition.get(),pageableSupplier.get()));
    }

    /**
     * TODO better implement
     * TODO method name
     * a fake future method, please implement yourself when you need
     * @param condition
     * @return
     */
    default <T> Future<Collection<T>> query(Supplier<AbstractQueryCondition<T>> condition) {
        return new FakeFuture<>(query(condition.get()));
    }

    /**
     * TODO ArrayList replace
     * TODO async
     * @param collections
     * @return
     */
    default <T> Collection<T> merge(Collection<T>... collections) {
        return Arrays.stream(collections).flatMap(collection -> collection.stream()).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * TODO error data handle
     * TODO future chain
     * @param collections
     * @return
     */
    default <T> Collection<T> merge(Future<Collection<T>>... collections) {
        return Arrays.stream(collections).flatMap(collection -> {
            try {
                return collection.get().stream();
            } catch (InterruptedException e) {
                LOG.error(e.getMessage(),e);
            } catch (ExecutionException e) {
                LOG.error(e.getMessage(),e);
            }
            return Stream.empty();
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    class FakeFuture<T> implements Future<T> {

        private final T t;

        public FakeFuture(T t) {
            this.t = t;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return true;
        }

        @Override
        public T get() {
            return t;
        }

        @Override
        public T get(long timeout, TimeUnit unit) {
            return null;
        }
    }
}
