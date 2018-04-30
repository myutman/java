package com.github.myutman.java;

import com.sun.org.apache.xpath.internal.operations.Mod;
import sun.management.MethodInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by myutman on 12/6/17.
 *
 * Class uses reflection to print class or compare two classes.
 */
public class Reflector {

    public static void main(String[] args) {
        printStructure(com.github.myutman.java.testclasses.A.class);
        diffClasses(ArrayList.class, AbstractList.class);
    }

    /**
     * @param someClass class needed to be printed
     * Prints class with all fields, methods and subclasses.
     */
    public static void printStructure(Class<?> someClass) {
        File path = new File("src/main/java/my/pack/");
        System.out.println(path.getAbsolutePath());
        System.out.println(path.mkdirs());
        try (PrintWriter out = new PrintWriter(new File("src/main/java/my/pack/" + someClass.getSimpleName() + ".java"))) {
            out.println("package my.pack;\n");
            printStructureBlock(out, someClass, 0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String templateParamsOfClass(Class<?> someClass) {
        if (someClass.getTypeParameters().length == 0)
            return "";
        return Arrays.stream(someClass.getTypeParameters()).map(TypeVariable::getName).collect(Collectors.joining(",", "<", ">"));
    }

    private static String templateParamsOfMethod(Method method) {
        if (method.getTypeParameters().length == 0)
            return " ";
        return Arrays.stream(method.getTypeParameters()).map(TypeVariable::getName).collect(Collectors.joining(",", " <", "> "));
    }

    private static String methodArgs(Method method) {
        return Arrays.stream(method.getGenericParameterTypes()).map(new Function<Type, String>() {

            int ct = 0;

            /**
             * Applies this function to the given argument.
             *
             * @param type the function argument
             * @return the function result
             */
            @Override
            public String apply(Type type) {
                return type.getTypeName() + " arg" + (ct++);
            }
        }).collect(Collectors.joining(", ", "(", ")"));
    }

    private static String getTabs(int tabs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < tabs; i++) {
            stringBuilder.append('\t');
        }
        return stringBuilder.toString();
    }

    private static void printField(PrintWriter out, Field field, int tabs){
        out.print(getTabs(tabs + 1) + Modifier.toString(field.getModifiers()) + " " + field.getGenericType().getTypeName() + " " + field.getName());
        if (Modifier.isFinal(field.getModifiers())) {
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

    private static void printMethod(PrintWriter out, Method method, int tabs){
        if (Modifier.isNative(method.getModifiers()) || Modifier.isFinal(method.getModifiers()))
            return;
        Type returnType = method.getGenericReturnType();
        method.getGenericParameterTypes();
        out.print(getTabs(tabs + 1) + Modifier.toString(method.getModifiers()) + templateParamsOfMethod(method) + returnType.getTypeName() + " " + method.getName());
        out.print(methodArgs(method));
        if (Modifier.isAbstract(method.getModifiers())) {
            out.println(";");
            return;
        }
        out.print(" { return");
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

    private static void printStructureBlock(PrintWriter out, Class<?> someClass, int tabs) {
        out.print(getTabs(tabs) + Modifier.toString(someClass.getModifiers()) + (someClass.isInterface() ? " " : " class ") + someClass.getSimpleName());

        out.print(templateParamsOfClass(someClass));

        if (!someClass.getGenericSuperclass().equals(Object.class)) {
            out.print(" extends " + someClass.getGenericSuperclass().getTypeName());
        }

        if (someClass.getGenericInterfaces().length != 0) {
            out.print(Arrays.stream(someClass.getInterfaces()).map(t -> t.getCanonicalName() + templateParamsOfClass(t)).collect(Collectors.joining(", ", " implements ", "")));
        }

        out.println(" {");

        for (Class clazz: someClass.getClasses()) {
            printStructureBlock(out, clazz, tabs + 1);
        }

        for (Field field : someClass.getDeclaredFields()) {
            System.out.println("hello");
            printField(out, field, tabs);
        }

        for (Method method : someClass.getDeclaredMethods()) {
            printMethod(out, method, tabs);
        }
        out.println(getTabs(tabs) + "}");
    }

    /**
     * @param a first class
     * @param b second class
     * Compares two classes and prints unique fields and methods of each.
     */
    public static void diffClasses(Class<?> a, Class<?> b) {
        Field[] fieldsA = a.getFields();
        Field[] fieldsB = b.getFields();
        PrintWriter out = new PrintWriter(System.out);
        for (Field field: fieldsA) {
            if (Arrays.stream(fieldsB).noneMatch(f -> fieldsAreEqual(f, field))) {
                out.print("A: ");
                printField(out, field, 0);
            }
        }
        for (Field field: fieldsB) {
            if (Arrays.stream(fieldsA).noneMatch(f -> fieldsAreEqual(f, field))) {
                out.print("B: ");
                printField(out, field, 0);
            }
        }

        Method[] methodsA = a.getMethods();
        Method[] methodsB = b.getMethods();
        for (Method method: methodsA) {
            if (Arrays.stream(methodsB).noneMatch(m -> methodsAreEqual(m, method))) {
                out.print("A: ");
                printMethod(out, method, 0);
            }
        }
        for (Method method: methodsB) {
            if (Arrays.stream(methodsA).noneMatch(m -> methodsAreEqual(m, method))) {
                out.print("B: ");
                printMethod(out, method, 0);
            }
        }
        out.close();
    }

    private static boolean fieldsAreEqual(Field f, Field field) {
        if (!f.getName().equals(field.getName())) return false;
        Type type1 = f.getGenericType();
        Type type2 = field.getGenericType();
        return typesAreEqual(type1, type2);
    }

    private static boolean methodsAreEqual(Method m, Method method) {
        if (!m.getName().equals(method.getName()) ||
                !typesAreEqual(m.getGenericReturnType(), method.getGenericReturnType()) ||
                m.getGenericParameterTypes().length != method.getGenericParameterTypes().length) return false;
        Type[] types1 = m.getGenericParameterTypes();
        Type[] types2 = method.getGenericParameterTypes();
        for (int i = 0; i < types1.length; i++){
            if (!typesAreEqual(types1[i], types2[i])) return false;
        }
        return true;
    }

    private static boolean typesAreEqual(Type type1, Type type2){
        return type1.getTypeName().equals(type2.getTypeName());
    }
}
