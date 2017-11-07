package com.github.myutman.java;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by myutman on 11/7/17.
 */
public class Collections {

    /**
     * @param f - function with one argument
     * @param a - container
     * Applies function f to each element a
     */
    public static <T extends Iterable<K>, S, K> ArrayList map(Function1<? super K, S> f, T a) {
        ArrayList<S> arrayList = new ArrayList();
        for (K v : a) {
            arrayList.add(f.apply(v));
        }
        return arrayList;
    }

    /**
     * @param p - predicate
     * @param a - container
     * Takes only such elements of container a that satisfy given predicate f.
     */
    public static <T extends Iterable<K>, K> ArrayList filter(Predicate<? super K> p, T a) {
        ArrayList<K> arrayList = new ArrayList();
        for (K v : a) {
            if (p.apply(v))
                arrayList.add(v);
        }
        return arrayList;
    }

    /**
     * @param p - predicate
     * @param a - container
     * Takes elements while they satisfy given predicate.
     */
    public static <T extends Iterable<K>, K> ArrayList takeWhile(Predicate<? super K> p, T a) {
        ArrayList<K> arrayList = new ArrayList();
        for (K v : a) {
            if (!p.apply(v))
                break;
            arrayList.add(v);
        }
        return arrayList;
    }

    /**
     * @param p - predicate
     * @param a - container
     * Takes elements while they don't satisfy given predicate.
     */
    public static <T extends Iterable<K>, K> ArrayList takeUnless(Predicate<? super K> p, T a) {
        ArrayList<K> arrayList = new ArrayList();
        for (K v : a) {
            if (p.apply(v))
                break;
            arrayList.add(v);
        }
        return arrayList;
    }

    private static <S, K> S helper(Function2<? super K, S, S> f, S start, Iterator<K> iterator){
        if (iterator.hasNext()){
            K arg = iterator.next();
            return f.apply(arg, helper(f, start, iterator));
        }
        return start;
    }

    /**
     * @param f - function with two arguments
     * @param a - container
     * @param start - starting value
     * Folds given container from the right.
     */
    public static <T extends Iterable<K>, S, K> S foldr(Function2<? super K, S, S> f, S start, T a) {
        ArrayList<K> arrayList = new ArrayList();
        return helper(f, start, a.iterator());
    }

    /**
     * @param f - function with two arguments
     * @param a - container
     * @param start - starting value
     * Folds given container from the left.
     */
    public static <T extends Iterable<K>, S, K> S foldl(Function2<S, ? super K, S> f, S start, T a) {
        ArrayList<K> arrayList = new ArrayList();
        for (K v : a) {
            start = f.apply(start, v);
        }
        return start;
    }
}
