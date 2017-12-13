package com.github.myutman.java;

/**
 * Created by myutman on 12/13/17.
 */
public class B implements Interface1 {

    static int ct = 0;

    public B(){
        count();
        System.out.println("Created B");
    }

    public static void init() {
        ct = 0;
    }

    public static void count(){
        ct++;
    }
}
