package com.github.myutman.java;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test method.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {

    String NOT_IGNORED = "NOT IGNORED";

    class EmptyException extends Throwable {
        private EmptyException() { }
    }

    /**
     * Throwable class that is treated like passed test.
     * @return throwable class
     */
    Class<? extends Throwable> expected() default EmptyException.class;

    /**
     * Test is ignored telling the reason.
     * @return the reason why test is ignored
     */
    String ignore() default NOT_IGNORED;
}
