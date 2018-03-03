package com.github.myutman.java.term2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleLazyTest {

    int a = 3, b = 4;
    public Lazy<Integer> lazy;

    @Test
    public void chekForBeingCalculatedOnlyOnce(){
        lazy = LazyFactory.createSimpleLazy(() -> {
            return a + b;
        });
        lazy.get();
        a = 5;
        assertEquals(7, lazy.get().intValue());
    }

    @Test
    public void checkForLaziness(){
        lazy = LazyFactory.createSimpleLazy(() -> {
            return a + b;
        });
        a = 5;
        assertEquals(9, lazy.get().intValue());
    }
}


