package com.github.myutman.java;


import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by myutman on 12/13/17.
 */
public class Injector {

    private static Set<String> was = new HashSet<>();
    private static Map<String, Object> classInstance = new HashMap<>();

    public static Object initialize(String rootClassName, Iterable<Class> collection) {
        was.clear();
        classInstance.clear();
        try {
            return getObs(rootClassName, collection);
        } catch (Exception e){
            throw e;
        }
    }

    public static Object getObs(String rootClassName, Iterable<Class> collection){
        if (classInstance.containsKey(rootClassName)){
            return classInstance.get(rootClassName);
        }
        if (was.contains(rootClassName)){
            throw new InjectionCycleException();
        }
        was.add(rootClassName);
        Class rootClass = null;
        for (Class cl: collection) {
            if (cl.getName().equals(rootClassName)) {
                rootClass = cl;
                break;
            }
        }
        Constructor constructor = rootClass.getConstructors()[0];
        ArrayList<Object> instances = new ArrayList<>();
        for (Class cl: constructor.getParameterTypes()){
            int ct = 0;
            for (Class implementer: collection){
                if (cl.isAssignableFrom(implementer)){
                    ct++;
                    try {
                        instances.add(Injector.getObs(implementer.getName(), collection));
                    } catch (Exception e) {
                        was.clear();
                        throw e;
                    }
                }
            }
            if (ct < 1){
                was.clear();
                throw new ImplementationNotFoundException();
            }
            if (ct > 1){
                was.clear();
                throw new AmbiguousImplementationException();
            }
        }
        try {
            was.remove(rootClassName);
            Object instance = constructor.newInstance(instances.toArray());
            classInstance.put(rootClassName, instance);
            return instance;
        } catch (Exception e) {
            was.clear();
            throw new ImplementationNotFoundException();
        }
    }
}
