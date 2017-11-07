package com.github.myutman.java;

/**
 * Created by myutman on 11/6/17.
 *
 * Function with one argument.
 */
public abstract class Function1<T, U> {

    /**
     * @param x - applied argument
     * @return - result of application
     */
    public abstract U apply(T x);

    /**
     * @param other - another function with one argument
     * @return - result of composition of two functions
     */
    public <S> Function1 compose(final Function1<? super U, S> other){
        return new Function1<T, S>(){
            public S apply(T x) {
                return other.apply(Function1.this.apply(x));
            }
        };
    }
}
