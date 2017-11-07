package com.github.myutman.java;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by myutman on 11/8/17.
 */
public class PredicateTest {

    private boolean flag;

    @Before
    public void before(){
        flag = false;
    }

    private Predicate p = new Predicate<Integer>() {
        @Override
        public Boolean apply(Integer x) {
            return x * x <= 25;
        }
    };

    private Predicate p1 = new Predicate<Integer>() {
        @Override
        public Boolean apply(Integer x) {
            flag = true;
            return x * x >= 4;
        }
    };

    @Test
    public void orFirstCorrectnessTest() throws Exception {
        assertTrue((Boolean) p.or(p1).apply(3));
    }

    @Test
    public void orSecondCorrectnessTest() throws Exception {
        assertTrue((Boolean) p.or(p1).apply(6));
    }

    @Test
    public void orLazyTest() throws Exception {
        p.or(p1).apply(3);
        assertFalse(flag);
    }

    @Test
    public void andFirstCorrectnessTest() throws Exception {
        assertFalse((Boolean) p.and(p1).apply(6));
    }

    @Test
    public void andSecondCorrectnessTest() throws Exception {
        assertFalse((Boolean) p.and(p1).apply(1));
    }

    @Test
    public void andLazyTest() throws Exception {
        p.and(p1).apply(6);
        assertFalse(flag);
    }

    @Test
    public void notCorrectness() throws Exception {
        assertTrue((Boolean) p.and(p1).not().apply(1));
    }

    @Test
    public void stringTrueTest() throws Exception {
        assertTrue((Boolean) Predicate.ALWAYS_TRUE().apply("abacaba"));
    }

    @Test
    public void stringFalseTest() throws Exception {
        assertFalse((Boolean) Predicate.ALWAYS_FALSE().apply("abacaba"));
    }

    @Test
    public void composeOneOrderTest() throws Exception {
        assertTrue((Boolean) Predicate.ALWAYS_FALSE().compose(Predicate.ALWAYS_TRUE()).apply("abacaba"));
    }

    @Test
    public void composeAnotherOrderTest() throws Exception {
        assertFalse((Boolean) Predicate.ALWAYS_TRUE().compose(Predicate.ALWAYS_FALSE()).apply("abacaba"));
    }
}