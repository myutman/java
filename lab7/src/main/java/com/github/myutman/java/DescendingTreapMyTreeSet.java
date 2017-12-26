package com.github.myutman.java;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by myutman on 11/14/17.
 *
 * Descending version of TreapMyTreeSet.
 */
public class DescendingTreapMyTreeSet<E> extends TreapMyTreeSet<E> {

    /**
     * @param parent - regular TreapMyTreeSet
     *
     * Constructor of descending version of TreapMyTreeSet given as an argument.
     */
    public DescendingTreapMyTreeSet (TreapMyTreeSet<E> parent){
        root = parent.root;
        size = parent.size;
        comparator = parent.comparator;
    }

    /**
     * {@link TreeSet#descendingIterator()}
     **/
    public Iterator<E> descendingIterator() {
        return super.iterator();
    }

    /**
     * {@link TreeSet#descendingSet()}
     **/
    public MyTreeSet<E> descendingSet() {
        return new TreapMyTreeSet<E>(this);
    }

    /**
     * {@link TreeSet#first()}
     **/
    public E first() {
        return super.last();
    }

    /**
     * {@link TreeSet#last()}
     **/
    public E last() {
        return super.first();
    }

    /**
     * {@link TreeSet#lower(E)}
     *
     * @param e
     **/
    public E lower(E e) {
        return super.higher(e);
    }

    /**
     * {@link TreeSet#floor(E)}
     *
     * @param e
     **/
    public E floor(E e) {
        return super.ceiling(e);
    }

    /**
     * {@link TreeSet#ceiling(E)}
     *
     * @param e
     **/
    public E ceiling(E e) {
        return super.floor(e);
    }

    /**
     * {@link TreeSet#higher(E)}
     *
     * @param e
     **/
    public E higher(E e) {
        return super.lower(e);
    }

    public Iterator<E> iterator() {
        return super.descendingIterator();
    }
}
