package com.github.myutman.java;

/*import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;*/

import static org.junit.Assert.*;

/**
 * Created by myutman on 9/27/17.
 */
//@RunWith(Arquillian.class)
public class HashTableTest {

    HashTable hs = new HashTable();

    @org.junit.Test
    public void size() throws Exception {
        assertEquals(hs.size(), 0);
        hs.put("aba", "caba");
        assertEquals(hs.size(), 1);
        hs.put("aba", "daba");
        assertEquals(hs.size(), 1);
        hs.put("aba", "caba");
        assertEquals(hs.size(), 1);
        hs.put("daba", "Hey, you!");
        assertEquals(hs.size(), 2);
        hs.put("Out", "there");
        assertEquals(hs.size(), 3);
        hs.put("in the", "cold!");
        assertEquals(hs.size(), 4);
        hs.clear();
        assertEquals(hs.size(), 0);
    }

    @org.junit.Test
    public void contains() throws Exception {
    }

    @org.junit.Test
    public void get() throws Exception {
        hs.put("Out", "there");
        hs.put("in the", "cold!");
        hs.size();
        assertEquals(hs.get("int the"), null);
        assertEquals(hs.get("in the"), "cold!");
        hs.clear();
    }

    @org.junit.Test
    public void put() throws Exception {
        assertEquals(hs.put("aba", "caba"), null);
        assertEquals(hs.put("aba", "daba"), "caba");
        assertEquals(hs.put("aba", "caba"), "daba");
        assertEquals(hs.put("daba", "Hey, you!"), null);
        assertEquals(hs.put("Out", "there"), null);
        assertEquals(hs.put("in the", "cold!"), null);
        assertEquals(hs.size(), 4);
        hs.clear();
    }

    @org.junit.Test
    public void remove() throws Exception {
        hs.put("Out", "there");
        hs.put("in the", "cold!");
        assertEquals(hs.remove("out"), null);
        assertEquals(hs.remove("Out"), "there");
        hs.clear();
    }

    @org.junit.Test
    public void clear() throws Exception {
        hs.put("aba", "caba");
        hs.put("daba", "Hey, you!");
        hs.put("Out", "there");
        hs.put("in the", "cold!");
        assertEquals(hs.get("aba"), "caba");
        assertEquals(hs.get("daba"), "Hey, you!");
        assertEquals(hs.get("Out"), "there");
        assertEquals(hs.get("in the"), "cold!");
        hs.clear();
        assertEquals(hs.get("aba"), null);
        assertEquals(hs.get("daba"), null);
        assertEquals(hs.get("Out"), null);
        assertEquals(hs.get("in the"), null);
    }

    /*@Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(HashTable.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }*/

}
