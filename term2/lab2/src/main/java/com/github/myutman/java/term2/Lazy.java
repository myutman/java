package com.github.myutman.java.term2;

/**
 * Interface of lazy calculation. The value is being calculated only once, when the get method is being called for the first time.
 * @param <T> calculation type
 */
public interface Lazy<T> {
    /**
     * Calculate the value. Calculations happens only when this method is called for the first time.
     * @return value of calculation
     */
    T get();
}