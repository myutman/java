package com.github.myutman.java;

import java.io.PrintStream;

public class TestClass {

    static PrintStream out = System.out;

    TestClass() {

    }

    TestClass(PrintStream out) {
        TestClass.out = out;
    }

    @Before
    public void before1() {
        out.println("before1");
    }

    @Before
    public void before2() {
        out.println("before2");
    }

    @BeforeClass
    public void beforeClass() {
        out.println("before class");
    }

    @Test
    public void test1() {
        out.println("test1");
    }

    @Test
    public void test2() {
        out.println("test2");
    }

    @Test
    public void test3() {
        out.println("test3");
    }

    @After
    public void after1() {
        out.println("after1");
    }

    @After
    public void after2() {
        out.println("after2");
    }

    @AfterClass
    public void afterClass() {
        out.println("after class");
    }
}
