package com.github.myutman.java;

/**
 * Created by myutman on 11/1/17.
 *
 * Pair of to elements of generic type T.
 */
public class Pair<T> {
    private final T first;
    private T second;

    public T getFirst() { return first; }
    public T getSecond() { return second; }

    public void setSecond(T second) { this.second = second; }

    /**
     * Class constructor.
     *
     * @param first first element
     * @param second second element
     */
    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }
}