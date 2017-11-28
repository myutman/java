package com.github.myutman.java;

/**
 * Created by myutman on 11/28/17.
 */
public interface Stack<T> {
    public void push(T element);
    public void pop();
    public T top();
    boolean empty();
    void clear();
}
