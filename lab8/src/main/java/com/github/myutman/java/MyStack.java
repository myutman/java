package com.github.myutman.java;

import java.util.ArrayList;

/**
 * Implementation of interface Stack with ArrayList.
 */
public class MyStack<T> implements Stack<T>{

    ArrayList<T> arrayList = new ArrayList<T>();
    int ct = 0;

    @Override
    public void push(T element) {
        if (arrayList.size() == ct){
            arrayList.add(element);
            ct++;
        } else {
            arrayList.set(ct++, element);
        }
    }

    @Override
    public void pop() {
        if (ct > 0) ct--;
    }

    @Override
    public T top() {
        if (ct == 0)
            return null;
        return arrayList.get(ct - 1);
    }

    @Override
    public boolean empty() {
        return ct == 0;
    }

    @Override
    public void clear() {
        ct = 0;
    }
}
