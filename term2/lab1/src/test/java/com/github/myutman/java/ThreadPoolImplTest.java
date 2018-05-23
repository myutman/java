package com.github.myutman.java;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.*;

public class ThreadPoolImplTest {

    private class Helper {
        private int ct;
    }

    private final Object otherMonitor = new Object();
    private boolean flag;
    private final Helper monitor = new Helper();
    private final int n = 10;
    private ThreadPoolImpl pool;

    @Before
    public void before() {
        flag = false;
        monitor.ct = 0;
        pool = new ThreadPoolImpl(n);
    }

    @Test
    public void checkAllThreadsStarted() {
        for (int i = 0; i < n; i++) {
            pool.add(() -> {
                synchronized (monitor) {
                    monitor.ct++;
                    monitor.notify();
                }
                synchronized (otherMonitor) {
                    while (!flag) {
                        try {
                            otherMonitor.wait();
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
                return 0;
            });
        }
        synchronized (monitor) {
            while (monitor.ct < n) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
            assertEquals(n, monitor.ct);
        }
        synchronized (otherMonitor) {
            flag = true;
            otherMonitor.notifyAll();
        }
        pool.shutdown();
    }

    @Test
    public void checkCorrectnessOfExecution() throws LightExecutionException {
        int a = 3, b = 5;
        Supplier<Integer> supplier = () -> a + b;
        pool.add(supplier);
        pool.shutdown();
        assertEquals(8, (int) supplier.get());
    }

    @Test
    public void checkRightException() {
        LightFuture lightFuture = pool.add(() -> {
            throw new RuntimeException();
        });
        pool.shutdown();
        try {
            lightFuture.get();
        } catch (LightExecutionException ignored) {

        }
    }

    @Test
    public void checkOneAfterAnother() {
        LightFuture<Integer> lightFuture = pool.add(() -> {
            synchronized (monitor) {
                monitor.ct = 1;
            }
            return 7;
        });
        lightFuture.thenApply((x) -> {
            synchronized (monitor) {
                monitor.ct += 2;
            }
            return x * x;
        });
        pool.shutdown();
        synchronized (monitor) {
            assertEquals(3, monitor.ct);
        }
    }

    @Test
    public void checkSeveralThenApply() {
        LightFuture<Integer> lightFuture = pool.add(() -> {
            synchronized (monitor) {
                monitor.ct = 1;
            }
            return 7;
        });
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
        pool.shutdown();
        synchronized (monitor) {
            assertEquals(6, monitor.ct);
        }
    }

    @Test
    public void checkNotBlocking() {
        synchronized (monitor) {
            monitor.ct = 1;
        }
        LightFuture<Integer> lightFuture = pool.add(() -> {
            synchronized (monitor) {
                while (monitor.ct < n) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
            return 7;
        });
        lightFuture.thenApply((x) -> x * x);
        for (int i = 1; i < n; i++) {
            pool.add(() -> {
                synchronized (monitor) {
                    monitor.ct++;
                    if (monitor.ct == n) {
                        monitor.notifyAll();
                    }
                }
                synchronized (otherMonitor) {
                    while (!flag) {
                        try {
                            otherMonitor.wait();
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
                return 0;
            });
        }
        synchronized (monitor) {
            while (monitor.ct < n) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
            assertEquals(n, monitor.ct);
        }
        synchronized (otherMonitor) {
            flag = true;
            otherMonitor.notifyAll();
        }
        pool.shutdown();

    }

    @Test
    public void checkCorrectnessOfThenApply() throws LightExecutionException {
        LightFuture<Integer> lightFuture = pool.add(() -> 7);
        LightFuture<Integer> lightFuture1 = lightFuture.thenApply((x) -> x * x);
        pool.shutdown();
        assertEquals(49, (int) lightFuture1.get());
    }

    @Test
    public void main() {
        for (int i = 0; i < 2 * n; i++) {
            final Integer z = i + 1;
            LightFuture<Integer> lightFuture = pool.add(() -> z);
            lightFuture.thenApply((x) -> {
                synchronized (monitor) {
                    monitor.ct += x;
                }
                return x * x;
            });
        }
        pool.shutdown();
        synchronized (monitor) {
            assertEquals(210, monitor.ct);
        }
    }
}
