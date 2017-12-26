package com.github.myutman.java;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by myutman on 11/7/17.
 */
public class CollectionsTest {

    private ArrayList<Integer> arrayList;

    private Function1<Integer, Integer> f1 = new Function1<Integer, Integer>() {
        public Integer apply(Integer x) {
            return x * x;
        }
    };

    private Function2<Integer, Integer, Integer> f2 = new Function2<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer x, Integer y) {
            return x / y;
        }
    };

    private Function2<Integer, Integer, Integer> f3 = new Function2<Integer, Integer, Integer>() {
        public Integer apply(Integer x, Integer y) {
            return x + y;
        }
    };

    private Predicate<Integer> p = new Predicate<Integer>() {
        @Override
        public Boolean apply(Integer x) {
            return x * x <= 25;
        }
    };

    @Before
    public void before() {
        arrayList = new ArrayList<Integer>();
        arrayList.add(8);
        arrayList.add(4);
        arrayList.add(2);
    }

    @Test
    public void foldrTest() throws Exception {
        assertEquals(4, Collections.foldr(f2, 1, arrayList));
    }

    @Test
    public void foldlTest() throws Exception {
        assertEquals(1, Collections.foldl(f2, 64, arrayList));
    }

    @Test
    public void filterTest() throws Exception {
        assertEquals(2, Collections.filter(p, arrayList).size());
    }

    @Test
    public void takeWhileTest() throws Exception {
        assertEquals(1, Collections.takeWhile(p.not(), arrayList).size());
    }

    @Test
    public void takeUnlessTest() throws Exception {
        assertEquals(0, Collections.takeUnless(p.not(), arrayList).size());
    }

    @Test
    public void mapTest() throws Exception {
        assertEquals(84, Collections.foldr(f3, 0, Collections.map(f1, arrayList)));
    }
}