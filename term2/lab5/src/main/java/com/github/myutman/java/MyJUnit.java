package com.github.myutman.java;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;


/**
 * Console application class that executes all tests by given path.
 */
public class MyJUnit {

    static PrintStream out = System.out;

    /**
     * Class constructor that takes output print stream to write to.
     * @param out output print stream
     */
    MyJUnit(PrintStream out) {
        MyJUnit.out = out;
    }

    /**
     * What to do if test is passed.
     * @param name name of test
     */
    public static void testPass(String name) {
        out.println("passed: " + name);
    }

    /**
     * What to do if test is failed.
     * @param name name of test
     */
    public static void testFail(String name) {
        out.println("failed: " + name);
    }

    /**
     * Runs all tests in given class.
     * @param clazz given class
     */
    public static void runTests(Class<?> clazz) {
        Object instance;
        try {
            instance = clazz.newInstance();
        } catch (IllegalAccessException e) {
            return;
        } catch (InstantiationException e) {
            return;
        }
        ArrayList<Method> befores = new ArrayList<>();
        ArrayList<Method> afters = new ArrayList<>();
        for (Method method: clazz.getMethods()) {
            if (method.getAnnotation(Before.class) != null) {
                befores.add(method);
            }
            if (method.getAnnotation(After.class) != null) {
                afters.add(method);
            }
            if (method.getAnnotation(BeforeClass.class) != null) {
                try {
                    method.invoke(instance);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        for (Method method: clazz.getMethods()) {
            Test test = method.getAnnotation(Test.class);
            if (test != null){
                boolean testPassed = true;
                String ignore = test.ignore();
                if (!ignore.equals(Test.NOT_IGNORED)) {
                    System.out.println(ignore);
                    continue;
                }
                for (Method before: befores) {
                    try {
                        before.invoke(instance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    method.invoke(instance);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (Throwable e) {
                    if (!test.expected().isInstance(e)) {
                        testPassed = false;
                    }
                }
                for (Method after: afters) {
                    try {
                        after.invoke(instance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                if (testPassed) {
                    testPass(method.getName());
                } else {
                    testFail(method.getName());
                }
            }
        }
        for (Method method: clazz.getMethods()) {
            if (method.getAnnotation(AfterClass.class) != null) {
                try {
                    method.invoke(instance);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Console application class that executes all tests by given path.
     * @param args [0] is given path
     */
    public static void main(String[] args) {
        File dir = new File(args[0]);
        URL url;
        try {
            url = new URL("file:" + args[0]);
        } catch (MalformedURLException e) {
            return;
        }
        URLClassLoader loader = new URLClassLoader(new URL[]{url});
        for (File classFile: FileUtils.listFiles(dir, new String[]{"class"}, true)) {
            String name = dir.toPath().relativize(classFile.toPath()).toString();
            name = name.split("\\.class")[0].replace(File.separatorChar, '.');
            try {
                Class<?> clazz = loader.loadClass(name);
                runTests(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
