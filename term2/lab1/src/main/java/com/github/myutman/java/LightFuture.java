package com.github.myutman.java;

import java.util.function.Function;

/**
 * Interface of lazy calculation for the thread pool.
 */
public interface LightFuture<R> {
    /**
     * Tells whether task is ready.
     * @return true if task is ready and false otherwise
     */
    boolean isReady();

    /**
     * Calculates value given as supplier (calculation is conducted only once).
     * @return calculated value
     */
    R get() throws LightExecutionException;

    /**
     * Makes new light future by applying given function to the result of this light future.
     * @param function given function
     * @return new light future
     */
    <S> LightFuture<S> thenApply(Function<? super R, ? extends S> function);
}
