package com.github.myutman.java;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class TrieTest {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final ByteArrayOutputStream output1 = new ByteArrayOutputStream();

    /**
     * @throws Exception
     *
     * Outputs Trie and checks output then reads this Trie adds some new Strings and checks output.
     */
    @Test
    public void testSerialization() throws Exception{
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
     * @throws Exception
     *
     * Adds Strings to Trie and checks correctness of returning values of add and also checks size of Trie, Strings containing and Strings which start with prefix.
     */
    @Test
    public void testAdd() throws Exception{
        Trie tr = new Trie();
        tr.add("abaca");
        tr.add("aba");
        tr.add("abada");
        assertEquals(tr.size(), 3);
        assertEquals(tr.add("abada"), false);
        assertEquals(tr.add("abc"), true);
        assertEquals(tr.size(), 4);
        assertEquals(tr.contains("abc"), true);
        assertEquals(tr.contains("abac"), false);
        assertEquals(tr.howManyStartsWithPrefix("ab"), 4);
        assertEquals(tr.howManyStartsWithPrefix("aba"), 3);
    }

    /**
     * @throws Exception
     *
     * Adds different Strings to Trie then checks contain then adds and removes some Strings and checks contain.
     */
    @Test
    public void testContains() throws Exception{
        Trie tr = new Trie();
        tr.add("abacaba");
        tr.add("abaca");
        tr.add("aba");
        assertEquals(tr.contains("abacaba"), true);
        assertEquals(tr.contains("abacab"), false);
        assertEquals(tr.contains("abaca"), true);
        assertEquals(tr.contains("abac"), false);
        assertEquals(tr.contains("aba"), true);
        assertEquals(tr.contains("ab"), false);
        tr.add("abacab");
        assertEquals(tr.contains("abacab"), true);
        tr.remove("abacaba");
        assertEquals(tr.contains("abacaba"), false);
    }

    /**
     * @throws Exception
     *
     * Adds different Strings than checks correctness of returning values of remove and checks size of Trie and how many Strings start with prefix.
     */
    @Test
    public void testRemove() throws Exception{
        Trie tr = new Trie();
        tr.add("abacaba");
        tr.add("abaca");
        tr.add("aba");
        assertEquals(tr.remove("abacaba"), true);
        assertEquals(tr.remove("abacab"), false);
        assertEquals(tr.size(), 2);
        assertEquals(tr.howManyStartsWithPrefix("aba"), 2);
    }

    /**
     * @throws Exception
     *
     * Adds different Strings and checks how many Strings start with prefix then removes one String and checks how many Strings start with prefix.
     */
    @Test
    public void testHowManyStartsWithPrefix() throws Exception{
        Trie tr = new Trie();
        tr.add("abacaba");
        tr.add("abaca");
        tr.add("abada");
        tr.add("acaba");
        tr.add("acama");
        tr.add("acabra");
        assertEquals(tr.howManyStartsWithPrefix("ab"), 3);
        assertEquals(tr.howManyStartsWithPrefix("ac"), 3);
        assertEquals(tr.howManyStartsWithPrefix("a"), 6);
        assertEquals(tr.howManyStartsWithPrefix("abac"), 2);
        assertEquals(tr.howManyStartsWithPrefix("abad"), 1);
        assertEquals(tr.howManyStartsWithPrefix("aca"), 3);
        assertEquals(tr.howManyStartsWithPrefix("acab"), 2);
        assertEquals(tr.howManyStartsWithPrefix("acaba"), 1);
        tr.remove("acaba");
        assertEquals(tr.howManyStartsWithPrefix("ab"), 3);
        assertEquals(tr.howManyStartsWithPrefix("ac"), 2);
        assertEquals(tr.howManyStartsWithPrefix("a"), 5);
        assertEquals(tr.howManyStartsWithPrefix("abac"), 2);
        assertEquals(tr.howManyStartsWithPrefix("abad"), 1);
        assertEquals(tr.howManyStartsWithPrefix("aca"), 2);
        assertEquals(tr.howManyStartsWithPrefix("acab"), 1);
        assertEquals(tr.howManyStartsWithPrefix("acaba"), 0);

    }

}