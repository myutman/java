package com.github.myutman.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by myutman on 11/14/17.
 */
public class TreapMyTreeSetTest {

    private TreapMyTreeSet<Integer> testSet = new TreapMyTreeSet<>();
    private ArrayList<Integer> testArrayList = new ArrayList<>();

    @Before
    public void before(){
        testArrayList.add(1);
        testArrayList.add(-5);
        testArrayList.add(3);
    }

    @After
    public void after(){
        testSet.clear();
        testArrayList.clear();
    }

    @Test
    public void testFirstEmpty() throws Exception {
        assertNull(testSet.first());
    }

    @Test
    public void testLastEmpty() throws Exception {
        assertNull(testSet.last());
    }

    @Test
    public void testLowerNull() throws Exception {
        testSet.add(3);
        testSet.add(4);
        assertNull(testSet.lower(3));
    }

    @Test
    public void testFloorNull() throws Exception {
        testSet.add(3);
        testSet.add(4);
        assertNull(testSet.floor(2));
    }

    @Test
    public void testCeilingNull() throws Exception {
        testSet.add(3);
        testSet.add(4);
        assertNull(testSet.ceiling(5));
    }

    @Test
    public void testHigherNull() throws Exception {
        testSet.add(3);
        testSet.add(4);
        assertNull(testSet.higher(4));
    }

