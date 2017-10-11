/**
 * Created by myutman on 10/11/17.
 */
public class Main {

    public static void main(String[] args) {
        Tree<Integer> tr = new Tree<Integer>();
        tr.add(7);
        tr.add(4);
        tr.add(8);
        tr.add(4);
        tr.add(7);
        tr.add(9);
        System.out.println(tr.contains(7));
        System.out.println(tr.contains(6));
        System.out.println(tr.size());
    }
}
