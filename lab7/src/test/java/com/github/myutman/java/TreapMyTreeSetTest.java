package com.github.myutman.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by myutman on 11/14/17.
 */
public class TreapMyTreeSetTest {

    TreapMyTreeSet<Integer> st = new TreapMyTreeSet<Integer>();
    ArrayList<Integer> ar = new ArrayList<Integer>();

    @Before
    public void before(){
        ar.add(1);
        ar.add(-5);
        ar.add(3);
    }

    @After
    public void after(){
        st.clear();
        ar.clear();
    }

    @Test
    public void testFirstEmpty() throws Exception {
        assertNull(st.first());
    }

    @Test
    public void testLastEmpty() throws Exception {
        assertNull(st.last());
    }

    @Test
    public void testLowerNull() throws Exception {
        st.add(3);
        st.add(4);
        assertNull(st.lower(3));
    }

    @Test
    public void testFloorNull() throws Exception {
        st.add(3);
        st.add(4);
        assertNull(st.floor(2));
    }

    @Test
    public void testCeilingNull() throws Exception {
        st.add(3);
        st.add(4);
        assertNull(st.ceiling(5));
    }

    @Test
    public void testHigherNull() throws Exception {
        st.add(3);
        st.add(4);
        assertNull(st.higher(4));
    }

