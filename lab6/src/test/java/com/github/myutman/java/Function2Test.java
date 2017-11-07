package com.github.myutman.java;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by myutman on 11/8/17.
 */
public class Function2Test {

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

    private Function2 f2 = new Function2<Integer, Integer, Integer>() {
        public Integer apply(Integer x, Integer y) {
            return x + y;
        }
    };

    @Test
    public void composeTest() throws Exception {
        assertEquals(49, f2.compose(f1).apply(3, 4));
    }

    @Test
    public void bind1Test() throws Exception {
        assertEquals(7, f2.bind1(3).apply(5, 4));
    }

    @Test
    public void bind2Test() throws Exception {
        assertEquals(7, f2.bind2(4).apply(3, 5));
    }

    @Test
    public void curryTest() throws Exception {
        assertEquals(7, f2.curry(3).apply(4));
    }

    @Test
    public void composeBind1Test() throws Exception {
        assertEquals(49, f2.bind1(3).compose(f1).apply(5, 4));
    }

    @Test
    public void composeBind2Test() throws Exception {
        assertEquals(49, f2.bind2(4).compose(f1).apply(3, 5));
    }

    @Test
    public void composeCurryOneOrderTest() throws Exception {
        assertEquals(49, f2.curry(3).compose(f1).apply(4));
    }

    @Test
    public void composeCurryAnotherOrderTest() throws Exception {
        assertEquals(19, f1.compose(f2.curry(3)).apply(4));
    }

    @Test
    public void composeCurryItselfTest() throws Exception {
        assertEquals(12, f2.curry(5).compose(f2.curry(3)).apply(4));
    }
}