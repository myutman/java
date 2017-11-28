package com.github.myutman.java;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by myutman on 11/28/17.
 */
public class MyStackTest {

    MyStack<Integer> st;

    @Before
    public void before(){
        st = new MyStack<>();
    }

    @Test
    public void testPopFromEmpty() throws Exception {
        st.pop();
        st.push(2);
        assertFalse(st.empty());
    }

    @Test
    public void testTopEmpty() throws Exception {
        assertNull(st.top());
    }

    @Test
    public void testCorrectOnTopBig() throws Exception {
        st.push(6);
        st.push(5);
        st.push(4);
        st.push(3);
        st.push(2);
        st.push(1);
        assertEquals(st.top(), 1);
    }

    @Test
    public void testCorrectOnTopSmall() throws Exception {
        st.push(6);
        assertEquals(st.top(), 6);
    }

    @Test
    public void testPushEqualsPop() throws Exception {
        st.push(6);
        st.push(5);
        st.push(4);
        st.push(3);
        st.push(2);
        st.push(1);
        st.pop();
        st.pop();
        st.pop();
        st.pop();
        st.pop();
        st.pop();
        assertTrue(st.empty());
    }

    @Test
    public void testOneItemLeft() throws Exception {
        st.push(6);
        st.push(5);
        st.push(4);
        st.push(3);
        st.push(2);
        st.push(1);
        st.pop();
        st.pop();
        st.pop();
        st.pop();
        st.pop();
        assertEquals(6, st.top());
    }
}