    @Test
    public void testFirst() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.add(17);
        st.add(228);
        st.add(-22334);
        assertEquals(-22334, st.first());
    }

    @Test
    public void testLast() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.add(17);
        st.add(228);
        st.add(-22334);
        assertEquals(228, st.last());
    }

    @Test
    public void testLower() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        assertEquals(1, st.lower(3));
    }

    @Test
    public void testFloor() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        assertEquals(3, st.floor(3));
    }

    @Test
    public void testCeiling() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        assertEquals(-5, st.ceiling(-5));
    }

    @Test
    public void testHigher() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        assertEquals(1, st.higher(-5));
    }

    @Test
    public void testFirstRemoved() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.remove(-5);
        assertEquals(1, st.first());
    }

    @Test
    public void testLastRemoved() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.remove(3);
        assertEquals(1, st.last());
    }

    @Test
    public void testLowerRemoved() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.remove(1);
        assertEquals(-5, st.lower(3));
    }

    @Test
    public void testFloorRemoved() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.remove(3);
        assertEquals(1, st.floor(3));
    }

    @Test
    public void testCeilingRemoved() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.remove(-5);
        assertEquals(1, st.ceiling(-5));
    }

    @Test
    public void testHigherRemoved() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.remove(1);
        assertEquals(3, st.higher(-5));
    }

    @Test
    public void testRemoveTrue() throws Exception {
        st.add(2);
        st.add(3);
        st.add(4);
        assertTrue(st.remove(3));
    }

    @Test
    public void testRemoveFalse() throws Exception {
        st.add(2);
        st.add(3);
        st.add(4);
        assertFalse(st.remove(1));
    }

    @Test
    public void testAddTrue() throws Exception {
        assertTrue(st.add(2));
    }

    @Test
    public void testAddRemovedTrue() throws Exception {
        st.add(2);
        st.remove(2);
        assertTrue(st.add(2));
    }

    @Test
    public void testAddFalse() throws Exception {
        st.add(2);
        assertFalse(st.add(2));
    }

    @Test
    public void testContainsTrue() throws Exception {
        st.add(2);
        st.add(3);
        st.add(4);
        assertTrue(st.contains(2));
    }

    @Test
    public void testContainsFalse() throws Exception {
        st.add(2);
        st.add(3);
        st.add(4);
        assertFalse(st.contains(1));
    }

    @Test
    public void testContainsRemovedFalse() throws Exception {
        st.add(2);
        st.add(3);
        st.add(4);
        st.remove(3);
        assertFalse(st.contains(3));
    }

    @Test
    public void testAddAllTrue() throws Exception {
        st.add(1);
        st.add(-5);
        assertTrue(st.addAll(ar));
    }

    @Test
    public void testRemoveAllTrue() throws Exception {
        st.add(2);
        st.add(3);
        st.add(4);
        assertTrue(st.removeAll(ar));
    }

    @Test
    public void testRetainAllTrue() throws Exception {
        st.add(2);
        st.add(3);
        assertTrue(st.retainAll(ar));
    }

    @Test
    public void testContainsAllTrue() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        assertTrue(st.containsAll(ar));
    }

    @Test
    public void testAddAllFalse() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        assertFalse(st.addAll(ar));
    }

    @Test
    public void testRemoveAllFalse() throws Exception {
        st.add(2);
        st.add(4);
        assertFalse(st.removeAll(ar));
    }

    @Test
    public void testRetainAllFalse() throws Exception {
        st.add(1);
        st.add(3);
        assertFalse(st.retainAll(ar));
    }

    @Test
    public void testContainsAllFalse() throws Exception {
        st.add(1);
        st.add(2);
        st.add(3);
        assertFalse(st.containsAll(ar));
    }

    @Test
    public void testAddSize() throws Exception {
        st.add(2);
        st.add(3);
        st.add(9);
        assertEquals(3, st.size());
    }

    @Test
    public void testAddSameSize() throws Exception {
        st.add(2);
        st.add(3);
        st.add(2);
        assertEquals(2, st.size());
    }

    @Test
    public void testRemoveSize() throws Exception {
        st.add(2);
        st.add(3);
        st.add(9);
        st.remove(3);
        assertEquals(2, st.size());
    }

    @Test
    public void testRemoveNotExistSize() throws Exception {
        st.add(2);
        st.add(3);
        st.add(9);
        st.remove(3);
        st.remove(1);
        assertEquals(2, st.size());
    }

    @Test
    public void testToArraySorted1() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        Integer[] arr = st.toArray(new Integer[3]);
        assertTrue(arr[0] < arr[1]);
    }

    @Test
    public void testToArraySorted2() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        Integer[] arr = st.toArray(new Integer[3]);
        assertTrue(arr[1] < arr[2]);
    }

    @Test
    public void testToArraySize() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        Object[] arr = st.toArray();
        assertEquals(3, arr.length);
    }

    @Test
    public void testIteratorIterationNumber() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.add(17);
        st.add(228);
        st.add(-22334);
        int ct = 0;
        for (Integer e: st){
            ct++;
        }
        assertEquals(6, ct);
    }

    @Test
    public void testDescendingIteratorIterationNumber() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.add(17);
        st.add(228);
        st.add(-22334);
        int ct = 0;
        Iterator<Integer> iterator = st.descendingIterator();
        while (iterator.hasNext()) {
            iterator.next();
            ct++;
        }
        assertEquals(6, ct);
    }

    @Test
    public void testDescendingIterator() throws Exception {
        st.add(2);
        st.add(3);
        st.add(4);
        Iterator<Integer> iterator = st.descendingIterator();
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
        st.add(1);
        st.add(-5);
        st.add(3);
        st.add(17);
        st.add(228);
        st.add(-22334);
        assertEquals(228, st.descendingSet().first());
    }

    @Test
    public void testDescendingSetLast() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.add(17);
        st.add(228);
        st.add(-22334);
        assertEquals(-22334, st.descendingSet().last());
    }

    @Test
    public void testDescendingSetLower() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.add(17);
        st.add(228);
        st.add(-22334);
        assertEquals(1, st.descendingSet().lower(-5));
    }

    @Test
    public void testDescendingSetHigher() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.add(17);
        st.add(228);
        st.add(-22334);
        assertEquals(-5, st.descendingSet().higher(1));
    }

    @Test
    public void testDescendingSetFloor() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.add(17);
        st.add(228);
        st.add(-22334);
        assertEquals(-5, st.descendingSet().floor(-5));
    }

    @Test
    public void testDescendingSetCeiling() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.add(17);
        st.add(228);
        st.add(-22334);
        assertEquals(1, st.descendingSet().ceiling(1));
    }

    @Test
    public void testDescendingSetIterator() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.add(17);
        st.add(228);
        st.add(-22334);
        int a = st.descendingIterator().next();
        int b = st.descendingSet().iterator().next();
        assertEquals(a, b);
    }

    @Test
    public void testDescendingSetDescendingIterator() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.add(17);
        st.add(228);
        st.add(-22334);
        int a = st.iterator().next();
        int b = st.descendingSet().descendingIterator().next();
        assertEquals(a, b);
    }

    @Test
    public void testDescendingSetDescendingSet() throws Exception {
        st.add(1);
        st.add(-5);
        st.add(3);
        st.add(17);
        st.add(228);
        st.add(-22334);
        MyTreeSet st1 = st.descendingSet();
        st1.add(22);
        MyTreeSet st2 = st1.descendingSet();
        assertEquals(22, st2.higher(20));
    }

    @Test
    public void testComparator() throws Exception {
        TreapMyTreeSet<String> st1 = new TreapMyTreeSet<String>((s1, s2) -> s1.length() - s2.length());
        st1.add("aba");
        st1.add("caba");
        st1.add("ad");
        assertEquals("ad", st1.first());
    }
}