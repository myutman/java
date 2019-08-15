package com.github.myutman.java;

import java.util.function.Function;

/**
 * Created by myutman on 10/17/17.
 *
 * Type Maybe that is present value of generic type T or nothing;
 */
public class Maybe<T> {

    private T value;
    private boolean present;
    private static final Maybe NOTHING = new Maybe<>();

    /**
     * Maybe present value constructor
     * @param value just value
     */
    private Maybe(T value) {
        this.value = value;
        present = true;
    }

    /**
     * Maybe nothing constructor
     */
    private Maybe() {
        value = null;
        present = false;
    }

    /**
     * Returns present value.
     * @param t value
     * @param <T> value type
     * @return present Maybe value
     */
    public static <T> Maybe<T> just(T t) {
        return new Maybe<T>(t);
    }

    /**
     * Returns nothing.
     * @param <T> value type
     * @return not present com.github.myutman.java.Maybe value
     */
    public static <T> Maybe<T> nothing() {
        return NOTHING;
    }

    /**
     * Returns present value of Maybe or throws exception if value is not present.
     * @return present value of Maybe
     * @throws Exception - if value is not present
     */
    public T get() throws Exception {
        if (!present)
            throw new NotPresentException();
        return value;
    }

    /**
     * Checks if value is present.
     * @return true if value is present and false otherwise
     */
    public boolean isPresent() {
        return present;
    }

    /**
     * Applies function to present value of Maybe or returns nothing.
     * @param mapper function to be applied
     * @param <U> type of function result
     * @return Maybe with applied value
     */
    public <U> Maybe<U> map(Function<? super T, ? extends U> mapper)  {
        if (!present) return new Maybe<U>();
        return new Maybe<U>(mapper.apply(this.value));
    }
}
