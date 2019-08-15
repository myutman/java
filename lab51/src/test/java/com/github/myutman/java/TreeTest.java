package com.github.myutman.java;

import org.junit.Assert;

import static org.junit.Assert.*;

/**
 * Created by myutman on 10/17/17.
 *
 * Binary search tree test.
 */
public class TreeTest {

    private Tree<Integer> tr = new Tree<>();

    /**
     * Adds many elements and checks containing and size.
     */
    @org.junit.Test
    public void add() throws Exception {
        tr.add(7);
        tr.add(4);
        tr.add(8);
        tr.add(4);
        tr.add(7);
        tr.add(9);
        tr.add(24);
        tr.add(2048);
        tr.add(-15);
        tr.add(24);
        tr.add(322);
        Assert.assertTrue(tr.contains(7));
        Assert.assertTrue(tr.contains(-15));
        Assert.assertTrue(tr.contains(2048));
        Assert.assertTrue(tr.contains(322));
        Assert.assertFalse(tr.contains(6));
        Assert.assertFalse(tr.contains(228));
        Assert.assertFalse(tr.contains(239));
        Assert.assertFalse(tr.contains(2049));
        Assert.assertEquals(8, tr.size());
    }

    /**
     * Adds many elements and checks containing.
     */
    @org.junit.Test
    public void contains() throws Exception {
        Assert.assertFalse(tr.contains(7));
        tr.add(7);
        Assert.assertTrue(tr.contains(7));
        tr.add(4);
        Assert.assertFalse(tr.contains(8));
        tr.add(8);
        Assert.assertTrue(tr.contains(8));
        tr.add(4);
        tr.add(7);
        Assert.assertTrue(tr.contains(7));
        Assert.assertFalse(tr.contains(9));
        tr.add(9);
        Assert.assertTrue(tr.contains(9));
        Assert.assertTrue(tr.contains(4));
        Assert.assertFalse(tr.contains(5));
    }

    /**
     * Adds many elements and checks size.
     */
    @org.junit.Test
    public void size() throws Exception {
        Assert.assertEquals(0, tr.size());
        tr.add(7);
        Assert.assertEquals(1, tr.size());
        tr.add(4);
        Assert.assertEquals(2, tr.size());
        tr.add(8);
        Assert.assertEquals(3, tr.size());
        tr.add(4);
        Assert.assertEquals(3, tr.size());
        tr.add(7);
        Assert.assertEquals(3, tr.size());
        tr.add(9);
        Assert.assertEquals(4, tr.size());
    }

}