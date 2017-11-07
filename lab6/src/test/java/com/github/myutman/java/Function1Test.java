package com.github.myutman.java;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by myutman on 11/7/17.
 */
public class Function1Test {

    private Function1 f = new Function1<Integer, Integer>() {
        public Integer apply(Integer x) {
            return x*2;
        }
    };

    private Function1 f1 = new Function1<Integer, Integer>() {
        public Integer apply(Integer x) {
            return x*x;
        }
    };

    @Test
    public void composeOneOrderTest() throws Exception {
        assertEquals(36, f.compose(f1).apply(3));
    }

    @Test
    public void composeAnotherOrderTest() throws Exception {
        assertEquals(18, f1.compose(f).apply(3));
    }

    @Test
    public void composItselfTest() throws Exception {
        assertEquals(81, f1.compose(f1).apply(3));
    }
}