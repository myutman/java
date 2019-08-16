package com.github.myutman.java;

/**
 * Created by myutman on 11/28/17.
 *
 * Stack with operations push, pop and top.
 */
public interface Stack<T> {
    /**
     * Adds new element to the top of the stack.
     * @param element new element in stack
     */
    void push(T element);

    /**
     * Deletes element from the top of the stack.
     */
    void pop();

    /**
     * Returns top element of the stack.
     * @return top element of the stack
     */
    T top();

    /**
     * Checks whether stack is empty.
     * @return true if stack is empty and false otherwise
     */
    boolean empty();

    /**
     * Clears stack.
     */
    void clear();
}
