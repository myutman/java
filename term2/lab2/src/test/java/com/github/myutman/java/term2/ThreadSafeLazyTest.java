package com.github.myutman.java.term2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ThreadSafeLazyTest {

    int ct;

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
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> { lazy.get(); }).start();
        }
        assertEquals(1, ct);
    }

}