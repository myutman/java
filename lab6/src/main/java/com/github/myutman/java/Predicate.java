package com.github.myutman.java;

/**
 * Created by myutman on 11/7/17.
 *
 * Predicate with one arguments which is function that returning boolean.
 */
public abstract class Predicate<T> extends Function1<T, Boolean> {

    /**
     * Predicate which value is disjunction of values of this and given predicate.
     * @param other another predicate
     * @return result of disjunction of two predicates
     */
    public Predicate<T> or(final Predicate<? super T> other) {
        return new Predicate<T>() {
            @Override
            public Boolean apply(T x) {
                return Predicate.this.apply(x) || other.apply(x);
            }
        };
    }

    /**
     * Predicate which value is conjunction of values of this and given predicate.
     * @param other another predicate
     * @return result of conjunction of two predicates
     */
    public Predicate<T> and(final Predicate<? super T> other) {
        return new Predicate<T>() {
            @Override
            public Boolean apply(T x) {
                return Predicate.this.apply(x) && other.apply(x);
            }
        };
    }

    /**
     * Predicate which value is inversion of this predicate.
     * @return result of inversion of the predicate
     */
    public Predicate<T> not() {
        return new Predicate<T>() {
            @Override
            public Boolean apply(T x) {
                return !Predicate.this.apply(x);
            }
        };
    }

    /**
     * Predicate which is always true.
     * @return true predicate
     */
    public static <U> Predicate<U> ALWAYS_TRUE() {
        return new Predicate<U>() {
            @Override
            public Boolean apply(U x) {
                return true;
            }
        };
    }

    /**
     * Predicate which is always false.
     * @return false predicate
     */
    public static <U> Predicate<U> ALWAYS_FALSE() {
        return new Predicate<U>() {
            @Override
            public Boolean apply(U x) {
                return false;
            }
        };
    }
}

