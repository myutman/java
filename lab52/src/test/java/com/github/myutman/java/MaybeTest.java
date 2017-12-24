package com.github.myutman.java;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by myutman on 10/17/17.
 */
public class MaybeTest {

    /**
     * Checks that get returns correct values on integer argument.
     */
    @Test
    public void getCheckIntegerBoxing() throws Exception {
        assertEquals(5, Maybe.just(5).get());
    }

    /**
     * Checks that get returns correct values on string argument.
     */
    @Test
    public void getCheckString() throws Exception {
        assertEquals("abacaba", Maybe.just("abacaba").get());
    }

    /**
     * Checks that get returns correct values on integer argument.
     */
    @Test
    public void isPresentCheckIntegerBoxing() throws Exception {
        assertTrue(Maybe.just(5).isPresent());
    }

    /**
     * Checks that get returns correct values on string argument.
     */
    @Test
    public void isPresentCheckString() throws Exception {
        assertTrue(Maybe.just("abacaba").isPresent());
    }

    /**
     * Checks that get returns correct values on nothing argument.
     */
    @Test
    public void isPresentCheckNothing() throws Exception {
        assertFalse(Maybe.nothing().isPresent());
    }

    /**
     * Checks that get returns correct values on present argument with boxed nothing value.
     */
    @Test
    public void isPresentCheckBoxedNothing() throws Exception {
        assertTrue(Maybe.just(Maybe.nothing()).isPresent());
    }

    /**
     * Checks that map returns correct values on integer argument.
     */
    @Test
    public void mapCheckIntegerBoxing() throws Exception {
        assertEquals(125, Maybe.just(5).map(x -> x * x * x).get());
    }

    /**
     * Checks that map returns correct values on string argument.
     */
    @Test
    public void mapCheckString() throws Exception {
        assertEquals("ac", Maybe.just("abacaba").map(s -> s.substring(2, 4)).get());
    }

    /**
     * Checks that map returns correct values on nothing argument.
     */
    @Test
    public void mapCheckNothing() throws Exception {
        Maybe<Integer> maybe = Maybe.nothing();
        assertFalse(maybe.map(x -> x * x * x).isPresent());
    }

    /**
     * Checks that map works correctly with Maybe method isPresent.
     */
    @Test
    public void mapCheckCompositionMapIsPresent() throws Exception {
        assertTrue(Maybe.just(Maybe.just(5)).map(Maybe::isPresent).get());
    }

    /**
     * Checks that map works correctly with Maybe method isPresent if argument is nothing.
     */
    @Test
    public void mapCheckNothingCompositionMapIsPresent() throws Exception {
        assertFalse(Maybe.just(Maybe.nothing()).map(Maybe::isPresent).get());
    }
}