package com.github.myutman.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.*;

/**
 * Created by myutman on 10/10/17.
 */
public class RegExZipperTest {

    /**
     * @throws Exception
     *
     * Tests RegExZipper functionality on the resource directory and checks correctness.
     */
    @org.junit.Test
    public void testMain() throws Exception {
        new RegExZipper().main(new String[]{"src/main/resources", ".*tx.*"});
        Scanner scanner = new Scanner(new File("src/main/resources/input.txt"));
        String s1 = scanner.next();
        assertEquals("abacaba", s1);
    }
}