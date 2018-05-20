package com.github.myutman.java;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {

    String NOT_IGNORED = "NOT IGNORED";

    class EmptyException extends Throwable {
        private EmptyException() { }
    }

    Class<? extends Throwable> expected() default EmptyException.class;
    String ignore() default NOT_IGNORED;
}
