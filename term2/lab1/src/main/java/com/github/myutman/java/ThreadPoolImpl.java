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
                        try {
                            lightFuture.calc();
                        } catch (LightExecutionException e) {
                            System.err.println("Exception during execution of light future.");
                        }
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

    /**
     * Adds new task to the pool.
     * @param lightFuture new task
     */
    public void add(LightFutureImpl<?> lightFuture) {
        synchronized (queue) {
            queue.add(lightFuture);
            queue.notify();
        }
    }

    /**
     * Inner class, implementation of light future interface.
     */
    public class LightFutureImpl<R> implements LightFuture<R> {

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
        public synchronized R get() throws LightExecutionException {
            while (!ready) {
                try {
                    wait();
                } catch (InterruptedException e) {

                }
            }
            return value;
        }

        public void calc() throws LightExecutionException {
            try {
                value = supplier.get();
            } catch (Exception e) {
                throw new LightExecutionException();
            }
            ready = true;
            synchronized (this) {
                this.notifyAll();
            }
        }

        @Override
        public synchronized <S> LightFutureImpl<S> thenApply(Function<? super R, ? extends S> function) {
            LightFutureImpl<S> lightFuture = new LightFutureImpl<>(() -> function.apply(value), this);
            add(lightFuture);
            return lightFuture;
        }
    }
}
