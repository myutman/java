package com.github.myutman.java;

/**
 * Created by myutman on 11/7/17.
 *
 * Function with two arguments.
 */
public abstract class Function2<S, T, U> {

    /**
     * @param x - first applied argument
     * @param y - second applied argument
     * @return - result of application
     */
    public abstract U apply(S x, T y);

    /**
     * @param other - function of one argument
     * @return - result of composition of two functions
     */
    public <V> Function2 compose(final Function1<? super U, V> other){
        return new Function2<S, T, V>(){
            public V apply(S x, T y) {
                return other.apply(Function2.this.apply(x, y));
            }
        };
    }

    /**
     * @param x - first applied argument
     * @return - new Function which ignores first argument
     */
    public Function2 bind1(final S x){
        return new Function2<Object, T, U>(){
            public U apply(Object x1, T y1) {
                return Function2.this.apply(x, y1);
            }
        };
    }

    /**
     * @param y - second applied argument
     * @return - new Function which ignores second argument
     */
    public Function2 bind2(final T y){
        return new Function2<S, Object, U>(){
            public U apply(S x1, Object y1) {
                return Function2.this.apply(x1, y);
            }
        };
    }

    /**
     * @param x - first applied argument
     * @return - new Function with one argument which is same to base function with applied argument
     */
    public Function1 curry(final S x){
        return new Function1<T, U>(){
            public U apply(T y) {
                return Function2.this.apply(x, y);
            }
        };
    }
}
