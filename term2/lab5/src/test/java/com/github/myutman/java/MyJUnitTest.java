package com.github.myutman.java;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class MyJUnitTest {

    PrintStream out;
    ByteArrayOutputStream byteOut;

    @Before
    public void init() {
        byteOut = new ByteArrayOutputStream();
        out = new PrintStream(byteOut);
    }

    @Test
    public void testTestClass() {
        new MyJUnit(out);
        new TestClass(out);
        MyJUnit.main(new String[]{"out/production/classes"});
        byte[] data = byteOut.toByteArray();
        Scanner scanner = new Scanner(new ByteArrayInputStream(data));
        assertTrue(scanner.hasNextLine());
        assertEquals(scanner.nextLine(), "before class");
        boolean[] flags = new boolean[]{false, false, false};
        for (int i = 0; i < 3; i++) {
            boolean[] befores = new boolean[]{false, false};
            boolean[] afters = new boolean[]{false, false};
            String next;
            for (int j = 0; j < 2; j++) {
                assertTrue(scanner.hasNextLine());
                next = scanner.nextLine();
                assertTrue(next.equals("before1") || next.equals("before2"));
                if (next.equals("before1")) befores[0] = true;
                else befores[1] = true;
            }
            assertTrue(scanner.hasNextLine());
            next = scanner.nextLine();
            assertTrue(next.equals("test1") || next.equals("test2") || next.equals("test3"));
            assertTrue(befores[0]);
            assertTrue(befores[1]);
            int c;
            if (next.equals("test1")) c = 0;
            else if (next.equals("test2")) c = 1;
            else c = 2;
            for (int j = 0; j < 2; j++) {
                assertTrue(scanner.hasNextLine());
                next = scanner.nextLine();
                assertTrue(next.equals("after1") || next.equals("after2"));
                if (next.equals("after1")) afters[0] = true;
                else afters[1] = true;
            }
            assertTrue(afters[0]);
            assertTrue(afters[1]);
            assertTrue(scanner.hasNextLine());
            next = scanner.nextLine();
            assertTrue(next.equals("passed: test1") || next.equals("passed: test2") || next.equals("passed: test3"));
            int c1;
            if (next.equals("passed: test1")) c1 = 0;
            else if (next.equals("passed: test2")) c1 = 1;
            else c1 = 2;
            assertEquals(c, c1);
            flags[c] = true;
        }
        assertTrue(flags[0]);
        assertTrue(flags[1]);
        assertTrue(flags[2]);
        assertTrue(scanner.hasNextLine());
        assertEquals(scanner.nextLine(), "after class");
    }
}
