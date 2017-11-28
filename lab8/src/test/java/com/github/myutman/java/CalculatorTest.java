package com.github.myutman.java;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by myutman on 11/28/17.
 */
public class CalculatorTest {

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSimplestCalculation() throws Exception {
        Stack mockStack = mock(Stack.class);
        when(mockStack.top()).thenReturn(-42);

        Calculator calc = new Calculator(mockStack);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("-42");

        assertEquals(-42, calc.eval(arrayList));
        verify(mockStack, times(1)).top();
        InOrder inOrder = inOrder(mockStack);
        inOrder.verify(mockStack).push(-42);
    }

    @Test
    public void testSimpleCalculation() throws Exception {
        Stack mockStack = mock(Stack.class);
        when(mockStack.top()).thenReturn(-13, -42, 546);

        Calculator calc = new Calculator(mockStack);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("-42");
        arrayList.add("-13");
        arrayList.add("*");

        assertEquals(546, calc.eval(arrayList));
        verify(mockStack, times(3)).top();
        InOrder inOrder = inOrder(mockStack);
        inOrder.verify(mockStack).push(-42);
        inOrder.verify(mockStack).push(-13);
        inOrder.verify(mockStack).push(546);
    }

    @Test
    public void testComplexCalculation() throws Exception {
        Stack mockStack = mock(Stack.class);
        when(mockStack.top()).thenReturn(3,4, 2, -1, 1, -2, 3);

        Calculator calc = new Calculator(mockStack);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("4");
        arrayList.add("3");
        arrayList.add("-");
        arrayList.add("2");
        arrayList.add("*");
        arrayList.add("1");
        arrayList.add("-");

        assertEquals(3, calc.eval(arrayList));
        verify(mockStack, times(7)).top();
        InOrder inOrder = inOrder(mockStack);
        inOrder.verify(mockStack).push(4);
        inOrder.verify(mockStack).push(3);
        inOrder.verify(mockStack).push(-1);
        inOrder.verify(mockStack).push(2);
        inOrder.verify(mockStack).push(-2);
        inOrder.verify(mockStack).push(1);
        inOrder.verify(mockStack).push(3);
    }

}