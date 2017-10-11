/**
 * Created by myutman on 10/11/17.
 */
public class Tree<T extends Comparable<T>> {
    private class Node{
        public T value;
        public int size = 1;
        public Node left = null, right = null;

        Node(T value){
            this.value = value;
        }

        void refresh(){
            size = (left == null ? 0 : left.size) + (right == null ? 0 : right.size) + 1;
        }
    }

    private Node root = null;

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

    public void add(T element){
        root = addToNode(root, element);
    }

    public boolean contains(T element){
        Node cur = root;
        while (cur != null){
            if (cur.value.compareTo(element) < 0){
                cur = cur.left;
            } else if (cur.value.compareTo(element) > 0){
                cur = cur.right;
            } else {
                return true;
            }
        }
        return false;
    }

    public int size(){
        return (root == null ? 0 : root.size);
    }
}
