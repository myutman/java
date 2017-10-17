/**
 * Created by myutman on 10/11/17.
 */
public class Tree<T extends Comparable<T>> {

    private class Node{
        private T value;
        private int size = 1;

        private Node left = null, right = null;

        /**
         * @param value - value for this Node
         *
         * Constructor of Node.
         */
        private Node(T value){
            this.value = value;
        }

        /**
         * Updates number of elements in the Node.
         */
        private void refresh(){
            size = (left == null ? 0 : left.size) + (right == null ? 0 : right.size) + 1;
        }
    }

    private Node root = null;

    /**
     * @param node - Node to add element to
     * @param element - element needed to be added
     * @return - Node after adding element
     *
     * Adds element to the Node and returns new Node with this element added.
     */
    private Node addToNode(Node node, T element){
        if (node == null){
            return new Node(element);
        }
        if (node.value.compareTo(element) < 0){
            node.right = addToNode(node.right, element);
        } else if (node.value.compareTo(element) > 0){
            node.left = addToNode(node.left, element);
        }
        node.refresh();
        return node;
    }

    /**
     * @param element - element needed to be added
     *
     * Adds element to the Tree.
     */
    public void add(T element){
        root = addToNode(root, element);
    }

    /**
     * @param element - element needed to be checked
     * @return - true if Tree contains element and false otherwise
     *
     * Tells is it true that element is in Tree.
     */
    public boolean contains(T element){
        Node cur = root;
        while (cur != null){
            if (cur.value.compareTo(element) > 0){
                cur = cur.left;
            } else if (cur.value.compareTo(element) < 0){
                cur = cur.right;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * @return - number of elements in the Tree
     *
     * Return number of elements in the Tree.
     */
    public int size(){
        return (root == null ? 0 : root.size);
    }
}
