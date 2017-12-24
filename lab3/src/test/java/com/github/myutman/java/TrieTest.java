package com.github.myutman.java;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class TrieTest {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final ByteArrayOutputStream output1 = new ByteArrayOutputStream();

    /**
     * Outputs Trie and checks output then reads this Trie adds some new Strings and checks output.
     */
    @Test
    public void testSerialization() throws Exception {
        Trie tr = new Trie();
        tr.add("abaca");
        tr.add("aba");
        tr.add("abada");
        tr.serialize(new PrintStream(output));
        assertEquals(output.toString(), "3 false 1\na\n3 false 1\nb\n3 false 1\na\n3 true 2\nc\n1 false 1\na\n1 true 0\nd\n1 false 1\na\n1 true 0\n");
        tr.deserialize(new ByteArrayInputStream(output.toByteArray()));
        assertEquals(tr.size(), 3);
        assertEquals(tr.add("abada"), false);
        assertEquals(tr.add("abc"), true);
        assertEquals(tr.size(), 4);
        assertEquals(tr.contains("abc"), true);
        assertEquals(tr.contains("abac"), false);
        assertEquals(tr.howManyStartsWithPrefix("ab"), 4);
        assertEquals(tr.howManyStartsWithPrefix("aba"), 3);
        tr.serialize(new PrintStream(output1));
        assertEquals(output1.toString(), "4 false 1\na\n4 false 1\nb\n4 false 2\na\n3 true 2\nc\n1 false 1\na\n1 true 0\nd\n1 false 1\na\n1 true 0\nc\n1 true 0\n");
    }

    /**
     * Adds Strings to Trie and checks correctness of returning values of add and also checks size of Trie, Strings containing and Strings which start with prefix.
     */
    @Test
    public void testAdd() throws Exception {
        Trie tr = new Trie();
        tr.add("abaca");
        tr.add("aba");
        tr.add("abada");
        assertEquals(3, tr.size());
        assertFalse(tr.add("abada"));
        assertTrue(tr.add("abc"));
        assertEquals(4, tr.size());
        assertTrue(tr.contains("abc"));
        assertFalse(tr.contains("abac"));
        assertEquals(4, tr.howManyStartsWithPrefix("ab"));
        assertEquals(3, tr.howManyStartsWithPrefix("aba"));
    }

    /**
     * Adds different Strings to Trie then checks contain then adds and removes some Strings and checks contain.
     */
    @Test
    public void testContains() throws Exception {
        Trie tr = new Trie();
        tr.add("abacaba");
        tr.add("abaca");
        tr.add("aba");
        assertTrue(tr.contains("abacaba"));
        assertFalse(tr.contains("abacab"));
        assertTrue(tr.contains("abaca"));
        assertFalse(tr.contains("abac"));
        assertTrue(tr.contains("aba"));
        assertFalse(tr.contains("ab"));
        tr.add("abacab");
        assertTrue(tr.contains("abacab"));
        tr.remove("abacaba");
        assertFalse(tr.contains("abacaba"));
    }

    /**
     * Adds different Strings than checks correctness of returning values of remove and checks size of Trie and how many Strings start with prefix.
     */
    @Test
    public void testRemove() throws Exception {
        Trie tr = new Trie();
        tr.add("abacaba");
        tr.add("abaca");
        tr.add("aba");
        assertTrue(tr.remove("abacaba"));
        assertFalse(tr.remove("abacab"));
        assertEquals(2, tr.size());
        assertEquals(2, tr.howManyStartsWithPrefix("aba"));
    }

    /**
     * Adds different Strings and checks how many Strings start with prefix then removes one String and checks how many Strings start with prefix.
     */
    @Test
    public void testHowManyStartsWithPrefix() throws Exception {
        Trie tr = new Trie();
        tr.add("abacaba");
        tr.add("abaca");
        tr.add("abada");
        tr.add("acaba");
        tr.add("acama");
        tr.add("acabra");
        assertEquals(3, tr.howManyStartsWithPrefix("ab"));
        assertEquals(3, tr.howManyStartsWithPrefix("ac"));
        assertEquals(6, tr.howManyStartsWithPrefix("a"));
        assertEquals(2, tr.howManyStartsWithPrefix("abac"));
        assertEquals(1, tr.howManyStartsWithPrefix("abad"));
        assertEquals(3, tr.howManyStartsWithPrefix("aca"));
        assertEquals(2, tr.howManyStartsWithPrefix("acab"));
        assertEquals(1, tr.howManyStartsWithPrefix("acaba"));
        tr.remove("acaba");
        assertEquals(3, tr.howManyStartsWithPrefix("ab"));
        assertEquals(2, tr.howManyStartsWithPrefix("ac"));
        assertEquals(5, tr.howManyStartsWithPrefix("a"));
        assertEquals(2, tr.howManyStartsWithPrefix("abac"));
        assertEquals(1, tr.howManyStartsWithPrefix("abad"));
        assertEquals(2, tr.howManyStartsWithPrefix("aca"));
        assertEquals(1, tr.howManyStartsWithPrefix("acab"));
        assertEquals(0, tr.howManyStartsWithPrefix("acaba"));

    }

}