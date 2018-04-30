package com.github.myutman.java;

import org.junit.Test;
import com.github.myutman.java.testclasses.A;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import static org.junit.Assert.*;

/**
 * Created by myutman on 12/21/17.
 */
public class ReflectorTest {
    /**
     * Makes copy of class A with method Reflector.printStructure and compiles it and old class A. Then compares them.
     */
    @Test
    public void test() throws Exception{
        Reflector.printStructure(A.class);
        Runtime.getRuntime().exec("javac src/main/java/my/pack/A.java").waitFor();
        Runtime.getRuntime().exec("javac src/main/java/com/github/myutman/java/testclasses/A.java").waitFor();


        File clazz = new File("src/main/java/my/pack/A.class");
        URL jar = new URL("file://" + clazz.getAbsolutePath());

        ClassLoader cl = new URLClassLoader(new URL[]{jar});

        Class A1 = cl.loadClass("my.pack.A");
        Reflector.diffClasses(A.class, A1);
    }
}