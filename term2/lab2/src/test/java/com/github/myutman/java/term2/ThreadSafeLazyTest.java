package com.github.myutman.java.term2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ThreadSafeLazyTest {

    private int ct;

    @Before
    public void before() {
        ct = 0;
    }

    @Test
    public void checkForLaziness() {
        Lazy<String> lazy = LazyFactory.createThreadSafeLazy(() -> {
            ct++;
            return "hi";
        });
        assertEquals(0, ct);
    }

    @Test
    public void checkForBeingCalculatedOnlyOnce() {
        Lazy<String> lazy = LazyFactory.createThreadSafeLazy(() -> {
            ct++;
            return "hi";
        });
        int n = 10000;
        Thread[] threads = new Thread[n];
        for (int i = 0; i < n; i++) {
            threads[i] = new Thread(lazy::get);
            threads[i].start();
        }
        for (int i = 0; i < n; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ignored) { }
        }
        assertEquals(1, ct);
    }

}