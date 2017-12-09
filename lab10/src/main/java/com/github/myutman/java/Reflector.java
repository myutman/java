package com.github.myutman.java;

import jdk.internal.org.objectweb.asm.Type;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

/**
 * Created by myutman on 12/6/17.
 */
public class Reflector {

    public static void main(String[] args) {
        printStructure(ArrayList.class);
    }

    public static void printStructure(Class<?> someClass) {
        try (PrintWriter out = new PrintWriter(new File(someClass.getSimpleName() + ".java"))) {
            out.println(Modifier.toString(someClass.getModifiers()) + " class " + someClass.getSimpleName() + " {");
            Field[] fields = someClass.getFields();
            for (Field field : fields) {
                out.print("\t" + Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName() + " " + field.getName());
                if (Modifier.isFinal(field.getModifiers())){
                    if (field.getType().equals(Character.TYPE) || field.getType().equals(Byte.TYPE) || field.getType().equals(Short.TYPE) ||
                            field.getType().equals(Integer.TYPE) || field.getType().equals(Float.TYPE) || field.getType().equals(Double.TYPE)) {
                        out.print(" = 0");
                    } else if (field.getType().equals(Boolean.TYPE)) {
                        out.print(" = false");
                    } else {
                        out.print(" = null");
                    }
                }
                out.println(";");
            }
            Method[] methods = someClass.getMethods();
            for (Method method : methods) {
                out.print("\t" + Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getSimpleName() + " " + method.getName() + "(");
                TypeVariable[] argtypes = method.getTypeParameters();
                for (int i = 0; i < argtypes.length; i++) {
                    out.print(argtypes[i].getName() + " arg" + i);
                    if (i + 1 != argtypes.length) {
                        out.print(", ");
                    }
                }
                out.print(") { return");
                if (method.getReturnType().equals(Character.TYPE) || method.getReturnType().equals(Byte.TYPE) || method.getReturnType().equals(Short.TYPE) ||
                        method.getReturnType().equals(Integer.TYPE) || method.getReturnType().equals(Float.TYPE) || method.getReturnType().equals(Double.TYPE)) {
                    out.print(" 0");
                } else if (method.getReturnType().equals(Boolean.TYPE)) {
                    out.print(" false");
                } else if (method.getReturnType().equals(Void.TYPE)) {
                    out.print("");
                } else {
                    out.print(" null");
                }
                out.println("; }");
            }
            out.println("}");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
