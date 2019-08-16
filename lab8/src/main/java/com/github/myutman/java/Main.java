package com.github.myutman.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by myutman on 11/28/17.
 *
 * Console application that prints the value of given arithmetical expression.
 */
public class Main {

    private static Stack<Character> stackOp = new MyStack<>();
    private static Stack<Integer> stackNum = new MyStack<>();
    private static Map<Character, Integer> priority = new HashMap<>();

    static {
        priority.put('+', 1);
        priority.put('-', 1);
        priority.put('*', 2);
        priority.put('/', 2);
        priority.put('(', 0);
    }

    /**
     * Helper function that adds one operation to postfix polish notation.
     *
     * @param ans reference to postfix polish notation, to add operations to
     */
    private static void reduceTopOperation(ArrayList<String> ans) {
        if (stackNum.empty()) throw new UnsupportedOperationException();
        Integer a = stackNum.top();
        stackNum.pop();
        if (stackNum.empty()) throw new UnsupportedOperationException();
        Integer b = stackNum.top();
        stackNum.pop();
        stackNum.push(null);
        if (a != null) {
            ans.add(a.toString());
        }
        if (b != null) {
            ans.add(b.toString());
        }
        ans.add(stackOp.top().toString());
        stackOp.pop();
    }

    /**
     * Translates arithmetic expression into postfix polish notation.
     *
     * @param s arithmetic expression in normal form written as a String.
     * @return postfix polish notation of given expression
     */
    private static ArrayList<String> polish(String s) {
        ArrayList<String> ans = new ArrayList<>();
        Integer cur = null;
        for (Character c: s.toCharArray()) {
            if (Character.isDigit(c)) {
                int x = Character.getNumericValue(c);
                if (cur == null) {
                    cur = x;
                } else {
                    cur = cur * 10 + x;
                }
            } else {
                if (cur != null) {
                    stackNum.push(cur);
                    cur = null;
                }
                if (Character.isSpaceChar(c)) {
                    continue;
                }
                if (c == '(') {
                    stackOp.push(c);
                } else if (c == ')') {
                    while (!stackOp.empty() && stackOp.top() != '(') {
                        reduceTopOperation(ans);
                    }
                    if (stackOp.empty()) throw new UnsupportedOperationException();
                    stackOp.pop();
                } else {
                    if (!priority.containsKey(c)) throw new UnsupportedOperationException();
                    while (!stackOp.empty() && priority.get(c) <= priority.get(stackOp.top())) {
                        reduceTopOperation(ans);
                    }
                    stackOp.push(c);
                }
            }
        }
        if (cur != null) {
            stackNum.push(cur);
        }
        while (!stackOp.empty()) {
            reduceTopOperation(ans);
        }
        return ans;
    }

    /**
     * Console application that tells result of given arithmetic expression.
     *
     * @param args arg[0] is given expression
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Too few arguments.");
            return;
        }
        Calculator calculator = new Calculator(new MyStack<>());
        System.out.println(calculator.eval(polish(args[0])));
    }
}
