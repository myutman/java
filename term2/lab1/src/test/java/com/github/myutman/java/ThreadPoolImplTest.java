package com.github.myutman.java;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.*;

public class ThreadPoolImplTest {

    class Helper {
        private int ct;
    }

    private final Helper monitor = new Helper();
    private final int n = 10;
    private ThreadPoolImpl pool;

    @Before
    public void before() {
        monitor.ct = 0;
        pool = new ThreadPoolImpl(n);
    }

    @Test
    public void checkAllThreadsStarted() {
        for (int i = 0; i < n; i++) {
            pool.add(pool.new LightFutureImpl<Integer>(new Supplier<Integer>() {
                @Override
                public Integer get() {
                    synchronized (monitor) {
                        try {
                            monitor.wait();
                            monitor.ct++;
                        } catch (InterruptedException e) {
                            return 0;
                        }
                    }
                    return 0;
                }
            }));
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err.println("Interrupted.");
        }
        synchronized (monitor) {
            monitor.notifyAll();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err.println("Interrupted.");
        }
        pool.shutdown();
        synchronized (monitor) {
            assertEquals(n, monitor.ct);
        }
    }

    @Test
    public void checkCorrectlnessOfExecution() throws LightExecutionException {
        int a = 3, b = 5;
        ThreadPoolImpl.LightFutureImpl<Integer> lightFuture = pool.new LightFutureImpl<>(() -> a + b);
        pool.add(lightFuture);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err.println("Interrupted.");
        }
        pool.shutdown();
        assertTrue(lightFuture.isReady());
        assertEquals(8, (int) lightFuture.get());
    }

    @Test
    public void checkRightException() {
        try {
            pool.new LightFutureImpl<Integer>(new Supplier<Integer>() {
                @Override
                public Integer get() {
                    throw new RuntimeException();
                }
            }).get();
        } catch (LightExecutionException e) {
            System.err.println("Okay.");
        }
    }

    @Test
    public void checkOneAfterAnother() {
        ThreadPoolImpl.LightFutureImpl<Integer> lightFuture = pool.new LightFutureImpl<>(() -> {
            synchronized (monitor) {
                monitor.ct = 1;
            }
            return 7;
        });
        pool.add(lightFuture);
        lightFuture.thenApply((x) -> {
            synchronized (monitor) {
                monitor.ct += 2;
            }
            return x * x;
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err.println("Interrupted.");
        }
        pool.shutdown();
        synchronized (monitor) {
            assertEquals(3, monitor.ct);
        }
    }

    @Test
    public void checkSeveralThenApply() {
        ThreadPoolImpl.LightFutureImpl<Integer> lightFuture = pool.new LightFutureImpl<>(() -> {
            synchronized (monitor) {
                monitor.ct = 1;
            }
            return 7;
        });
        pool.add(lightFuture);
        lightFuture.thenApply((x) -> {
            synchronized (monitor) {
                monitor.ct += 2;
            }
            return x * x;
        });
        lightFuture.thenApply((x) -> {
            synchronized (monitor) {
                monitor.ct += 3;
            }
            return x * x * x;
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err.println("Interrupted.");
        }
        pool.shutdown();
        synchronized (monitor) {
            assertEquals(6, monitor.ct);
        }
    }

    @Test
    public void checkNotBlocking() {
        ThreadPoolImpl.LightFutureImpl<Integer> lightFuture = pool.new LightFutureImpl<>(() -> 7);
        lightFuture.thenApply((x) -> x * x);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err.println("Interrupted.");
        }
        for (int i = 0; i < n; i++) {
            pool.add(pool.new LightFutureImpl<Integer>(new Supplier<Integer>() {
                @Override
                public Integer get() {
                    synchronized (monitor) {
                        try {
                            monitor.wait();
                            monitor.ct++;
                        } catch (InterruptedException e) {
                            return 0;
                        }
                    }
                    return 0;
                }
            }));
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err.println("Interrupted.");
        }
        synchronized (monitor) {
            monitor.notifyAll();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err.println("Interrupted.");
        }
        pool.shutdown();
        synchronized (monitor) {
            assertEquals(n, monitor.ct);
        }
    }

    @Test
    public void checkCorrectnessOfThenApply() throws LightExecutionException {
        ThreadPoolImpl.LightFutureImpl<Integer> lightFuture = pool.new LightFutureImpl<>(() -> 7);
        pool.add(lightFuture);
        ThreadPoolImpl.LightFutureImpl<Integer> lightFuture1 = lightFuture.thenApply((x) -> x * x);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err.println("Interrupted.");
        }
        pool.shutdown();
        assertEquals(49, (int) lightFuture1.get());
    }

    @Test
    public void main() {
        for (int i = 0; i < 2 * n; i++) {
            final Integer z = i + 1;
            ThreadPoolImpl.LightFutureImpl<Integer> lf = pool.new LightFutureImpl<>(() -> z);
            pool.add(lf);
            lf.thenApply((x) -> {
                synchronized (monitor) {
                    monitor.ct += x;
                }
                return x * x;
            });
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err.println("Interrupted.");
        }
        pool.shutdown();
        synchronized (monitor) {
            assertEquals(210, monitor.ct);
        }
    }
}
