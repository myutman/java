package com.github.myutman.java;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class MD5Test {

    byte[] expected;
    File resources;

    @Before
    public void before() {
        File file = new File("CW1/src/test/resources/dir/file.dat");
        resources = new File("CW1/src/test/resources");
        file.getParentFile().mkdirs();
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            return;
        }
        byte[] bytes = new byte[]{3, 2, 2, 8, 3, 9};
        try {
            out.write(bytes);
        } catch (IOException e) {
            return;
        }
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return;
        }
        expected = md.digest(("resources" + new String(md.digest(("dir" + new String(md.digest(bytes))).getBytes()))).getBytes());
    }

    @Test
    public void testSimple() {
        byte[] res = SimpleMD5.f(resources);
        assertEquals(expected.length, res.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], res[i]);
        }
    }

    @Test
    public void testForkJoin() {
        byte[] res = ForkJoinMD5.f(resources);
        assertEquals(expected.length, res.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], res[i]);
        }
    }
}
