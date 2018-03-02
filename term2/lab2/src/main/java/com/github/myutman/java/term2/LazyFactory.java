package com.github.myutman.java.term2;

import java.util.function.Supplier;

/**
 * Created by myutman on 2/24/18.
 */
public class LazyFactory {

    public static <T> Lazy<T> createSimpleLazy(Supplier<T> givenSupplier) {
        return new Lazy<T>() {

            boolean calc = false;
            private T result;

            @Override
            public T get() {
                if (!calc) {
                    calc = true;
                    result = givenSupplier.get();
                }
                return result;
            }
        };
    }

    public static <T> Lazy<T> createParallelLazy(Supplier<T> givenSupplier) {
        return new Lazy<T>() {

            boolean calc = false;
            private T result;

            @Override
            public T get() {
                synchronized (this) {
                    if (!calc) {
                        System.out.println("initialized");
                        calc = true;
                        result = givenSupplier.get();
                    }
                }
                return result;
            }
        };
    }
}
