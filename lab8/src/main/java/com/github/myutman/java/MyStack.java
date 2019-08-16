package com.github.myutman.java;

import java.util.ArrayList;

/**
 * Created by myutman on 11/28/17.
 *
 * Implementation of interface Stack with ArrayList.
 */
public class MyStack<T> implements Stack<T>{

    private ArrayList<T> arrayList = new ArrayList<>();
    private int count = 0;

    @Override
    public void push(T element) {
        if (element == null) {
            throw new UnsupportedOperationException();
        }
        if (arrayList.size() == count) {
            arrayList.add(element);
            count++;
        } else {
            arrayList.set(count++, element);
        }
    }

    @Override
    public void pop() {
        if (count > 0) {
            count--;
        }
    }

    @Override
    public T top() {
        if (count == 0) {
            return null;
        }
        return arrayList.get(count - 1);
    }

    @Override
    public boolean empty() {
        return count == 0;
    }

    @Override
    public void clear() {
        count = 0;
        arrayList.clear();
    }
}
