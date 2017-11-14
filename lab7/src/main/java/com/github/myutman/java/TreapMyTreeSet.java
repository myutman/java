package com.github.myutman.java;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.TreeSet;

/**
 * Created by myutman on 11/13/17.
 *
 * Implementation of MyTreeSet with Treap.
 */
public class TreapMyTreeSet<E> implements MyTreeSet<E> {

    protected Random random;
    protected Node root;
    protected int size;
    protected Comparator<E> comparator = null;

    /**
     * Constructor of TreapMyTreeSet of comparable E that compares elements with compareTo
     */
    public <E extends Comparable<E>> TreapMyTreeSet() {
        random = new Random();
        root = null;
        size = 0;
    }

    /**
     * @param comparator - comparator to compare elements.
     *
     * Constructor of TreapMyTreeSet that compares elements with given comparator.
     */
    public TreapMyTreeSet(Comparator<E> comparator) {
        random = new Random();
        root = null;
        size = 0;
        this.comparator = comparator;
    }

    /**
     * @param parent - descending set we want to get DescendingSet to
     *
     * Constructor of TreapMyTreeSet that is descending to a given DescendingSet
     */
    public TreapMyTreeSet(DescendingTreapMyTreeSet<E> parent){
        random = parent.random;
        root = parent.root;
        size = parent.size;
        comparator = parent.comparator;
    }

    private int compare(E a, E b){
        if (comparator != null){
            return comparator.compare(a, b);
        }
        return ((Comparable<E>) a).compareTo(b);
    }

    /**
     * Node of Treap.
     */
    private class Node {
        private Node left;
        private Node right;
        private Node parent;
        private E key;
        private long priority;

        public E getKey() {
            return key;
        }

