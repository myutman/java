package com.github.myutman.java.term2;

/**
 * @param <T> calculation type
 * Interface of lazy calculation. The value is being calculated only once, when the get method is being called for the first time.
 */
public interface Lazy<T> {
    /**
     * @return value of calculation
     * Calculate the value. Calculations happens only when this method is called for the first time.
     */
    T get();
}