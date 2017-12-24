package com.github.myutman.java;

import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * Created by myutman on 10/10/17.
 */
public class RegExZipperTest {

    /**
     * Tests RegExZipper functionality on the resource directory and checks correctness.
     */
    @org.junit.Test
    public void testMain() throws Exception {
        RegExZipper.main(new String[]{"src/main/resources", ".*tx.*"});
        Scanner scanner = new Scanner(new File("src/main/resources/input.txt"));
        String s1 = scanner.next();
        assertEquals("abacaba", s1);
    }
}