    @Test
    public void testFirst() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.add(17);
        testSet.add(228);
        testSet.add(-22334);
        assertEquals(-22334, testSet.first());
    }

    @Test
    public void testLast() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.add(17);
        testSet.add(228);
        testSet.add(-22334);
        assertEquals(228, testSet.last());
    }

    @Test
    public void testLower() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        assertEquals(1, testSet.lower(3));
    }

    @Test
    public void testFloor() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        assertEquals(3, testSet.floor(3));
    }

    @Test
    public void testCeiling() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        assertEquals(-5, testSet.ceiling(-5));
    }

    @Test
    public void testHigher() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        assertEquals(1, testSet.higher(-5));
    }

    @Test
    public void testFirstRemoved() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.remove(-5);
        assertEquals(1, testSet.first());
    }

    @Test
    public void testLastRemoved() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.remove(3);
        assertEquals(1, testSet.last());
    }

    @Test
    public void testLowerRemoved() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.remove(1);
        assertEquals(-5, testSet.lower(3));
    }

    @Test
    public void testFloorRemoved() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.remove(3);
        assertEquals(1, testSet.floor(3));
    }

    @Test
    public void testCeilingRemoved() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.remove(-5);
        assertEquals(1, testSet.ceiling(-5));
    }

    @Test
    public void testHigherRemoved() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.remove(1);
        assertEquals(3, testSet.higher(-5));
    }

    @Test
    public void testRemoveTrue() throws Exception {
        testSet.add(2);
        testSet.add(3);
        testSet.add(4);
        assertTrue(testSet.remove(3));
    }

    @Test
    public void testRemoveFalse() throws Exception {
        testSet.add(2);
        testSet.add(3);
        testSet.add(4);
        assertFalse(testSet.remove(1));
    }

    @Test
    public void testAddTrue() throws Exception {
        assertTrue(testSet.add(2));
    }

    @Test
    public void testAddRemovedTrue() throws Exception {
        testSet.add(2);
        testSet.remove(2);
        assertTrue(testSet.add(2));
    }

    @Test
    public void testAddFalse() throws Exception {
        testSet.add(2);
        assertFalse(testSet.add(2));
    }

    @Test
    public void testContainsTrue() throws Exception {
        testSet.add(2);
        testSet.add(3);
        testSet.add(4);
        assertTrue(testSet.contains(2));
    }

    @Test
    public void testContainsFalse() throws Exception {
        testSet.add(2);
        testSet.add(3);
        testSet.add(4);
        assertFalse(testSet.contains(1));
    }

    @Test
    public void testContainsRemovedFalse() throws Exception {
        testSet.add(2);
        testSet.add(3);
        testSet.add(4);
        testSet.remove(3);
        assertFalse(testSet.contains(3));
    }

    @Test
    public void testAddAllTrue() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        assertTrue(testSet.addAll(testArrayList));
    }

    @Test
    public void testRemoveAllTrue() throws Exception {
        testSet.add(2);
        testSet.add(3);
        testSet.add(4);
        assertTrue(testSet.removeAll(testArrayList));
    }

    @Test
    public void testRetainAllTrue() throws Exception {
        testSet.add(2);
        testSet.add(3);
        assertTrue(testSet.retainAll(testArrayList));
    }

    @Test
    public void testContainsAllTrue() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        assertTrue(testSet.containsAll(testArrayList));
    }

    @Test
    public void testAddAllFalse() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        assertFalse(testSet.addAll(testArrayList));
    }

    @Test
    public void testRemoveAllFalse() throws Exception {
        testSet.add(2);
        testSet.add(4);
        assertFalse(testSet.removeAll(testArrayList));
    }

    @Test
    public void testRetainAllFalse() throws Exception {
        testSet.add(1);
        testSet.add(3);
        assertFalse(testSet.retainAll(testArrayList));
    }

    @Test
    public void testContainsAllFalse() throws Exception {
        testSet.add(1);
        testSet.add(2);
        testSet.add(3);
        assertFalse(testSet.containsAll(testArrayList));
    }

    @Test
    public void testAddSize() throws Exception {
        testSet.add(2);
        testSet.add(3);
        testSet.add(9);
        assertEquals(3, testSet.size());
    }

    @Test
    public void testAddSameSize() throws Exception {
        testSet.add(2);
        testSet.add(3);
        testSet.add(2);
        assertEquals(2, testSet.size());
    }

    @Test
    public void testRemoveSize() throws Exception {
        testSet.add(2);
        testSet.add(3);
        testSet.add(9);
        testSet.remove(3);
        assertEquals(2, testSet.size());
    }

    @Test
    public void testRemoveNotExistSize() throws Exception {
        testSet.add(2);
        testSet.add(3);
        testSet.add(9);
        testSet.remove(3);
        testSet.remove(1);
        assertEquals(2, testSet.size());
    }

    @Test
    public void testToArraySorted1() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        Integer[] arr = testSet.toArray(new Integer[3]);
        assertTrue(arr[0] < arr[1]);
    }

    @Test
    public void testToArraySorted2() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        Integer[] arr = testSet.toArray(new Integer[3]);
        assertTrue(arr[1] < arr[2]);
    }

    @Test
    public void testToArraySize() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        Object[] arr = testSet.toArray();
        assertEquals(3, arr.length);
    }

    @Test
    public void testIteratorIterationNumber() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.add(17);
        testSet.add(228);
        testSet.add(-22334);
        int ct = 0;
        for (Integer e: testSet){
            ct++;
        }
        assertEquals(6, ct);
    }

    @Test
    public void testDescendingIteratorIterationNumber() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.add(17);
        testSet.add(228);
        testSet.add(-22334);
        int ct = 0;
        Iterator<Integer> iterator = testSet.descendingIterator();
        while (iterator.hasNext()) {
            iterator.next();
            ct++;
        }
        assertEquals(6, ct);
    }

    @Test
    public void testDescendingIterator() throws Exception {
        testSet.add(2);
        testSet.add(3);
        testSet.add(4);
        Iterator<Integer> iterator = testSet.descendingIterator();
        int ls = 100500;
        boolean flag = true;
        while (iterator.hasNext()){
            int cur = iterator.next();
            flag = flag && (ls >= cur);
            ls = cur;
        }
        assertTrue(flag);
    }

    @Test
    public void testDescendingSetFirst() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.add(17);
        testSet.add(228);
        testSet.add(-22334);
        assertEquals(228, testSet.descendingSet().first());
    }

    @Test
    public void testDescendingSetLast() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.add(17);
        testSet.add(228);
        testSet.add(-22334);
        assertEquals(-22334, testSet.descendingSet().last());
    }

    @Test
    public void testDescendingSetLower() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.add(17);
        testSet.add(228);
        testSet.add(-22334);
        assertEquals(1, testSet.descendingSet().lower(-5));
    }

    @Test
    public void testDescendingSetHigher() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.add(17);
        testSet.add(228);
        testSet.add(-22334);
        assertEquals(-5, testSet.descendingSet().higher(1));
    }

    @Test
    public void testDescendingSetFloor() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.add(17);
        testSet.add(228);
        testSet.add(-22334);
        assertEquals(-5, testSet.descendingSet().floor(-5));
    }

    @Test
    public void testDescendingSetCeiling() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.add(17);
        testSet.add(228);
        testSet.add(-22334);
        assertEquals(1, testSet.descendingSet().ceiling(1));
    }

    @Test
    public void testDescendingSetIterator() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.add(17);
        testSet.add(228);
        testSet.add(-22334);
        int a = testSet.descendingIterator().next();
        int b = testSet.descendingSet().iterator().next();
        assertEquals(a, b);
    }

    @Test
    public void testDescendingSetDescendingIterator() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.add(17);
        testSet.add(228);
        testSet.add(-22334);
        int a = testSet.iterator().next();
        int b = testSet.descendingSet().descendingIterator().next();
        assertEquals(a, b);
    }

    @Test
    public void testDescendingSetDescendingSet() throws Exception {
        testSet.add(1);
        testSet.add(-5);
        testSet.add(3);
        testSet.add(17);
        testSet.add(228);
        testSet.add(-22334);
        MyTreeSet<Integer> testSet1 = testSet.descendingSet();
        testSet1.add(22);
        MyTreeSet st2 = testSet1.descendingSet();
        assertEquals(22, st2.higher(20));
    }

    @Test
    public void testComparator() throws Exception {
        TreapMyTreeSet<String> testSet1 = new TreapMyTreeSet<>(Comparator.comparingInt(String::length));
        testSet1.add("aba");
        testSet1.add("caba");
        testSet1.add("ad");
        assertEquals("ad", testSet1.first());
    }
}