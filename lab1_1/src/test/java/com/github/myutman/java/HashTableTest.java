package com.github.myutman.java;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by myutman on 9/27/17.
 */
public class HashTableTest {

    private HashTable hs = new HashTable();

    @After
    public void doAfter(){
        hs.clear();
    }

    /**
     * Putting different values and checking size.
     */
    @Test
    public void testSizeAfterAddingElementsAndClearing() throws Exception {
        assertEquals(0, hs.size());
        hs.put("aba", "caba");
        assertEquals(1, hs.size());
        hs.put("aba", "daba");
        assertEquals(1, hs.size());
        hs.put("aba", "caba");
        assertEquals(1, hs.size());
        hs.put("daba", "Hey, you!");
        assertEquals(2, hs.size());
        hs.put("Out", "there");
        assertEquals(3, hs.size());
        hs.put("in the", "cold!");
        assertEquals(4, hs.size());
        hs.clear();
        assertEquals(0, hs.size());
    }

    /**
     * Crashes if contains works incorrectly with element from HashTable.
     */
    @Test
    public void testContainsValueExist() throws Exception {
        hs.put("Out", "there");
        assertTrue(hs.contains("Out"));
    }

    /**
     * Crashes if contains works incorrectly with no such element in HashTable.
     */
    @Test
    public void testContainsValueNotExist() throws Exception {
        hs.put("Out", "there");
        assertFalse(hs.contains("out"));
    }

    /**
     * Crashes if contains works incorrectly with cleared HashTable.
     */
    @Test
    public void testContainsValueCleared() throws Exception {
        hs.put("Out", "there");
        hs.clear();
        assertFalse(hs.contains("Out"));
    }

    /**
     * Crashes if get works incorrectly with element from HashTable.
     */
    @Test
    public void testGetValueExist() throws Exception {
        hs.put("Out", "there");
        hs.put("in the", "cold!");
        assertEquals("cold!", hs.get("in the"));
    }

    /**
     * Crashes if get works incorrectly with no such element in HashTable.
     */
    @Test
    public void testGetValueNotExist() throws Exception {
        hs.put("Out", "there");
        hs.put("in the", "cold!");
        assertNull(hs.get("int the"));
    }

    /**
     * Crashes if get works incorrectly with cleared HashTable.
     */
    @Test
    public void testGetValueCleared() throws Exception {
        hs.put("Out", "there");
        hs.put("in the", "cold!");
        hs.clear();
        assertNull(hs.get("in the"));
    }

    /**
     * Crashes if put works incorrectly with no such element in HashTable.
     */
    @Test
    public void testPutNotExisting() throws Exception {
        assertNull(hs.put("aba", "caba"));
    }

    /**
     * Crashes if put works incorrectly with element from HashTable.
     */
    @Test
    public void testPutExisting() throws Exception {
        hs.put("aba", "caba");
        assertEquals("caba", hs.put("aba", "daba"));
    }

    /**
     * Crashes if put works incorrectly with cleared HashTable.
     */
    @Test
    public void testPutCleared() throws Exception {
        hs.put("aba", "caba");
        hs.clear();
        assertNull(hs.put("aba", "daba"));
    }

    /**
     * Crashes if remove works incorrectly with element from HashTable.
     */
    @Test
    public void testRemoveExisting() throws Exception {
        hs.put("Out", "there");
        hs.put("in the", "cold!");
        assertEquals(hs.remove("Out"), "there");
    }

    /**
     * Crashes if remove works incorrectly with no such element in HashTable.
     */
    @Test
    public void testRemoveNotExisting() throws Exception {
        hs.put("Out", "there");
        hs.put("in the", "cold!");
        assertNull(hs.remove("out"));
    }

    /**
     * Crashes if remove works incorrectly with cleared HashTable.
     */
    @Test
    public void testRemoveCleared() throws Exception {
        hs.put("Out", "there");
        hs.put("in the", "cold!");
        hs.clear();
        assertNull(hs.remove("Out"));
    }
}
