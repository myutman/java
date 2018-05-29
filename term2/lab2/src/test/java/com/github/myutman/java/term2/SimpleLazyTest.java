package com.github.myutman.java.term2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleLazyTest {

    private int a;
    private int b;
    private Lazy<Integer> lazy;

    @Before
    public void before(){
        a = 3;
        b = 4;
    }

    @Test
    public void checkForBeingCalculatedOnlyOnce(){
        lazy = LazyFactory.createSimpleLazy(() -> a + b);
        lazy.get();
        a = 5;
        assertEquals(7, lazy.get().intValue());
    }

    @Test
    public void checkForLaziness(){
        lazy = LazyFactory.createSimpleLazy(() -> a + b);
        a = 5;
        assertEquals(9, lazy.get().intValue());
    }
}


