package com.github.myutman.java.testclasses;

/**
 * Created by myutman on 12/21/17.
 */
public class A {
    static int ct = 0;
    private int z = 0;

    public A(){
        z = 3;
    }

    public static void doSome(){
        ct++;
    }

    public int getZ(){
        ct++;
        return z;
    }
}
