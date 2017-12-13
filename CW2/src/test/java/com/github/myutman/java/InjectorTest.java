package com.github.myutman.java;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by myutman on 12/13/17.
 */
public class InjectorTest {

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFromStatements(){
        A obj = (A) Injector.initialize(A.class.getName(), Arrays.asList(new Class[]{A.class, B.class}));
        assertNotNull(obj);
    }

    @Test
    public void testAmbiguous(){
        try {
            A obj = (A) Injector.initialize(A.class.getName(), Arrays.asList(new Class[]{A.class, B.class, C.class}));
            throw new RuntimeException();
        } catch (AmbiguousImplementationException e){
            System.out.println("Ok, ambiguous.");
        }
    }

    @Test
    public void testMultipleCalls(){
        B.init();
        D obj = (D) Injector.initialize(D.class.getName(), Arrays.asList(new Class[]{A.class, B.class, D.class}));
        assertEquals(B.getCt(), 1);
    }

    @Test
    public void testCycleDependency(){
        try {
            A obj = (A) Injector.initialize(A.class.getName(), Arrays.asList(new Class[]{A.class, E.class}));
            throw new RuntimeException();
        } catch (InjectionCycleException e){
            System.out.println("Ok, cycle.");
        }
    }

    @Test
    public void testNotImplemented(){
        try {
            A obj = (A) Injector.initialize(A.class.getName(), Arrays.asList(new Class[]{A.class, F.class}));
            throw new RuntimeException();
        } catch (ImplementationNotFoundException e){
            System.out.println("Ok, not implemented.");
        }
    }
}