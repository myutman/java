package com.github.myutman.java;

import java.util.*;

/**
 * Created by myutman on 11/13/17.
 *
 * Implementation of MyTreeSet with Treap.
 */
public class TreapMyTreeSet<E> extends AbstractSet<E> implements MyTreeSet<E> {

    protected Node<E> root;
    protected int size;
    protected Comparator<? super E> comparator = null;

    /**
     * Constructor of TreapMyTreeSet of comparable E that compares elements with compareTo
     */
    public <T extends Comparable<T>> TreapMyTreeSet() {
        root = null;
        size = 0;
    }

    /**
     * Constructor of TreapMyTreeSet that compares elements with given comparator.
     * @param comparator - comparator to compare elements.
     */
    public TreapMyTreeSet(Comparator<? super E> comparator) {
        root = null;
        size = 0;
        this.comparator = comparator;
    }

    /**
     * Constructor of TreapMyTreeSet that is descending to a given DescendingSet
     * @param parent - descending set we want to get DescendingSet to
     */
    public TreapMyTreeSet(DescendingTreapMyTreeSet<E> parent) {
        root = parent.root;
        size = parent.size;
        comparator = parent.comparator;
    }

    private int compare(E a, E b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        }
        return ((Comparable<? super E>) a).compareTo(b);
    }

    /**
     * Node of Treap.
     */
    private static class Node<E> {

        private static final Random random = new Random();
        private Node<E> left;
        private Node<E> right;
        private Node<E> parent;
        private E key;
        private long priority;

        public E getKey() {
            return key;
        }

        public long getPriority() {
            return priority;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setLeft(Node<E> left) {
            this.left = left;
        }

        public void setRight(Node<E> right) {
            this.right = right;
        }

        public void setParent(Node<E> parent) {
            this.parent = parent;
        }

        /**
         * Constructor of Treap Node with given key.
         * @param key - key of new Node
         */
        public Node(E key) {
            left = null;
            right = null;
            parent = null;
            this.key = key;
            priority = random.nextLong();
        }
    }

    /**
     * Pair of two Nodes (return type of split).
     */
    private static class Pair<E> {
        private Node<E> first;
        private final Node<E> second;

        /**
         * Constructor of Pair with two given Nodes.
         * @param first - first Node
         * @param second - second Node
         */
        public Pair(Node<E> first, Node<E> second) {
            this.first = first;
            this.second = second;
        }

        public Node<E> getFirst() {
            return first;
        }

        public Node<E> getSecond() {
            return second;
        }

        public void setFirst(Node<E> first) {
            this.first = first;
        }
    }

    /**
     * Descending iterator of Treap.
     */
    private class DescendingTreapIterator implements Iterator<E> {

        private Node<E> cur;

        /**
         * Constructor of DescendingTreapIterator.
         */
        public DescendingTreapIterator() {
            cur = root;
            if (cur == null) return;
            while (cur.getRight() != null) {
                cur = cur.getRight();
            }
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        public boolean hasNext() {
            return cur != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E ans = cur.getKey();
            if (cur.getLeft() != null) {
                cur = cur.getLeft();
                while (cur.getRight() != null) {
                    cur = cur.getRight();
                }
                return ans;
            }
            while (cur.getParent() != null && cur.getParent().getLeft() == cur) {
                cur = cur.getParent();
            }
            cur = cur.getParent();
            return ans;
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.  The behavior of an iterator
         * is unspecified if the underlying collection is modified while the
         * iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        public void remove() {
            if (hasNext()) {
                TreapMyTreeSet.this.remove(cur.getKey());
                next();
            }
        }
    }

    private class TreapIterator implements Iterator<E>{

        private Node<E> cur;

        public TreapIterator() {
            cur = root;
            if (cur == null) return;
            while (cur.getLeft() != null) {
                cur = cur.getLeft();
            }
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         * @return {@code true} if the iteration has more elements
         */
        public boolean hasNext() {
            return cur != null;
        }

        /**
         * Returns the next element in the iteration.
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E ans = cur.getKey();
            if (cur.getRight() != null) {
                cur = cur.getRight();
                while (cur.getLeft() != null) {
                    cur = cur.getLeft();
                }
                return ans;
            }
            while (cur.getParent() != null && cur.getParent().getRight() == cur) {
                cur = cur.getParent();
            }
            cur = cur.getParent();
            return ans;
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.  The behavior of an iterator
         * is unspecified if the underlying collection is modified while the
         * iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        public void remove() {
            if (hasNext()) {
                TreapMyTreeSet.this.remove(cur.getKey());
                next();
            }
        }
    }

    /**
     * Merge two Treaps.
     * @return the head of resulting Treap.
     */
    private Node<E> merge(Node<E> a, Node<E> b) {
        if (b == null) return a;
        if (a == null) return b;
        if (a.getPriority() > b.getPriority()) {
            Node<E> res = merge(a.getRight(), b);
            a.setRight(res);
            res.setParent(a);
            return a;
        }
        Node<E> res = merge(a, b.getLeft());
        b.setLeft(res);
        res.setParent(b);
        return b;
    }

    /**
     * Splits Nodes of subtree into two subtrees by given value. Keys of
     * left subtree are less than given value and of the right one are not less.
     * @param a - Node to be split
     * @param v - value to split Node
     */
    private Pair<E> split(Node<E> a, E v) {
        if (a == null) return new Pair<E>(null, null);
        if (compare(a.getKey(), v) < 0) {
            Pair<E> pair = split(a.getRight(), v);
            a.setRight(pair.getFirst());
            if (pair.getFirst() != null)
                pair.getFirst().setParent(a);
            if (pair.getSecond() != null)
                pair.getSecond().setParent(null);
            a.setParent(null);
            return new Pair<E>(a, pair.getSecond());
        }
        Pair<E> pair = split(a.getLeft(), v);
        a.setLeft(pair.getSecond());
        if (pair.getSecond() != null)
            pair.getSecond().setParent(a);
        if (pair.getFirst() != null)
            pair.getFirst().setParent(null);
        a.setParent(null);
        return new Pair<E>(pair.getFirst(), a);
    }

    /**
     * Splits Nodes of subtree into two subtrees by given value. Keys of
     * left subtree are not bigger than given value and of the right one are bigger.
     * @param a - Node to be split
     * @param v - value to split Node
     */
    private Pair<E> splitHigher(Node<E> a, E v) {
        if (a == null) return new Pair<E>(null, null);
        if (compare(a.getKey(), v) <= 0) {
            Pair<E> pair = splitHigher(a.getRight(), v);
            a.setRight(pair.getFirst());
            if (pair.getFirst() != null)
                pair.getFirst().setParent(a);
            if (pair.getSecond() != null)
                pair.getSecond().setParent(null);
            a.setParent(null);
            return new Pair<E>(a, pair.getSecond());
        }
        Pair<E> pair = splitHigher(a.getLeft(), v);
        a.setLeft(pair.getSecond());
        if (pair.getSecond() != null)
            pair.getSecond().setParent(a);
        if (pair.getFirst() != null)
            pair.getFirst().setParent(null);
        a.setParent(null);
        return new Pair<E>(pair.getFirst(), a);
    }

    /**
     * {@link TreeSet#descendingIterator()}
     **/
    public Iterator<E> descendingIterator() {
        return new DescendingTreapIterator();
    }

    /**
     * {@link TreeSet#descendingSet()}
     **/
    public MyTreeSet<E> descendingSet() {
        return new DescendingTreapMyTreeSet<E>(this);
    }

    /**
     * {@link TreeSet#first()}
     **/
    public E first() {
        if (root == null) return null;
        Node<E> cur = root;
        while (cur.getLeft() != null)
            cur = cur.getLeft();
        return cur.getKey();
    }

    /**
     * {@link TreeSet#last()}
     **/
    public E last() {
        if (root == null) return null;
        Node<E> cur = root;
        while (cur.getRight() != null)
            cur = cur.getRight();
        return cur.getKey();
    }

    /**
     * {@link TreeSet#lower(E)}
     **/
    public E lower(E e) {
        if (root == null) return null;
        Node<E> cur = root;
        Node<E> ls = null;
        while (cur != null) {
            if (compare(cur.getKey(), e) < 0) {
                ls = cur;
                cur = cur.getRight();
            } else {
                cur = cur.getLeft();
            }
        }
        return ls == null ? null : ls.getKey();
    }

    /**
     * {@link TreeSet#floor(E)}
     **/
    public E floor(E e) {
        if (root == null) return null;
        Node<E> cur = root;
        Node<E> ls = null;
        while (cur != null) {
            if (compare(cur.getKey(), e) <= 0) {
                ls = cur;
                cur = cur.getRight();
            } else {
                cur = cur.getLeft();
            }
        }
        return ls == null ? null : ls.getKey();
    }

    /**
     * {@link TreeSet#ceiling(E)}
     **/
    public E ceiling(E e) {
        if (root == null) return null;
        Node<E> cur = root;
        Node<E> ls = null;
        while (cur != null) {
            if (compare(cur.getKey(), e) < 0) {
                cur = cur.getRight();
            } else {
                ls = cur;
                cur = cur.getLeft();
            }
        }
        return ls == null ? null : ls.getKey();
    }

    /**
     * {@link TreeSet#higher(E)}
     **/
    public E higher(E e) {
        if (root == null) return null;
        Node<E> cur = root;
        Node<E> ls = null;
        while (cur != null) {
            if (compare(cur.getKey(), e) <= 0) {
                cur = cur.getRight();
            } else {
                ls = cur;
                cur = cur.getLeft();
            }
        }
        return ls == null ? null : ls.getKey();
    }

    /**
     * Returns the number of elements in this set (its cardinality).  If this
     * set contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of elements in this set (its cardinality)
     */
    public int size() {
        return size;
    }

    /**
     * Returns <tt>true</tt> if this set contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this set
     * contains an element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this set is to be tested
     * @return <tt>true</tt> if this set contains the specified element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this set
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              set does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    public boolean contains(Object o) {
        E that = (E) o;
        return that != null && that.equals(floor(that));
    }

    /**
     * Returns an iterator over the elements in this set.  The elements are
     * returned in no particular order (unless this set is an instance of some
     * class that provides a guarantee).
     *
     * @return an iterator over the elements in this set
     */
    public Iterator<E> iterator() {
        return new TreapIterator();
    }

    /**
     * Adds the specified element to this set if it is not already present
     * (optional operation).  More formally, adds the specified element
     * <tt>e</tt> to this set if the set contains no element <tt>e2</tt>
     * such that
     * <tt>(e==null&nbsp;?&nbsp;e2==null&nbsp;:&nbsp;e.equals(e2))</tt>.
     * If this set already contains the element, the call leaves the set
     * unchanged and returns <tt>false</tt>.  In combination with the
     * restriction on constructors, this ensures that sets never contain
     * duplicate elements.
     * <p>
     * <p>The stipulation above does not imply that sets must accept all
     * elements; sets may refuse to add any particular element, including
     * <tt>null</tt>, and throw an exception, as described in the
     * specification for {@link Collection#add Collection.add}.
     * Individual set implementations should clearly document any
     * restrictions on the elements that they may contain.
     *
     * @param e element to be added to this set
     * @return <tt>true</tt> if this set did not already contain the specified
     * element
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of the specified element
     *                                       prevents it from being added to this set
     * @throws NullPointerException          if the specified element is null and this
     *                                       set does not permit null elements
     * @throws IllegalArgumentException      if some property of the specified element
     *                                       prevents it from being added to this set
     */
    public boolean add(E e) {
        if (contains(e)) return false;
        size++;
        Pair<E> pair = split(root, e);
        pair.setFirst(merge(pair.getFirst(), new Node<E>(e)));
        root = merge(pair.getFirst(), pair.getSecond());
        return true;
    }

    /**
     * Removes the specified element from this set if it is present
     * (optional operation).  More formally, removes an element <tt>e</tt>
     * such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>, if
     * this set contains such an element.  Returns <tt>true</tt> if this set
     * contained the element (or equivalently, if this set changed as a
     * result of the call).  (This set will not contain the element once the
     * call returns.)
     *
     * @param o object to be removed from this set, if present
     * @return <tt>true</tt> if this set contained the specified element
     * @throws ClassCastException            if the type of the specified element
     *                                       is incompatible with this set
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if the specified element is null and this
     *                                       set does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *                                       is not supported by this set
     */
    public boolean remove(Object o) {
        E that = (E) o;
        if (that == null) return false;
        Pair<E> pair = split(root, that);
        Pair<E> pair1 = splitHigher(pair.getSecond(), that);
        boolean flag = pair1.getFirst() != null;
        root = merge(pair.getFirst(), pair1.getSecond());
        if (flag) size--;
        return flag;
    }

    /**
     * Removes all of the elements from this set (optional operation).
     * The set will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> method
     *                                       is not supported by this set
     */
    public void clear() {
        root = null;
        size = 0;
    }
}
