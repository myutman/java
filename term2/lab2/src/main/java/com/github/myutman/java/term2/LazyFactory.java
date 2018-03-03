package com.github.myutman.java.term2;

import java.util.function.Supplier;

/**
 * Factory of lazy calculation objects. It can create simple lazy object and thread-safe lazy object.
 */
public class LazyFactory {

    /**
     * @param givenSupplier given supplier
     * @param <T> return type
     * @return value of calculation
     * Creates new lazy calculation object.
     */
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


    /**
     * @param givenSupplier given supplier
     * @param <T> return type
     * @return value of calculation
     * Creates new thread safe lazy calculation object.
     */
    public static <T> Lazy<T> createThreadSafeLazy(Supplier<T> givenSupplier) {
        return new Lazy<T>() {

            boolean calc = false;
            private T result;

            @Override
            public synchronized T get() {
                if (!calc) {
                    calc = true;
                    result = givenSupplier.get();
                }
                return result;
            }
        };
    }
}