        public long getPriority() {
            return priority;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public Node getParent() {
            return parent;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        /**
         * @param key - key of new Node
         *
         * Constructor of Treap Node with given key.
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
    private class Pair {
        private Node first;
        private Node second;

        /**
         * @param first - first Node
         * @param second - second Node
         *
         * Constructor of Pair with two given Nodes.
         */
        public Pair(Node first, Node second) {
            this.first = first;
            this.second = second;
        }

        public Node getFirst() {
            return first;
        }

        public Node getSecond() {
            return second;
        }

        public void setFirst(Node first) {
            this.first = first;
        }
    }

    /**
     * Descending iterator of Treap.
     */
    public class DescendingTreapIterator implements Iterator<E> {

        private Node cur;

        /**
         * Constructor of DescendingTreapIterator.
         */
        public DescendingTreapIterator(){
            cur = root;
            if (cur == null) return;
            while (cur.getRight() != null){
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
            if (cur.getLeft() != null){
                cur = cur.getLeft();
                while (cur.getRight() != null){
                    cur = cur.getRight();
                }
                return ans;
            }
            while (cur.getParent() != null && cur.getParent().getLeft() == cur){
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
            throw new UnsupportedOperationException();
        }
    }

    public class TreapIterator implements Iterator<E>{

        private Node cur;

        public TreapIterator(){
            cur = root;
            if (cur == null) return;
            while (cur.getLeft() != null){
                cur = cur.getLeft();
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
            if (cur.getRight() != null){
                cur = cur.getRight();
                while (cur.getLeft() != null){
                    cur = cur.getLeft();
                }
                return ans;
            }
            while (cur.getParent() != null && cur.getParent().getRight() == cur){
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
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Merge two Treaps.
     *
     * @return the head of resulting Treap.
     */
    private Node merge(Node a, Node b){
        if (b == null) return a;
        if (a == null) return b;
        if (a.getPriority() > b.getPriority()){
            Node res = merge(a.getRight(), b);
            a.setRight(res);
            res.setParent(a);
            return a;
        }
        Node res = merge(a, b.getLeft());
        b.setLeft(res);
        res.setParent(b);
        return b;
    }

    /**
     * @param a - Node to be split
     * @param v - value to split Node
     *
     * Splits Nodes of subtree into two subtrees by given value. Keys of
     * left subtree are less than given value and of the right one are not less.
     */
    private Pair split(Node a, E v){
        if (a == null) return new Pair(null, null);
        if (compare(a.getKey(), v) < 0){
            Pair pair = split(a.getRight(), v);
            a.setRight(pair.getFirst());
            if (pair.getFirst() != null)
                pair.getFirst().setParent(a);
            if (pair.getSecond() != null)
                pair.getSecond().setParent(null);
            a.setParent(null);
            return new Pair(a, pair.getSecond());
        }
        Pair pair = split(a.getLeft(), v);
        a.setLeft(pair.getSecond());
        if (pair.getSecond() != null)
            pair.getSecond().setParent(a);
        if (pair.getFirst() != null)
            pair.getFirst().setParent(null);
        a.setParent(null);
        return new Pair(pair.getFirst(), a);
    }

    /**
     * @param a - Node to be split
     * @param v - value to split Node
     *
     * Splits Nodes of subtree into two subtrees by given value. Keys of
     * left subtree are not bigger than given value and of the right one are bigger.
     */
    private Pair splitHigher(Node a, E v){
        if (a == null) return new Pair(null, null);
        if (compare(a.getKey(), v) <= 0){
            Pair pair = splitHigher(a.getRight(), v);
            a.setRight(pair.getFirst());
            if (pair.getFirst() != null)
                pair.getFirst().setParent(a);
            if (pair.getSecond() != null)
                pair.getSecond().setParent(null);
            a.setParent(null);
            return new Pair(a, pair.getSecond());
        }
        Pair pair = splitHigher(a.getLeft(), v);
        a.setLeft(pair.getSecond());
        if (pair.getSecond() != null)
            pair.getSecond().setParent(a);
        if (pair.getFirst() != null)
            pair.getFirst().setParent(null);
        a.setParent(null);
        return new Pair(pair.getFirst(), a);
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
        TreapMyTreeSet<E> ans = new DescendingTreapMyTreeSet(this);
        return ans;
    }

    /**
     * {@link TreeSet#first()}
     **/
    public E first() {
        if (root == null) return null;
        Node cur = root;
        while (cur.getLeft() != null)
            cur = cur.getLeft();
        return cur.getKey();
    }

    /**
     * {@link TreeSet#last()}
     **/
    public E last() {
        if (root == null) return null;
        Node cur = root;
        while (cur.getRight() != null)
            cur = cur.getRight();
        return cur.getKey();
    }

    /**
     * {@link TreeSet#lower(E)}
     *
     * @param e
     **/
    public E lower(E e) {
        if (root == null) return null;
        Node cur = root;
        Node ls = null;
        while (cur != null){
            if (compare(cur.getKey(), e) < 0){
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
     *
     * @param e
     **/
    public E floor(E e) {
        if (root == null) return null;
        Node cur = root;
        Node ls = null;
        while (cur != null){
            if (compare(cur.getKey(), e) <= 0){
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
     *
     * @param e
     **/
    public E ceiling(E e) {
        if (root == null) return null;
        Node cur = root;
        Node ls = null;
        while (cur != null){
            if (compare(cur.getKey(), e) < 0){
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
     *
     * @param e
     **/
    public E higher(E e) {
        if (root == null) return null;
        Node cur = root;
        Node ls = null;
        while (cur != null){
            if (compare(cur.getKey(), e) <= 0){
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
     * Returns <tt>true</tt> if this set contains no elements.
     *
     * @return <tt>true</tt> if this set contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
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
        if (that == null) return false;
        return that.equals(floor(that));
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
     * Returns an array containing all of the elements in this set.
     * If this set makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the
     * elements in the same order.
     * <p>
     * <p>The returned array will be "safe" in that no references to it
     * are maintained by this set.  (In other words, this method must
     * allocate a new array even if this set is backed by an array).
     * The caller is thus free to modify the returned array.
     * <p>
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all the elements in this set
     */
    public Object[] toArray() {
        Object[] ans = new Object[size];
        int ct = 0;
        for (Object o : this){
            ans[ct++] = o;
        }
        return ans;
    }

    /**
     * Returns an array containing all of the elements in this set; the
     * runtime type of the returned array is that of the specified array.
     * If the set fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this set.
     * <p>
     * <p>If this set fits in the specified array with room to spare
     * (i.e., the array has more elements than this set), the element in
     * the array immediately following the end of the set is set to
     * <tt>null</tt>.  (This is useful in determining the length of this
     * set <i>only</i> if the caller knows that this set does not contain
     * any null elements.)
     * <p>
     * <p>If this set makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements
     * in the same order.
     * <p>
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     * <p>
     * <p>Suppose <tt>x</tt> is a set known to contain only strings.
     * The following code can be used to dump the set into a newly allocated
     * array of <tt>String</tt>:
     * <p>
     * <pre>
     *     String[] y = x.toArray(new String[0]);</pre>
     * <p>
     * Note that <tt>toArray(new Object[0])</tt> is identical in function to
     * <tt>toArray()</tt>.
     *
     * @param a the array into which the elements of this set are to be
     *          stored, if it is big enough; otherwise, a new array of the same
     *          runtime type is allocated for this purpose.
     * @return an array containing all the elements in this set
     * @throws ArrayStoreException  if the runtime type of the specified array
     *                              is not a supertype of the runtime type of every element in this
     *                              set
     * @throws NullPointerException if the specified array is null
     */
    public <T> T[] toArray(T[] a) {
        int ct = 0;
        for (E o : this){
            a[ct++] = (T) o;
        }
        return a;
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
        Pair pair = split(root, e);
        pair.setFirst(merge(pair.getFirst(), new Node(e)));
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
        Pair pair = split(root, that);
        Pair pair1 = splitHigher(pair.getSecond(), that);
        boolean flag = pair1.getFirst() != null;
        root = merge(pair.getFirst(), pair1.getSecond());
        if (flag) size--;
        return flag;
    }

    /**
     * Returns <tt>true</tt> if this set contains all of the elements of the
     * specified collection.  If the specified collection is also a set, this
     * method returns <tt>true</tt> if it is a <i>subset</i> of this set.
     *
     * @param c collection to be checked for containment in this set
     * @return <tt>true</tt> if this set contains all of the elements of the
     * specified collection
     * @throws ClassCastException   if the types of one or more elements
     *                              in the specified collection are incompatible with this
     *                              set
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection contains one
     *                              or more null elements and this set does not permit null
     *                              elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>),
     *                              or if the specified collection is null
     * @see #contains(Object)
     */
    public boolean containsAll(Collection<?> c) {
        for (Object o: c){
            if (!contains(o)) return false;
        }
        return true;
    }

    /**
     * Adds all of the elements in the specified collection to this set if
     * they're not already present (optional operation).  If the specified
     * collection is also a set, the <tt>addAll</tt> operation effectively
     * modifies this set so that its value is the <i>union</i> of the two
     * sets.  The behavior of this operation is undefined if the specified
     * collection is modified while the operation is in progress.
     *
     * @param c collection containing elements to be added to this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of an element of the
     *                                       specified collection prevents it from being added to this set
     * @throws NullPointerException          if the specified collection contains one
     *                                       or more null elements and this set does not permit null
     *                                       elements, or if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the
     *                                       specified collection prevents it from being added to this set
     * @see #add(Object)
     */
    public boolean addAll(Collection<? extends E> c) {
        boolean flag = false;
        for (E e: c){
            if (add(e))
                flag = true;
        }
        return flag;
    }

    /**
     * Retains only the elements in this set that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this set all of its elements that are not contained in the
     * specified collection.  If the specified collection is also a set, this
     * operation effectively modifies this set so that its value is the
     * <i>intersection</i> of the two sets.
     *
     * @param c collection containing elements to be retained in this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>retainAll</tt> operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of an element of this set
     *                                       is incompatible with the specified collection
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this set contains a null element and the
     *                                       specified collection does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     */
    public boolean retainAll(Collection<?> c) {
        int oldSize = size;
        TreapMyTreeSet newSet = new TreapMyTreeSet();
        for (Object o: c){
            if (contains(o))
                newSet.add((E) o);
        }
        root = newSet.root;
        size = newSet.size;
        random = newSet.random;
        return size != oldSize;
    }

    /**
     * Removes from this set all of its elements that are contained in the
     * specified collection (optional operation).  If the specified
     * collection is also a set, this operation effectively modifies this
     * set so that its value is the <i>asymmetric set difference</i> of
     * the two sets.
     *
     * @param c collection containing elements to be removed from this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of an element of this set
     *                                       is incompatible with the specified collection
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this set contains a null element and the
     *                                       specified collection does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean removeAll(Collection<?> c) {
        boolean flag = false;
        for (Object o: c){
            if (remove(o))
                flag = true;
        }
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
