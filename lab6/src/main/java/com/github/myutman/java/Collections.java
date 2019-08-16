package com.github.myutman.java;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by myutman on 11/7/17.
 *
 * Static transformation methods over given iterable collection.
 */
public class Collections {

    /**
     * Applies function f to each element a.
     * @param f function with one argument
     * @param a container
     */
    public static <T extends Iterable<K>, S, K> ArrayList map(Function1<? super K, S> f, T a) {
        ArrayList<S> arrayList = new ArrayList<>();
        for (K v : a) {
            arrayList.add(f.apply(v));
        }
        return arrayList;
    }

    /**
     * Takes only such elements of container a that satisfy given predicate f.
     * @param p predicate
     * @param a container
     */
    public static <T extends Iterable<K>, K> ArrayList filter(Predicate<? super K> p, T a) {
        ArrayList<K> arrayList = new ArrayList<>();
        for (K v : a) {
            if (p.apply(v)) {
                arrayList.add(v);
            }
        }
        return arrayList;
    }

    /**
     * Takes elements while they satisfy given predicate.
     * @param p predicate
     * @param a container
     */
    public static <T extends Iterable<K>, K> ArrayList takeWhile(Predicate<? super K> p, T a) {
        ArrayList<K> arrayList = new ArrayList<>();
        for (K v : a) {
            if (!p.apply(v)) {
                break;
            }
            arrayList.add(v);
        }
        return arrayList;
    }

    /**
     * Takes elements while they don't satisfy given predicate.
     * @param p predicate
     * @param a container
     */
    public static <T extends Iterable<K>, K> ArrayList takeUnless(Predicate<? super K> p, T a) {
        ArrayList<K> arrayList = new ArrayList<>();
        for (K v : a) {
            if (p.apply(v)) {
                break;
            }
            arrayList.add(v);
        }
        return arrayList;
    }

    /**
     * One recursive step of foldr operation.
     * @param f folding function
     * @param start start value
     * @param iterator iterator of given iterable collection
     * @return result of rest collection elements foldr operation
     */
    private static <S, K> S foldrStep(Function2<? super K, S, S> f, S start, Iterator<K> iterator) {
        if (iterator.hasNext()) {
            K arg = iterator.next();
            return f.apply(arg, foldrStep(f, start, iterator));
        }
        return start;
    }

    /**
     * Folds given container from the right.
     * @param f function with two arguments
     * @param a container
     * @param start starting value
     */
    public static <T extends Iterable<K>, S, K> S foldr(Function2<? super K, S, S> f, S start, T a) {
        return foldrStep(f, start, a.iterator());
    }

    /**
     * Folds given container from the left.
     * @param f function with two arguments
     * @param a container
     * @param start starting value
     */
    public static <T extends Iterable<K>, S, K> S foldl(Function2<S, ? super K, S> f, S start, T a) {
        for (K v : a) {
            start = f.apply(start, v);
        }
        return start;
    }
}
