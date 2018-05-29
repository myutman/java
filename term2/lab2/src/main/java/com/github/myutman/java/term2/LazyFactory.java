package com.github.myutman.java.term2;

import java.util.function.Supplier;

/**
 * Factory of lazy calculation objects. It can create simple lazy object and thread-safe lazy object.
 */
public class LazyFactory {

    /**
     * Creates new lazy calculation object.
     * @param givenSupplier given supplier
     * @param <T> return type
     * @return value of calculation
     */
    public static <T> Lazy<T> createSimpleLazy(Supplier<T> givenSupplier) {
        return new Lazy<T>() {

            private Supplier<T> supplier = givenSupplier;
            private T result;

            @Override
            public T get() {
                if (supplier != null) {
                    result = supplier.get();
                    supplier = null;
                }
                return result;
            }
        };
    }


    /**
     * Creates new thread safe lazy calculation object.
     * @param givenSupplier given supplier
     * @param <T> return type
     * @return value of calculation
     */
    public static <T> Lazy<T> createThreadSafeLazy(Supplier<T> givenSupplier) {
        return new Lazy<T>() {

            private Supplier<T> supplier = givenSupplier;
            private T result;

            @Override
            public T get() {
                if (supplier == null) {
                    return result;
                }

                synchronized (this) {
                    if (supplier != null) {
                        result = supplier.get();
                        supplier = null;
                    }
                }
                return result;
            }
        };
    }
}
