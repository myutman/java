package com.github.myutman.java;

import com.sun.org.apache.xml.internal.security.algorithms.implementations.IntegrityHmac;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

/**
 * Created by myutman on 11/28/17.
 */
public class Calculator {

    private Map<String, BinaryOperator<Integer>> operation = new HashMap<>();
    private Stack<Integer> stack;

    /**
     * Calculator constructor
     *
     * @param stack - stack that will be used in calculations
     */
    public Calculator(Stack<Integer> stack){
        this.stack = stack;
        operation.put("+", (x,y) -> x + y);
        operation.put("-", (x,y) -> x - y);
        operation.put("*", (x,y) -> x * y);
        operation.put("/", (x,y) -> x / y);
    }


    /**
     * Calculates the result of given expression.
     *
     * @param list - postfix polish notation, each element is either integer number or operation written as a String
     * @return - the result of calculation
     */
    public int eval(List<String> list){
        stack.clear();
        for (String element: list){
            try {
                int x = Integer.parseInt(element);
                stack.push(x);
            } catch (NumberFormatException e){
                int a = stack.top();
                stack.pop();
                int b = stack.top();
                stack.pop();
                stack.push(operation.get(element).apply(a, b));
            }
        }
        return stack.top();
    }
}
