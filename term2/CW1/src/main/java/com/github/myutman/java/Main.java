package com.github.myutman.java;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file;
        if (args.length < 1) {
            file = new File("src/test/resources");
        } else {
            file = new File(args[0]);
        }
        long timeSimple = System.currentTimeMillis();
        byte[] result = SimpleMD5.f(file);
        timeSimple = System.currentTimeMillis() - timeSimple;
        long timeForkJoin = System.currentTimeMillis();
        byte[] result1 = ForkJoinMD5.f(file);
        timeForkJoin = System.currentTimeMillis() - timeForkJoin;
        System.out.println(timeSimple / 1000.0 + " " + timeForkJoin / 1000.0);
    }
}
