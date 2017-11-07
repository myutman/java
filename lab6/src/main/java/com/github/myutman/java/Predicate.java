package com.github.myutman.java;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by myutman on 11/7/17.
 *
 * Predicate with one arguments which is function that returning boolean.
 */
public abstract class Predicate<T> extends Function1<T, Boolean> {

    /**
     * @param other - another predicate
     * @return - result of disjunction of two predicates
     */
    public Predicate or(final Predicate<? super T> other){
        return new Predicate<T>() {
            @Override
            public Boolean apply(T x) {
                return Predicate.this.apply(x) || other.apply(x);
            }
        };
    }

    /**
     * @param other - another predicate
     * @return - result of conjunction of two predicates
     */
    public Predicate and(final Predicate<? super T> other){
        return new Predicate<T>() {
            @Override
            public Boolean apply(T x) {
                return Predicate.this.apply(x) && other.apply(x);
            }
        };
    }

    /**
     * @return - result of inversion of the predicate
     */
    public Predicate not(){
        return new Predicate<T>() {
            @Override
            public Boolean apply(T x) {
                return !Predicate.this.apply(x);
            }
        };
    }

    /**
     * Predicate which is always true.
     */
    public static <U> Predicate ALWAYS_TRUE(){
        return new Predicate<U>() {
            @Override
            public Boolean apply(U x) {
                return true;
            }
        };
    }

    /**
     * Predicate which is always false.
     */
    public static <U> Predicate ALWAYS_FALSE(){
        return new Predicate<U>() {
            @Override
            public Boolean apply(U x) {
                return false;
            }
        };
    }
}

