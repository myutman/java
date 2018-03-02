package com.github.myutman.java.term2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by myutman on 2/25/18.
 */
public class ParallelLazyTest {

    int ct = 0;

    @Test
    public void test() {
        Lazy<String> lazy = LazyFactory.createParallelLazy(() -> {
            ct++;
            return "hi";
        });
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> { lazy.get(); }).start();
        }
        assertEquals(1, ct);
    }

}