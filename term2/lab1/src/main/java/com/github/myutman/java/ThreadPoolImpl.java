package com.github.myutman.java;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of a thread pool.
 */
public class ThreadPoolImpl {

    private final Thread[] pool;
    private final Queue<LightFutureImpl<?>> queue = new ArrayDeque<>();
    private final int n;

    /**
     * Class constructor that starts n threads.
     * @param n number of threads
     */
    public ThreadPoolImpl(int n){
        this.n = n;
        pool = new Thread[n];
        for (int i = 0; i < n; i++) {
            pool[i] = new Thread(() -> {
                while (true) {
                    LightFutureImpl<?> lightFuture = null;
                    try {
                        synchronized (queue) {
                            while (queue.isEmpty()) {
                                queue.wait();
                            }
                            lightFuture = queue.remove();
                        }
                    } catch (InterruptedException e) {
                        break;
                    }
                    if (lightFuture != null) {
                        LightFuture<?> parent = lightFuture.getParent();
                        if (parent != null && !parent.isReady()) {
                            add(lightFuture);
                            continue;
                        }
                        lightFuture.calc();
                    }
                }
            });
            pool[i].start();
        }
    }

    /**
     * Interrupts all the threads in the pool.
     */
    public void shutdown(){
        for (int i = 0; i < n; i++) {
            pool[i].interrupt();
        }
        for (int i = 0; i < n; i++) {
            try {
                pool[i].join();
            } catch (InterruptedException ignored) { }
        }
    }

    private void add(LightFutureImpl<?> lightFuture) {
        synchronized (queue) {
            queue.add(lightFuture);
            queue.notify();
        }
    }

    /**
     * Adds new task to the pool.
     * @param supplier new task
     */
    public <S> LightFuture<S> add(Supplier<? extends S> supplier) {
        LightFutureImpl<S> lightFuture = new LightFutureImpl<>(supplier);
        add(lightFuture);
        return lightFuture;
    }

    /**
     * Inner class, implementation of light future interface.
     */
    public class LightFutureImpl<R> implements LightFuture<R> {

        private Throwable failed = null;
        private boolean ready = false;
        private R value = null;
        private final Supplier<? extends R> supplier;
        private final LightFuture<?> parent;


        /**
         * Class constructor that takes executable task.
         * @param supplier task needed to be executed
         */
        public LightFutureImpl(Supplier<? extends R> supplier) {
            this.supplier = supplier;
            this.parent = null;
        }

        /**
         * Class constructor that takes executable task and another task after which this task should be executed.
         * @param supplier task needed to be executed
         * @param parent task after which this task should be executed
         */
        public LightFutureImpl(Supplier<? extends R> supplier, LightFuture<?> parent) {
            this.supplier = supplier;
            this.parent = parent;
        }

        /**
         * Returns tasks parent if it exists.
         * @return taks parent if it exists or null otherwise
         */
        public synchronized LightFuture<?> getParent(){
            return parent;
        }

        /**
         * Tells whether task is ready.
         * @return true if task is ready and false otherwise
         */
        @Override
        public synchronized boolean isReady() {
            return ready;
        }

        /**
         * Calculates value given as supplier (calculation is conducted only once).
         * @return calculated value
         */
        @Override
        public R get() throws LightExecutionException {
            synchronized (this) {
                if (failed != null) {
                    LightExecutionException e = new LightExecutionException();
                    e.addSuppressed(failed);
                    throw e;
                }
            }
            while (!ready) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (Throwable e) {
                        failed = e;
                    }
                    if (failed != null) {
                        LightExecutionException e = new LightExecutionException();
                        e.addSuppressed(failed);
                        throw e;
                    }
                }
            }
            return value;
        }

        private void calc() {
            try {
                R temp;
                synchronized (supplier) {
                    temp = supplier.get();
                }
                synchronized (this) {
                    value = temp;
                }
            } catch (Throwable e) {
                synchronized (this) {
                    failed = e;
                    notify();
                    return;
                }
            }
            synchronized (this) {
                ready = true;
                notify();
            }
        }

        @Override
        public <S> LightFutureImpl<S> thenApply(Function<? super R, ? extends S> function) {
            synchronized (this) {
                LightFutureImpl<S> lightFuture = new LightFutureImpl<>(() -> function.apply(value), this);
                add(lightFuture);
                return lightFuture;
            }
        }
    }
}
